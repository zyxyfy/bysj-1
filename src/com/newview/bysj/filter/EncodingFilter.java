package com.newview.bysj.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 将请求的编码类型改为utf-8，防止乱码的产生 =
 * Created by zhan on 2016/4/1.
 */
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 更改请求的编码类型
     *
     * @param servletRequest  当前请求
     * @param servletResponse 请求对应的response
     * @param filterChain     过滤器链
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        //设置请求的字符集
        httpServletRequest.setCharacterEncoding("UTF-8");
        //将请求放行到目的地址
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
