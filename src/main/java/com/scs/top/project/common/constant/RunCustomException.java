package com.scs.top.project.common.constant;

/**
 * 自定义异常
 * @author yihur
 */
public class RunCustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RunCustomException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RunCustomException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RunCustomException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RunCustomException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
