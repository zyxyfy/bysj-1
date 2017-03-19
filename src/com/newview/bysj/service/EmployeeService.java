package com.newview.bysj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.EmployeeDao;
import com.newview.bysj.dao.GraduateProjectDao;
import com.newview.bysj.dao.ReplyGroupDao;
import com.newview.bysj.dao.StudentDao;
import com.newview.bysj.dao.UserDao;
import com.newview.bysj.dao.UserRoleDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.Employee;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("employeeService")
public class EmployeeService extends BasicService<Employee, Integer> {

    EmployeeDao employeeDao;
    @Autowired
    UserDao userDao;
    @Autowired
    RoleService roleService;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    StudentService studentService;
    @Autowired
    GraduateProjectService graduateProjectService;
    @Autowired
    ReplyGroupDao replyGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Employee, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        employeeDao = (EmployeeDao) basicDao;
    }

    @MethodDescription("通过学院获得校内职工")
    public Page<Employee> getEmployeeBySchool(Integer schoolId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = employeeDao.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").get("school").get("id").as(Integer.class), schoolId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("通过教研室获得校内职工")
    public Page<Employee> getEmployeeByDepartment(Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = employeeDao.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").get("id").as(Integer.class), departmentId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("获取全部校内职工")
    public Page<Employee> getEmployeeByCollege(Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = this.pageQuery(pageNo, pageSize, Direction.ASC, "no");
        return result;
    }

    @MethodDescription("根据职工号，职工姓名，学院获得全部校内职工")
    public Page<Employee> getEmployeeBySchool(String employeeNo, String employeeName, Integer schoolId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = employeeDao.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (employeeNo != null && employeeName != null) {
                    predicates.add(cb.equal(root.get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                } else if (employeeNo != null && employeeName == null) {
                    predicates.add(cb.equal(root.get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                } else if (employeeNo == null && employeeName != null) {
                    predicates.add(cb.equal(root.get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("department").get("school").get("id").as(Integer.class), schoolId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据职工号，职工姓名，教研室获得校内全部职工")
    public Page<Employee> getEmployeeByDepartment(String employeeNo, String employeeName, Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = employeeDao.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (employeeNo != null && employeeName != null) {
                    predicates.add(cb.equal(root.get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                } else if (employeeNo != null && employeeName == null) {
                    predicates.add(cb.equal(root.get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                } else if (employeeNo == null && employeeName != null) {
                    predicates.add(cb.equal(root.get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("department").get("id").as(Integer.class), departmentId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据职工号，职工姓名获得校内全部职工")
    public Page<Employee> getEmployees(String employeeNo, String employeeName, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Employee> result = employeeDao.findAll(new Specification<Employee>() {
            @Override
            public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (employeeNo != null && employeeName != null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                } else if (employeeNo != null && employeeName == null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + employeeNo + "%"));
                } else if (employeeNo == null && employeeName != null) {
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + employeeName + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

    @MethodDescription("添加校内职工")
    public void saveEmployee(Employee employee) {
        this.save(employee);
        User user = new User();
        user.setUsername(employee.getNo());
        user.setPassword(CommonHelper.makeMD5(employee.getNo()));
        user.setActor(employee);
        user.setUserRole(new ArrayList<UserRole>());
        userDao.save(user);
        employee.setUser(user);
        this.update(employee);
        UserRole userRole = new UserRole();
        userRole.setRole(roleService.uniqueResult("no", "0206"));
        userRole.setUser(user);
        userRoleDao.save(userRole);
        user.getUserRole().add(userRole);
    }

    @MethodDescription("删除校内职工")
    public void deleteEmployee(Employee employee) {
        employee.getDepartment().getTutor().remove(employee);
        employee.setDepartment(null);
        for (Student student : employee.getStudent() == null ? new HashSet<Student>() : employee.getStudent()) {
            student.setTutor(null);
            student.setGraduateProject(null);
            studentService.update(student);
            //对更新状态进行保存
            studentService.save(student);
        }
        employee.setStudent(null);
        //删除该教师的所有课题
        for (GraduateProject graduateProject : employee.getProposedGraduateProject() == null ? new HashSet<GraduateProject>() : employee.getProposedGraduateProject()) {
            graduateProject.setReplyGroup(null);
            graduateProject.setReviewer(null);
            graduateProject.setStudent(null);
            graduateProjectService.deleteObject(graduateProject);
        }
        employee.setProposedGraduateProject(null);
        //删除教师评阅的课题
        employee.setGraduateProjectToReview(null);
        for (GraduateProject graduateProject : employee.getGraduateProjectToReview() == null ? new HashSet<GraduateProject>() : employee.getGraduateProjectToReview()) {
            graduateProject.setReviewer(null);
            graduateProjectService.saveOrUpdate(graduateProject);
        }
        //如果该用户作为答辩小组组长时，移除。
        employee.setTutorAsLeaderToReplyGroups(null);
        for (ReplyGroup replyGroup : employee.getTutorAsLeaderToReplyGroups() == null ? new HashSet<ReplyGroup>() : employee.getTutorAsLeaderToReplyGroups()) {
            replyGroup.getDepartment().getReplyGroup().remove(replyGroup);
            replyGroup.setDepartment(null);
            for (Tutor tempTutor : replyGroup.getMembers()) {
                tempTutor.getReplyGroup().remove(replyGroup);
            }
            replyGroup.setMembers(null);
            for (GraduateProject graduateProject : replyGroup.getGraduateProject() == null ? new HashSet<GraduateProject>() : replyGroup.getGraduateProject()) {
                graduateProject.setReplyGroup(null);
                graduateProjectService.saveOrUpdate(graduateProject);
            }
            replyGroup.setGraduateProject(null);
            replyGroupDao.delete(replyGroup);
        }
        //当为答辩小组成员是移除该用户
        employee.setReplyGroup(null);
        //删除user和Role的关系
        User user = employee.getUser();
        for (UserRole userRole : user.getUserRole()) {
            userRole.getRole().getUserRole().remove(userRole);
            userRole.setRole(null);
            userRole.setUser(null);
            userRoleDao.delete(userRole);
        }
        user.setUserRole(null);
        user.setActor(null);
        employee.setUser(null);
        userDao.delete(user);
        employeeDao.delete(employee);

    }

}
