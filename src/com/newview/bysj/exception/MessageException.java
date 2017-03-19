package com.newview.bysj.exception;

/**
 * 自定义异常类
 */
public class MessageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public MessageException() {
        super();
    }

    public MessageException(String message) {
        super(message);
    }


}
