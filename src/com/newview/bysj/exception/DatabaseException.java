package com.newview.bysj.exception;

/**
 * 自定义数据访问异常类
 */
public class DatabaseException extends Exception {

    private static final long serialVersionUID = 1L;

    public DatabaseException() {
        super();
    }

    public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(Throwable cause) {
        super(cause);
    }

}
