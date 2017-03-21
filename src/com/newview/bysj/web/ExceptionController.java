package com.newview.bysj.web;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理类
 *
 * @author zhan
 *         Created on 2017/03/21  17:24:40
 */
public class ExceptionController implements HandlerExceptionResolver {

    private static final Logger LOGGER = Logger.getLogger(ExceptionController.class);

    /**
     * 跳转到异常页面
     */
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        Map<String, Object> map = new HashMap<>();
        if(e instanceof AuthenticationServiceException){
            LOGGER.info("跳转到登录页面");
            return new ModelAndView("login/login");
        }
        e.printStackTrace();
        map.put("e", e);
        map.put("detail", e.getStackTrace());
        LOGGER.error(e);
        return new ModelAndView("error",map);
    }
}
