package com.newview.bysj.exception;

/**
 * 用户无效的异常类
 *
 * @author zhan
 */
public class UserInvalidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public UserInvalidException() {
        super();
    }

    public UserInvalidException(String message) {
        super(message);
    }


}
