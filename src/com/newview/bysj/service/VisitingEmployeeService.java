package com.newview.bysj.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.DepartmentDao;
import com.newview.bysj.dao.GraduateProjectDao;
import com.newview.bysj.dao.ReplyGroupDao;
import com.newview.bysj.dao.StudentDao;
import com.newview.bysj.dao.UserDao;
import com.newview.bysj.dao.UserRoleDao;
import com.newview.bysj.dao.VisitingEmployeeDao;
import com.newview.bysj.domain.Department;
import com.newview.bysj.domain.GraduateProject;
import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.domain.School;
import com.newview.bysj.domain.Student;
import com.newview.bysj.domain.Tutor;
import com.newview.bysj.domain.User;
import com.newview.bysj.domain.UserRole;
import com.newview.bysj.domain.VisitingEmployee;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

@Service("visitingEmployeeService")
public class VisitingEmployeeService extends BasicService<VisitingEmployee, Integer> {

    VisitingEmployeeDao visitingEmployeeDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private RoleService roleServce;
    @Autowired
    private StudentService studentService;
    @Autowired
    private GraduateProjectService graduateProjectService;
    @Autowired
    private ReplyGroupDao replyGroupDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<VisitingEmployee, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        visitingEmployeeDao = (VisitingEmployeeDao) basicDao;
    }

    @MethodDescription("根据学院获得职工")
    public Page<VisitingEmployee> getVisitingEmployeeBySchool(Integer pageNo, Integer pageSize, Integer schoolId) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new Specification<VisitingEmployee>() {
            @Override
            public Predicate toPredicate(Root<VisitingEmployee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                SetJoin<VisitingEmployee, Department> depJoin = root.join(root.getModel().getSet("department", Department.class), JoinType.LEFT);
                Predicate condition = cb.equal(depJoin.get("school").get("id").as(Integer.class), schoolId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort("ASC", "no")));
        return result;

    }

    @MethodDescription("根据教研室获得职工")
    public Page<VisitingEmployee> getVisitingEmployeeByDepartment(Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Department department = departmentDao.findOne(departmentId);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new Specification<VisitingEmployee>() {
            @Override
            public Predicate toPredicate(Root<VisitingEmployee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("department").as(Department.class), department);
                query.where(condition);
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("获取全部职工")
    public Page<VisitingEmployee> getAll(Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据职工号，职工姓名，学院获得职工")
    public Page<VisitingEmployee> getVisitingEmployeeBySchool(Integer pageNo, Integer pageSize, String visitingEmployeeNo, String visitingEmployeeName, Integer schoolId) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new Specification<VisitingEmployee>() {
            @Override
            public Predicate toPredicate(Root<VisitingEmployee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                SetJoin<VisitingEmployee, Department> depJoin = root.join(root.getModel().getSet("department", Department.class), JoinType.LEFT);
                Predicate c1 = cb.equal(depJoin.get("school").get("id").as(Integer.class), schoolId);
                if (visitingEmployeeNo != null && visitingEmployeeName != null) {
                    Predicate c2 = cb.and(cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%"), cb.like(root.get("name"), "%" + visitingEmployeeName + "%"));
                    query.where(cb.and(c1, c2));
                } else if (visitingEmployeeNo == null && visitingEmployeeName != null) {
                    Predicate c3 = cb.like(root.get("name").as(String.class), "%" + visitingEmployeeName + "%");
                    query.where(cb.and(c1, c3));
                } else if (visitingEmployeeNo != null && visitingEmployeeName == null) {
                    Predicate c4 = cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%");
                    query.where(cb.and(c1, c4));
                }
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据职工号，职工姓名，教研室获得职工")
    public Page<VisitingEmployee> getVisitingEmployeeByDepartment(Integer pageNo, Integer pageSize, String visitingEmployeeNo, String visitingEmployeeName, Integer departmentId) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Department department = departmentDao.findOne(departmentId);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new Specification<VisitingEmployee>() {
            @Override
            public Predicate toPredicate(Root<VisitingEmployee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("department").as(Department.class), department);
                if (visitingEmployeeNo != null && visitingEmployeeName != null) {
                    Predicate c2 = cb.and(cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%"), cb.like(root.get("name"), "%" + visitingEmployeeName + "%"));
                    query.where(cb.and(c1, c2));
                } else if (visitingEmployeeNo == null && visitingEmployeeName != null) {
                    Predicate c3 = cb.like(root.get("name").as(String.class), "%" + visitingEmployeeName + "%");
                    query.where(cb.and(c1, c3));
                } else if (visitingEmployeeNo == null && visitingEmployeeName != null) {
                    Predicate c4 = cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%");
                    query.where(cb.and(c1, c4));
                }
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据职工号，职工姓名获得职工")
    public Page<VisitingEmployee> getVisitingEmployees(String visitingEmployeeNo, String visitingEmployeeName, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<VisitingEmployee> result = visitingEmployeeDao.findAll(new Specification<VisitingEmployee>() {
            @Override
            public Predicate toPredicate(Root<VisitingEmployee> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (visitingEmployeeName != null && visitingEmployeeNo != null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + visitingEmployeeName + "%"));
                    ;
                } else if (visitingEmployeeNo != null && visitingEmployeeName == null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + visitingEmployeeNo + "%"));
                } else if (visitingEmployeeNo == null && visitingEmployeeName != null) {
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + visitingEmployeeName + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("添加VisitingEmployee")
    public void saveVisitingEmployee(VisitingEmployee visitingEmployee) {
        User user = new User();
        user.setUsername(visitingEmployee.getNo());
        user.setPassword(CommonHelper.makeMD5(visitingEmployee.getNo()));
        user.setUserRole(new ArrayList<UserRole>());
        userDao.save(user);
        visitingEmployee.setUser(user);
        visitingEmployeeDao.merge(visitingEmployee);
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(roleServce.uniqueResult("description", "指导教师"));
        userRoleDao.save(userRole);
    }

    @MethodDescription("删除VisitingEmployee")
    public void delete(VisitingEmployee visitingEmployee) {
        visitingEmployee.getDepartment().getTutor().remove(visitingEmployee);
        visitingEmployee.setDepartment(null);
        for (Student student : visitingEmployee.getStudent() == null ? new HashSet<Student>() : visitingEmployee.getStudent()) {
            student.setTutor(null);
            student.setGraduateProject(null);
            studentService.saveOrUpdate(student);
        }
        visitingEmployee.setStudent(null);
        for (GraduateProject graduateProject : visitingEmployee.getProposedGraduateProject() == null ? new HashSet<GraduateProject>() : visitingEmployee.getProposedGraduateProject()) {
            graduateProject.getReplyGroup().getGraduateProject().remove(graduateProject);
            graduateProject.setReplyGroup(null);
            graduateProject.getReviewer().getGraduateProjectToReview().remove(graduateProject);
            graduateProject.setReviewer(null);
            graduateProject.setStudent(null);
            graduateProjectService.deleteObject(graduateProject);
        }
        visitingEmployee.setProposedGraduateProject(null);
        for (GraduateProject graduateProject : visitingEmployee.getGraduateProjectToReview() == null ? new HashSet<GraduateProject>() : visitingEmployee.getGraduateProjectToReview()) {
            graduateProject.setReviewer(null);
            graduateProjectService.saveOrUpdate(graduateProject);
        }
        visitingEmployee.setGraduateProjectToReview(null);
        for (ReplyGroup replyGroup : visitingEmployee.getReplyGroup() == null ? new HashSet<ReplyGroup>() : visitingEmployee.getReplyGroup()) {
            replyGroup.getMembers().remove(visitingEmployee);
        }
        visitingEmployee.setReplyGroup(null);
        for (ReplyGroup replyGroup : visitingEmployee.getTutorAsLeaderToReplyGroups() == null ? new HashSet<ReplyGroup>() : visitingEmployee.getTutorAsLeaderToReplyGroups()) {
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
        visitingEmployee.setTutorAsLeaderToReplyGroups(null);
        User user = visitingEmployee.getUser();

        for (UserRole userRole : user.getUserRole()) {
            userRole.getRole().getUserRole().remove(userRole);
            userRole.setRole(null);
            userRole.setUser(null);
            userRoleDao.delete(userRole);
        }

        user.setUserRole(null);
        user.setActor(null);
        visitingEmployee.setUser(null);
        userDao.delete(user);
        visitingEmployeeDao.delete(visitingEmployee);

    }

}
