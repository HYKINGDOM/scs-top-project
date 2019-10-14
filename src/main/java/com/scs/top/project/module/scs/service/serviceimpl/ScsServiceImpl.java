package com.scs.top.project.module.scs.service.serviceimpl;

import com.github.pagehelper.PageInfo;

import com.scs.top.project.framework.druid.DataSourceNames;
import com.scs.top.project.framework.druid.DataSourceTransaction;
import com.scs.top.project.framework.druid.TransactionConfig;
import com.scs.top.project.module.scs.mapper.ScsMapper;
import com.scs.top.project.module.scs.pojo.ScsHome;
import com.scs.top.project.module.scs.service.ScsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ScsServiceImpl implements ScsService {

    @Autowired
    private ScsMapper scsMapper;

    @Override
    public PageInfo<ScsHome> getScsHomePageInfoPage(int pageNum, int pageSize, Map<String, String> scsHome) {

        if (!scsHome.containsKey("data")) {
            throw new RuntimeException("参数为空");
        }
        return null;
    }

    @Override
    @DataSourceTransaction(name = DataSourceNames.FIRST)
    @Transactional(value = TransactionConfig.SECOND_TX, rollbackFor = Exception.class)
    public List<Map<String, Object>> queryScsCodeReview() {
        List<Map<String, Object>> maps = scsMapper.queryScsCodeReview();
        log.info("查询值：{}", maps);
        return maps;
    }
}
