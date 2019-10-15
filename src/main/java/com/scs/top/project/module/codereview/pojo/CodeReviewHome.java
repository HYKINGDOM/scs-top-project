package com.scs.top.project.module.codereview.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 首页信息
 * @author yihur
 */
@Data
public class CodeReviewHome {

    /**
     * 列表Id
     */
    private int codeReviewId;
    /**
     * 列表内容标题
     */
    private String codeReviewTitle;
    /**
     * 列表内容描述
     */
    private String codeReviewDescript;
    /**
     * 列表点赞数
     */
    private int pointRatio;
    /**
     * 列表评论数
     */
    private int codeReviewCount;
    /**
     * github地址
     */
    private String gitUrl;
    /**
     * 发布人用户名
     */
    private String userName;
    /**
     * 代码类型
     */
    private String codeType;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改人
     */
    private String updateBy;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;


}
