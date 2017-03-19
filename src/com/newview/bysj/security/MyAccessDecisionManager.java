package com.newview.bysj.security;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Iterator;

/**
 * 访问决策
 * 用以判断当前请求的资源是否有权限访问
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

    //private static final Logger logger = Logger.getLogger(MyAccessDecisionManager.class);

    /**
     * 参数说明：
     * auths:是用户拥有的角色数组，它是从DefaultUserDetailsService中的loadUserByUsername传递过来的
     * configAttribute:是请求的url允许的角色数组，它是从MySecurityMetadataSource中的loadDefineResource传递过来的
     */
    @Override
    public void decide(Authentication authentications, Object obj, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (authentications == null) {

            return;
        }
        Iterator<ConfigAttribute> it = configAttributes.iterator();
        /*while(it.hasNext()){
			System.out.println("请求的url允许的角色数组："+it.next());
		}
		System.out.println("=========执行下一个方法=============");*/
        while (it.hasNext()) {
            ConfigAttribute configAttribute = it.next();
            String roleName = configAttribute.getAttribute();
            for (GrantedAuthority auth : authentications.getAuthorities()) {
                //如果用户拥有的角色和要访问的url拥有的角色相等，则允许访问,必须要将GrantedAuthority对象转换为String类型才可以比较。不然一直是false
                if (roleName.equals(auth.toString())) {
                    return;
                }
            }
        }
        //角色不相等，不允许访问
        throw new AccessDeniedException("你没有权限访问！");
    }

    /**
     * 此方法被AbstractSecurityInterceptor调用，用来决定AccessDecisionManager是否可以传递
     * configAttribute
     */
    @Override
    public boolean supports(ConfigAttribute arg0) {
        return true;
    }

    @Override
    public boolean supports(Class<?> arg0) {
        return true;
    }

}
