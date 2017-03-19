package com.newview.bysj.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 自定义访问授权的拦截器
 *
 * @author 张战
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private FilterInvocationSecurityMetadataSource securityMetadataSource;

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
            throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(arg0, arg1, arg2);
        invoke(fi);
    }

    /**
     * 自定义的方法
     *
     * @param fi
     * @throws IOException
     * @throws ServletException
     */
    private void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken ist = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(ist, null);
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.securityMetadataSource;
    }

    public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
        return securityMetadataSource;
    }

    public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
        this.securityMetadataSource = securityMetadataSource;
    }

}
