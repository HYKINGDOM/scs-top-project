package com.scs.top.project.module.codereview.service;



import com.github.pagehelper.PageInfo;
import com.scs.top.project.module.codereview.pojo.CodeReviewHome;

import java.util.Map;

/**
 * @author Administrator
 */
public interface CodeReviewService {


    PageInfo<CodeReviewHome> getScsHomePageInfoPage(int pageNum, int pageSize, Map<String, String> scsHome);
}
