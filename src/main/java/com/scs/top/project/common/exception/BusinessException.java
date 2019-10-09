package com.scs.top.project.common.exception;

public class BusinessException extends NullPointerException{

    private static final long serialVersionUID = 1L;

    //错误码
    private Integer code;

    public BusinessException() {}

    public BusinessException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}