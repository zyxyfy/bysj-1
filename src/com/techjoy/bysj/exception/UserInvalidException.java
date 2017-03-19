package com.techjoy.bysj.exception;


/**
 * session 中用户失效
 */
public class UserInvalidException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -1223491628830238231L;

    /**
     *
     */

    public UserInvalidException() {
        super();
    }

    public UserInvalidException(String message) {
        super(message);
    }


}
