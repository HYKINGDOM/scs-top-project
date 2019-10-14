package com.scs.top.project.module.scs.service;



import com.github.pagehelper.PageInfo;
import com.scs.top.project.module.scs.pojo.ScsHome;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface ScsService {


    PageInfo<ScsHome> getScsHomePageInfoPage(int pageNum, int pageSize, Map<String, String> scsHome);

    List<Map<String,Object>> queryScsCodeReview();
}
