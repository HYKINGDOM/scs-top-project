package com.scs.top.project.module.scs.service.serviceimpl;

import com.github.pagehelper.PageInfo;

import com.scs.top.project.module.scs.pojo.ScsHome;
import com.scs.top.project.module.scs.service.ScsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScsServiceImpl implements ScsService {
    @Override
    public PageInfo<ScsHome> getScsHomePageInfoPage(int pageNum, int pageSize, Map<String, String> scsHome) {

        if (!scsHome.containsKey("data")){
            throw new RuntimeException("参数为空");
        }
        return null;
    }
}
