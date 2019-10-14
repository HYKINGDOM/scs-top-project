package com.scs.top.project.module.scs.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author yihur
 */
@Mapper
public interface ScsMapper {

    /**
     *
     * @return
     */
    List<Map<String,Object>> queryScsCodeReview();
}
