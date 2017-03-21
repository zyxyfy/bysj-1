package com.newview.bysj.security.login;

import com.newview.bysj.domain.User;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * spring security的登录认证
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //private static final Logger logger = Logger.getLogger(MyUsernamePasswordAuthenticationFilter.class);
    @Autowired
    private UserService userService;

    /**
     * spring security的登录功能
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        //post只能大写，小写比较返回false
        if (!request.getMethod().equals("POST"))
            throw new AuthenticationServiceException("提交的方式不支持" + request.getMethod());
        HttpSession httpSession = request.getSession();
        //正确的验证码
        //String correctIdentifyingCode = (String) httpSession.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        //用户输入的验证码
        //String inputIdentifyingCode = request.getParameter("identifyingCode");
        /*if (inputIdentifyingCode == null || !inputIdentifyingCode.equals(correctIdentifyingCode))
            throw new AuthenticationServiceException("验证码输入错误");*/
        //获取输入的用户名和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //根据用户名，得到数据库中的用户
        User storedUser = userService.uniqueResult("username", username);
        if (storedUser == null)
            throw new AuthenticationServiceException("用户不存在");
        //把用户输入的密码进行加密后与数据库中的密码进行比较
        if (storedUser.getPassword().equals(CommonHelper.makeMD5(password))) {
            //设置登录时间
            storedUser.setLastLoginTime(CommonHelper.getNow());

			/*
			 * 获取最后登录的ip,
			 */
            storedUser.setLastLoginIp(request.getRemoteAddr());

            //登录的次数加一
            if (storedUser.getLoginTime() == null) {
                storedUser.setLoginTime(0);
            }
            storedUser.setLoginTime(storedUser.getLoginTime() + 1);
            userService.saveOrUpdate(storedUser);
            httpSession.setAttribute("user", storedUser);
        } else {
            throw new AuthenticationServiceException("用户名或密码错误");
        }

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, CommonHelper.makeMD5(password));
        setDetails(request, authRequest);
        //运行UserDetailService中的loadUserByUsername，再次封装authentication
        return this.getAuthenticationManager().authenticate(authRequest);
    }

}
