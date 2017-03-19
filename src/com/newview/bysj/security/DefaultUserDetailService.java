package com.newview.bysj.security;

import com.newview.bysj.domain.UserRole;
import com.newview.bysj.service.UserRoleService;
import com.newview.bysj.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获取当前登录用户的权限
 *
 * @author 张战
 */
public class DefaultUserDetailService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(DefaultUserDetailService.class);
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //用于存放用户的所有角色
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        // 获取当前登录系统的user
        com.newview.bysj.domain.User user = userService.uniqueResult("username", username);
        if (user == null) {
            try {
                throw new AccessDeniedException("用户名或密码错误！");
            } catch (AccessDeniedException e) {
                e.printStackTrace();
            }
        }
        List<UserRole> list = userRoleService.list("user", com.newview.bysj.domain.User.class, user);
        // 获取该用户所有的角色名称
        for (UserRole userRole : list) {
            String userRoleName = userRole.getRole().getRoleName();
            // 将userRoleName封装成GrantedAuthority对象
            authorities.add(new SimpleGrantedAuthority(userRoleName));
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        User userDetail = new User(user.getUsername(), user.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        return userDetail;
    }

}
