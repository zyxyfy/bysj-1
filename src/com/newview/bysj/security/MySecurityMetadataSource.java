package com.newview.bysj.security;

import com.newview.bysj.exception.MessageException;
import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * 获取数据源
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = Logger.getLogger(MyAccessDecisionManager.class);
    // Logger.getLogger(MySecurityMetadataSource.class);
    private String driver = "com.mysql.jdbc.Driver";
    private String user;
    private String password;
    private String url;

    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    public MySecurityMetadataSource() {
        try {
            loadJDBC();
            loadDefinedResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadJDBC() throws IOException {
        Properties pro = new Properties();
        pro.load(this.getClass().getClassLoader().getResourceAsStream("db.properties"));
        //driver = pro.getProperty("jdbc.driverClass");
        //获取登录的用户名
        user = pro.getProperty("jdbc.user");
        //获取登录的密码
        password = pro.getProperty("jdbc.password");
        //获取连接数据库的路径
        url = pro.getProperty("jdbc.jdbcUrl");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 获取资源对应的角色,服务器启动时不会执行该方法，只有当请求链接时，才会执行
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
        String reqUrl = ((FilterInvocation) obj).getRequestUrl();
        Iterator<String> urls = resourceMap.keySet().iterator();
        String url;
        while (urls.hasNext()) {
            url = urls.next();
            //System.out.println("the url: " + url);
            // 如果请求的url在资源列表中存在，则返回这个资源所对应的权限（角色），到MyAccessDecisionManager中的decide方法中进行决策
            if (url.equals(reqUrl)) {
                return resourceMap.get(url);
            }
        }
        //如果没有找到对应的路径，则说明该路径的访问不需要权限，直接访问
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    // 加载资源
    private void loadDefinedResource() {
        // 获得所有的角色名称
        List<String> roleNames = this.getAllRollName();
        //logger.debug("get roleName" + roleNames.toString());
        resourceMap = new HashMap<>();
        for (String roleName : roleNames) {
            ConfigAttribute configAttribute = new SecurityConfig(roleName);
            // 得到每个角色所对应的资源
            List<String> URLS = getURLSByRoleName(roleName);
            //logger.debug("roleName: "+roleName+"  URLS :" + URLS.toString());
            // 对资源进行遍历，并将资源-角色的对应关系添加到resourceMap集合中
            for (String URL : URLS) {
                URL = "/" + URL.trim();
                if (resourceMap.containsKey(URL)) {
                    Collection<ConfigAttribute> collectionOfRoleName = resourceMap.get(URL);
                    collectionOfRoleName.add(configAttribute);
                    resourceMap.put(URL, collectionOfRoleName);
                } else {
                    Collection<ConfigAttribute> collectionOfRoleName = new ArrayList<>();
                    collectionOfRoleName.add(configAttribute);
                    resourceMap.put(URL, collectionOfRoleName);
                }
            }
        }

    }

    // 得到所有的角色名称
    private List<String> getAllRollName() {
        List<String> roleNames = new ArrayList<>();
        try {
            // 加载jdbc的驱动
            Class.forName(driver);
            // 得到DriverManager对象，获取数据库连接
            Connection conn = DriverManager.getConnection(url, user, password);
            // 创建Statement对象，用于执行sql语句的工具接口
            Statement sm = conn.createStatement();
            String sql = "select roleName from role";
            // 返回查询到的结果集
            ResultSet rs = sm.executeQuery(sql);
            while (rs.next()) {
                roleNames.add(rs.getString("roleName"));
            }
            // 关闭资源
            rs.close();
            //sm.close();
            conn.close();
        } catch (Exception e) {
            throw new MessageException("获取角色失败");
        }
        return roleNames;
    }

    // 根据角色名称获取对应的资源
    private List<String> getURLSByRoleName(String roleName) {
        List<String> urls = new ArrayList<>();
        try {
            // 加载jdbc的驱动
            Class.forName(driver);
            // 获取Connection
            Connection conn = DriverManager.getConnection(url, user, password);
            // 创建Statement对象，用于执行sql语句的工具接口
            Statement sm = conn.createStatement();
            //"SELECT DISTINCT resource.id, role.id, resource.url FROM resource left join role_resource on resource.id = role_resource.resource_id left join role on role.id = role_resource.role_id where parent_id is not null and role.roleName = '"+ roleName + "'"
            String sql = "SELECT DISTINCT resource.id, role.id, resource.url FROM resource left join roleresource on resource.id = roleresource.resource_id left join role on role.id = roleresource.role_id where parent_id is not null and role.roleName = '" + roleName + "'";
            ResultSet rs = sm.executeQuery(sql);
            String resource;
            while (rs.next()) {
                // 得到资源的路径
                resource = rs.getString("url");
                urls.add(resource);
            }
            // 关闭连接
            rs.close();
            //sm.close();
            conn.close();

        } catch (Exception e) {
            throw new MessageException("根据角色获取对应的资源失败");
        }
        return urls;
    }
}
