package com.newview.bysj.web.roleToResource;

import com.newview.bysj.domain.Resource;
import com.newview.bysj.domain.Role;
import com.newview.bysj.domain.RoleResource;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.web.baseController.BaseController;
import org.apache.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhan on 2016/4/30.
 */
@Controller
public class RoleToResourceController extends BaseController {

    private static final Logger logger = Logger.getLogger(RoleToResourceController.class);

    /**
     * 用户资源分配的方法
     *
     * @param modelMap           用于存储需要在jsp中获取的数据
     * @param pageNo             当前页
     * @param httpServletRequest 用于获取请求的路径
     * @return jsp
     */
    @RequestMapping("/usersManage/universityAdmin/roleToResource.html")
    public String roleToResource(ModelMap modelMap, Integer pageNo, HttpServletRequest httpServletRequest) {
        //List<Role> roleList = roleService.findAll();
        //modelMap.put("roleList", roleList);
        Page<Role> rolePage = roleService.getRoleByPage(pageNo, 6);
        CommonHelper.pagingHelp(modelMap, rolePage, "roleList", httpServletRequest.getRequestURI(), rolePage.getTotalElements());
        return "usersManage/roleToResource/roleToResource";
    }

    /**
     * 删除角色中的资源
     *
     * @param roleId              需要删除资源的角色id
     * @param resourceId          需要删除的资源
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/usersManage/delResource.html", method = RequestMethod.GET)
    public void delResourceInRole(Integer roleId, Integer resourceId, HttpServletResponse httpServletResponse) {
        //获取当前的角色
        Role role = roleService.findById(roleId);
        //获取角色所拥有的资源
        List<RoleResource> resourceList = role.getRoleResource();
        Resource resource = resourceService.findById(resourceId);
        //对当前角色所拥有的资源进行遍历，如果资源的id和需要删除的资源的id相同，则删除
        try {
            for (RoleResource roleResource : resourceList) {
                //获取需要删除的资源
                Resource delResource = roleResource.getResource();
                //如果当前资源的id和需要删除的资源的id相等，则删除
                if (Objects.equals(delResource.getId(), resourceId)) {
                    RoleResource roleResource1 = roleResourceService.findById(roleResource.getId());
                    //roleResourceService.deleteById(roleResource.getId());
                    //取消与role和resource的关联
                    roleResource1.setRole(null);
                    roleResource1.setResource(null);
                    roleResourceService.saveOrUpdate(roleResource1);
                    roleResourceService.deleteObject(roleResource1);
                }
            }
        } catch (Exception e) {
            throw new MessageException("资源删除失败");
        }
        CommonHelper.buildSimpleJson(httpServletResponse);
    }

    /**
     * 给角色添加资源的get方法
     *
     * @param roleId   需要添加的角色的id
     * @param modelMap 用于存储数据
     * @return jsp
     */
    @RequestMapping(value = "/usersManage/addResource.html", method = RequestMethod.GET)
    public String addResourceInRole(Integer roleId, ModelMap modelMap) {
        Role role = roleService.findById(roleId);

        List<RoleResource> roleResourceList = role.getRoleResource();
        //用于存放当前角色所拥有的所有资源
        List<Resource> resourceList = new ArrayList<>();
        for (RoleResource roleResource : roleResourceList) {
            resourceList.add(roleResource.getResource());
        }
        List<Resource> allResource = resourceService.getChildResource();
        //移除已经拥有的资源
        allResource.removeAll(resourceList);
        modelMap.put("resourceList", allResource);
        //用于传递到post的方法中
        modelMap.put("roleId", roleId);
        return "usersManage/roleToResource/addResource";
    }

    /**
     * 给角色添加资源的post方法
     *
     * @param roleId              需要添加的角色的id
     * @param resourceId          添加的资源的id
     * @param httpServletResponse 用于给浏览器返回json数据
     */
    @RequestMapping(value = "/usersManage/addResource.html", method = RequestMethod.POST)
    public void addResourceInRole(Integer roleId, Integer resourceId, HttpServletResponse httpServletResponse) {
        //获取需要添加的资源
        Resource addResource = resourceService.findById(resourceId);
        //获取需要添加资源的角色
        Role role = roleService.findById(roleId);
        //创建一个新的角色资源对象
        RoleResource roleResource = new RoleResource();
        //设置关联关系
        roleResource.setResource(addResource);
        roleResource.setRole(role);
        roleResourceService.saveOrUpdate(roleResource);
        CommonHelper.buildSimpleJson(httpServletResponse);
    }
}
