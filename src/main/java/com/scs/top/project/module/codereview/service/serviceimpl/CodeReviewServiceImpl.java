package com.scs.top.project.module.codereview.service.serviceimpl;

import com.github.pagehelper.PageInfo;
import com.scs.top.project.module.codereview.pojo.CodeReviewHome;
import com.scs.top.project.module.codereview.service.CodeReviewService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author yihur
 */
@Service
public class CodeReviewServiceImpl implements CodeReviewService {
    @Override
    public PageInfo<CodeReviewHome> getScsHomePageInfoPage(int pageNum, int pageSize, Map<String, String> scsHome) {

        if (!scsHome.containsKey("data")){
            throw new RuntimeException("参数为空");
        }
        return null;
    }
}
