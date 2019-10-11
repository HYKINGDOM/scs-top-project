package com.scs.top.project.module.spider.utils;


import com.scs.top.project.common.util.StringUtils;
import com.scs.top.project.framework.druid.DataSourceTransaction;
import com.scs.top.project.framework.druid.TransactionConfig;
import com.scs.top.project.module.spider.service.SpiderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Map;


/**
 * 定时任务---用于每天从网站获取数据刷新数据库
 *
 * @author yihur
 */
@Slf4j
@Component
@Configuration
@EnableScheduling
public class DynamicScheduleTask implements SchedulingConfigurer{

    @Autowired
    private SpiderService spiderService;

    @Override
    @DataSourceTransaction(name="second")
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(
                //1.添加任务内容(Runnable)
                () -> spiderService.getBookInfoUrl(),
                //2.设置执行周期(Trigger)
                triggerContext -> {
                    //2.1 从数据库获取执行周期
                    Map<String, Object> cronMapLists = spiderService.getCronMapLists();
                    String scheduleTaskCorn = cronMapLists.get("scheduleTaskCorn").toString();
                    //2.2 合法性校验.
                    if (StringUtils.isEmpty(scheduleTaskCorn)) {
                        log.error("时间解析出错");
                    }
                    spiderService.updateScheduledTime(cronMapLists);
                    //2.3 返回执行周期(Date)
                    return new CronTrigger(scheduleTaskCorn).nextExecutionTime(triggerContext);
                }
        );
    }
}
