package com.newview.bysj.initialize;

import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.initialize.domain.*;
import com.newview.bysj.initialize.excel.XlsDataSetBeanFactory;
import com.newview.bysj.service.TestBasicService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * 在使用本程序之前需要将已存在的数据库删除
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationcontext.xml")
public class DataToMysql extends TestBasicService {

    private EntityManager entityManager;
    private String excelFile = "excel/data.xls";

    @Before
    public void init() {
        this.initilize();
    }

    @Test
    public void save() throws Exception {
        //先保存不需要关联的表
        Class[] entityWithoutFK = {Role.class, School.class, Resource.class, ProjectType.class, ProjectFidelity.class, ProjectFrom.class, ProTitle.class, Degree.class, ClassRoom.class};
        for (Class clazz : entityWithoutFK) {
            this.saveEntity(clazz);
        }
        //保存具有外键的表
        this.saveDepartment();
        this.saveMajor();
        this.saveStudentClass();
        //保存用户时注意密码保存成MD5的形式
        this.saveUser();
        this.saveEmployee();
        //this.saveStudent();
        this.setResourceParent();
        this.setRoleHandler();
        this.setResourceChild();
        this.saveUser2Role();
        this.saveRoleReource();
    }

    private void saveStudentClass() throws Exception {
        Major major = null;
        List<StudentClass> studentClasses = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "studentclass", StudentClass.class);
        List<StudentClassWithFK> studentClassWithFKs = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "studentclass", StudentClassWithFK.class);
        for (StudentClass studentClass : studentClasses) {
            for (StudentClassWithFK studentClassWithFK : studentClassWithFKs) {
                if (studentClass.getNo().equals(studentClassWithFK.getNo())) {
                    major = majorService.findById(studentClassWithFK.getMajorId());
                    studentClass.setMajor(major);
                    studentClassService.save(studentClass);
                }
            }
        }
    }

    private void saveMajor() throws Exception {
        Department department = null;
        List<Major> majors = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "major", Major.class);
        List<MajorWithFK> majorWithFKs = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "major", MajorWithFK.class);
        for (Major major : majors) {
            for (MajorWithFK majorWithFK : majorWithFKs) {
                if (major.getNo().equals(majorWithFK.getNo())) {
                    department = departmentService.findById(majorWithFK.getDepartmentId());
                    major.setDepartment(department);
                    majorService.save(major);
                }
            }
        }
    }

    private void saveDepartment() throws Exception {
        List<Department> departments = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "department", Department.class);
        List<DepartmentWithFK> departmentWithFK = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "department", DepartmentWithFK.class);
        int index = 0;//由于两个List的顺序是相同的，所以可以根据顺序号进行对应赋值
        for (Department department : departments) {
            Integer schoolId = departmentWithFK.get(index++).getSchoolId();
            department.setSchool(schoolService.findById(schoolId));
            departmentService.save(department);
        }
    }

    private void saveStudent() throws Exception {
        List<Student> students = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "student", Student.class);
        List<StudentWithFk> studentWithFk = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "student", StudentWithFk.class);
        Integer index = 0;
        StudentClass studentClass = null;
        User user = null;
        for (Student student : students) {
            user = userService.uniqueResult("username", student.getNo());
            student.setUser(user);
            Integer studentClassId = studentWithFk.get(index++).getStudentClassId();
            student.setStudentClass(studentClassService.findById(studentClassId));
            studentService.save(student);
        }
    }

    private void setResourceParent() throws Exception {
        List<ResourceWithFK> resourcesWithFk = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "resource", ResourceWithFK.class);
        List<Resource> resources = resourceService.findAll();
        int index = 0;
        for (Resource resource : resources) {
            System.out.println(resource.getDescription());
            ResourceWithFK resourceWithFk = resourcesWithFk.get(index++);
            Integer parent_id = resourceWithFk.getParentId();
            if (parent_id != null) {
                resource.setParent(resourceService.findById(parent_id));
            }
            resourceService.update(resource);
            resourceService.save(resource);
        }
    }

    private void setRoleHandler() throws Exception {
        List<RoleWithFk> rolesWithFk = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "role", RoleWithFk.class);
        List<Role> roles = roleService.findAll();
        int index = 0;
        for (Role role : roles) {
            System.out.println(role.getDescription());
            RoleWithFk roleWithFk = rolesWithFk.get(index++);
            Integer roleHandler_Id = roleWithFk.getRoleHandlerId();

            if (roleHandler_Id != null) {
                role.setRoleHandler(roleService.findById(roleHandler_Id));
            }
            roleService.update(role);
            roleService.save(role);
        }
    }

    private void setResourceChild() throws Exception {
        List<RoleRes2Resource> roleRes2Resources = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "resc_childs", RoleRes2Resource.class);
        List<Resource> childs = null;
        Resource resource = null;
        Resource childResource = null;
        for (int i = 1; i <= resourceService.countAll(Resource.class); i++) {
            resource = resourceService.findById(i);
            childs = new ArrayList<Resource>();
            for (RoleRes2Resource res2Resource : roleRes2Resources) {
                if (res2Resource.getResourceId().equals(i)) {
                    childResource = resourceService.findById(res2Resource.getChildId());
                    childs.add(childResource);
                }
            }
            if (childs != null) {
                resource.setChild(childs);
            }
        }
    }

    private void saveRoleReource() throws Exception {
        List<Role2Resource> role2Resources = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "role_resc", Role2Resource.class);
        List<Role> roles = roleService.findAll();
        List<RoleResource> roleResources = null;
        RoleResource roleResource = null;
        Resource resource = null;
        for (Role role : roles) {
            roleResources = new ArrayList<RoleResource>();
            for (Role2Resource role2Resource : role2Resources) {
                //System.out.println(role.getId().equals(role2Resource.getRole()));
                if (role.getId().equals(role2Resource.getRole())) {
                    resource = resourceService.findById(role2Resource.getResource());
                    roleResource = new RoleResource();
                    roleResource.setRole(role);
                    roleResource.setResource(resource);
                    roleResources.add(roleResource);
                    roleResourceService.save(roleResource);
                }
            }
            role.setRoleResource(roleResources);
        }
    }

    private void saveUser2Role() throws Exception {
        List<User2Role> user2roles = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "user_role", User2Role.class);
        List<User> users = userService.findAll();
        List<UserRole> userRoles = null;
        UserRole userRole = null;
        Role role = null;
        for (User user : users) {
            userRoles = new ArrayList<UserRole>();
            for (User2Role user2Role : user2roles) {
                if (user.getId().equals(user2Role.getUser())) {
                    role = roleService.findById(user2Role.getRole());
                    userRole = new UserRole();
                    userRole.setUser(user);
                    userRole.setRole(role);
                    userRoles.add(userRole);
                    userRoleService.save(userRole);
                }
            }
        }
    }

    private void saveUser() throws Exception {
        List<User> users = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "user", User.class);
        for (User user : users) {
            user.setPassword(CommonHelper.makeMD5(user.getPassword()));
            userService.save(user);
        }
    }

    /*private void saveEmployee() throws Exception{
        List<Employee> employeeWithFk=XlsDataSetBeanFactory.createBeans(this.getClass(),excelFile,"employee",Employee.class);
        User user=null;
        for(Employee employee:employeeWithFk){
            user=userService.uniqueResult("username",employee.getNo());
            employee.setUser(user);
            System.out.println(employee.getName());
            employeeService.save(employee);
        }
    }*/
    private void saveEmployee() throws Exception {
        User user = null;
        Department department = null;
        int index = 0;
        List<Employee> employees = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "employee", Employee.class);
        List<EmployeeWithFk> employeeWithFk = XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, "employee", EmployeeWithFk.class);
        for (Employee employee : employees) {
            user = userService.uniqueResult("username", employee.getNo());
            employee.setUser(user);
            Integer departmentId = employeeWithFk.get(index++).getDepartmentId();
            employee.setDepartment(departmentService.findById(departmentId));
            employeeService.save(employee);
        }
    }

    private void saveEntity(Class entity) throws Exception {
        for (Object t : listFormExcel(entity)) {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(t);
            entityManager.getTransaction().commit();
            entityManager.close();
        }
    }

    private List<Class> listFormExcel(Class entity) throws Exception {
        //获取类名
        String className = getClassName(entity);
        String tableName = CommonHelper.initialToLowerCase(className);
        //将excel中的数据转换为对象
        return (List<Class>) XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, tableName, entity);
    }

    private List<Class> listFormExcel(Class entity, String tableName) throws Exception {
        //获取类名
        String className = getClassName(entity);
        //将excel表中的数据转换为对象
        return (List<Class>) XlsDataSetBeanFactory.createBeans(this.getClass(), excelFile, tableName, entity);
    }

    private String getClassName(Class entity) {
        String className = entity.getName();
        //使用split分割字符串,通过.分割backage
        String regexStr = "\\.";
        //获取最后一个值，split返回一个字符串数组
        className = className.split(regexStr)[className.split(regexStr).length - 1];
        return className;
    }

    private void readList(Class entity) throws Exception {
        for (Object obj : listFormExcel(entity)) {
            System.out.println(obj);
        }
    }
}
