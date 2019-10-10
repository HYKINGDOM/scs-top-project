package com.scs.top.project.framework.druid;

import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

/**
 * 多数据源切换
 * @author Administrator
 */
@Aspect
@Component
public class DataSourceAspect implements Ordered {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("@annotation(com.scs.top.project.framework.druid.DataSourceTransaction)")
    public void dataSourcePointCut() {

    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        DataSourceTransaction ds = method.getAnnotation(DataSourceTransaction.class);
        if (ds == null) {
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.info("set datasource is " + DataSourceNames.FIRST);
        } else {
            DynamicDataSource.setDataSource(ds.name());
            logger.info("set datasourceSecond is " + ds.name());
        }
        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.info("clean datasource");
        }
    }


    @Override
    public int getOrder() {
        return 1;
    }
}
