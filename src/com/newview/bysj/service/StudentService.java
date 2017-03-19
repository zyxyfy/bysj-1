package com.newview.bysj.service;

import com.newview.bysj.dao.StudentDao;
import com.newview.bysj.domain.*;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("studentService")
public class StudentService extends BasicService<Student, Integer> {
    //private final static Logger logger = Logger.getLogger(StudentService.class);
    StudentDao studentDao;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private GraduateProjectService graduateProjectService;

    @Autowired
    @Override
    public void setDasciDao(MyRepository<Student, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        this.studentDao = (StudentDao) basicDao;
    }

    @MethodDescription("获取全部的学生")
    public Page<Student> getStudentByCollege(Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = this.pageQuery(pageNo, pageSize, Direction.ASC, "no");
        return result;
    }

    @MethodDescription("根据学院获得学生")
    public Page<Student> getStudentBySchool(Integer schoolId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("studentClass").get("major").get("department").get("school").get("id").as(Integer.class), schoolId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据教研室获得学生")
    public Page<Student> getStudentByDepartment(Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("studentClass").get("major").get("department").get("id").as(Integer.class), departmentId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据专业获得学生")
    public Page<Student> getStudentByMajor(Integer majorId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("studentClass").get("major").get("id").as(Integer.class), majorId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据班级获得学生")
    public Page<Student> getStudentByStudentClass(Integer studentClassId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

    @MethodDescription("根据学号，学生姓名，学院获得学生")
    public Page<Student> getStudentBySchool(String studentNo, String studentName, Integer schoolId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (studentNo != null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else if (studentNo != null && studentName == null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                } else if (studentNo == null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("school").get("id").as(Integer.class), schoolId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("school").get("id").as(Integer.class), schoolId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据学号，姓名，教研室查询学生")
    public Page<Student> getStudentByDepartment(String studentNo, String studentName, Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (studentNo != null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else if (studentNo != null && studentName == null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                } else if (studentNo == null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("id").as(Integer.class), departmentId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("department").get("id").as(Integer.class), departmentId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

    @MethodDescription("根据学号，姓名，专业获取学生")
    public Page<Student> getStudentByMajor(String studentNo, String studentName, Integer majorId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (studentNo != null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("id").as(Integer.class), majorId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else if (studentNo != null && studentName == null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("id").as(Integer.class), majorId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                } else if (studentNo == null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("id").as(Integer.class), majorId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("studentClass").get("major").get("id").as(Integer.class), majorId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据学生学号，学生姓名，班级获取学生")
    public Page<Student> getStudentByStudentClass(String studentNo, String studentName, Integer studentClassId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (studentNo != null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else if (studentNo != null && studentName == null) {
                    predicates.add(cb.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId));
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                } else if (studentNo == null && studentName != null) {
                    predicates.add(cb.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else {
                    predicates.add(cb.equal(root.get("studentClass").get("id").as(Integer.class), studentClassId));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据学号，姓名获取学生")
    public Page<Student> getStudents(String studentNo, String studentName, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                if (studentNo != null && studentName != null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                } else if (studentNo != null && studentName == null) {
                    predicates.add(cb.like(root.get("no").as(String.class), "%" + studentNo + "%"));
                } else if (studentNo == null && studentName != null) {
                    predicates.add(cb.like(root.get("name").as(String.class), "%" + studentName + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "no")));
        return result;
    }

    @MethodDescription("根据教研室获取学生，为分配学生功能服务")
    public List<Student> getStudentsByDepartmentWithoutTutor(Department department, Boolean hasTutor, HashMap<String, String> queryMap) {
        List<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("studentClass").get("major").get("department").as(Department.class), department));
                //hasTutor为null获取本教研室的所有学生，如果为true获取已分配老师的学生，如果为false获取为未分配老师的学生
                if (hasTutor != null) {
                    if (hasTutor) {
                        predicates.add(cb.isNotNull(root.get("tutor").as(Tutor.class)));
                    } else {
                        predicates.add(cb.isNull(root.get("tutor").as(Tutor.class)));
                    }
                }
                //predicates.add(cb.isNull(root.get("tutor").as(Tutor.class)));
                if (queryMap != null) {
                    for (String key : queryMap.keySet()) {
                        String value = queryMap.get(key);
                        if (value != null) {
                            predicates.add(cb.like(root.get(key).as(String.class), "%" + value + "%"));
                        }
                    }
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.asc(root.get("no").as(String.class)));
                return query.getRestriction();
            }
        });
        return result;
    }

    @MethodDescription("根据教师获取学生")
    public Page<Student> getPagesByMainTutorage(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate condition = cb.equal(root.get("tutor").as(Tutor.class), tutor);
                query.where(condition);
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.ASC, "id")));
        return result;
    }

    @MethodDescription("获取可以用来匹配学生的数据")
    public List<Student> getPagesByTutor(Tutor tutor, boolean selected, HashMap<String, String> queryMap) {
        List<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("tutor").as(Tutor.class), tutor));
                //添加模糊查询语句
                if (queryMap != null) {
                    //添加模糊查询条件
                    for (String key : queryMap.keySet()) {
                        String value = queryMap.get(key);
                        if (value != null) {
                            predicates.add(cb.like(root.get(key), "%" + value + "%"));
                        }
                    }
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        });
        //声明数组，用以存储该老师下的所有学生
        List<Student> students = new ArrayList<Student>();
        //logger.debug(selected);
        if (!selected) {
            //获取没有选择课题的学生
            for (Student student : result) {
                if (student.getGraduateProject() == null)
                    students.add(student);
            }
        } else {
            //获取已经选择课题的学生
            for (Student student : result) {
                if (student.getGraduateProject() != null)
                    students.add(student);
            }
        }
        return students;
    }

    @MethodDescription("查看老师的学生，只显示当前年份")
    public List<Student> getStudentsOfTutor(Tutor tutor, Integer year) {
        List<Student> result = studentDao.findAll(new Specification<Student>() {
            @Override
            public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("tutor").as(Tutor.class), tutor);
                Predicate c2 = cb.equal(root.get("graduateProject").get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        });
        return result;
    }

    @MethodDescription("保存student")
    public void saveStudent(Student student) {
        // TODO Auto-generated method stub
        //studentDao.save(student);
        //获取保存后的student否则更新student会出错
        //student=this.uniqueResult("no",student.getNo());
        User user = new User();
        user.setUsername(student.getNo());
        user.setPassword(CommonHelper.makeMD5(student.getNo()));
        user.setActor(student);
        user.setUserRole(new ArrayList<UserRole>());
        //保存user同时级联保存student
        userService.save(user);
        //获取保存后的user否则保存userRole会出错
        user = userService.uniqueResult("username", user.getUsername());
        //获取保存后的student否则更新student会出错
        student = this.uniqueResult("no", student.getNo());
        student.setUser(user);
        this.saveOrUpdate(student);
        UserRole userRole = new UserRole();
        userRole.setRole(roleService.uniqueResult("description", "学生"));
        userRole.setUser(user);
        userRoleService.save(userRole);
        user.getUserRole().add(userRole);
    }

    @MethodDescription("删除student")
    public void deleteStudent(Student student) {
        // TODO Auto-generated method stub
        if (student.getGraduateProject() != null) {
            GraduateProject graduateProject = student.getGraduateProject();
            graduateProject.setStudent(null);
            graduateProjectService.saveOrUpdate(graduateProject);
        }
        student.getStudentClass().getStudent().remove(student);
        student.setStudentClass(null);
        if (student.getTutor() != null) {
            student.getTutor().getStudent().remove(student);
            student.setTutor(null);
        }
        User user = student.getUser();
        for (UserRole userRole : user.getUserRole()) {
            userRole.getRole().getUserRole().remove(userRole);
            userRole.setRole(null);
            userRole.setUser(null);
            userRoleService.saveOrUpdate(userRole);
            userRoleService.deleteObject(userRole);
        }
        user.setUserRole(null);
        user.setActor(null);
        student.setUser(null);
        userService.deleteObject(user);
        studentDao.delete(student);
    }
}

