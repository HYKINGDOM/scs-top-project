package com.scs.top.project.common.exception;

/**
 * @author yihur
 * 自定义session过期提示异常
 */
public class UserShiroNullPointException extends NullPointerException  {

    protected String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserShiroNullPointException() {
        setMessage("Prop is illegal!");
    }

    public UserShiroNullPointException(String message) {
        this.message = message;
        setMessage(String.format("Prop: %s is illegal!", message));
    }
}
