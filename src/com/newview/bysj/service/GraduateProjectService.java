package com.newview.bysj.service;

import com.newview.bysj.dao.*;
import com.newview.bysj.domain.*;
import com.newview.bysj.exception.MessageException;
import com.newview.bysj.helper.CommonHelper;
import com.newview.bysj.jpaRepository.MyRepository;
import com.newview.bysj.myAnnotation.MethodDescription;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Service("graduateProjectService")
public class GraduateProjectService extends BasicService<GraduateProject, Integer> {

    private static final Logger logger = Logger.getLogger(GraduateProjectService.class);
    GraduateProjectDao graduateProjectDao;
    @Autowired
    private AuditService auditService;
    @Autowired
    private TutorService tutorService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private ProjectTypeDao projectTypeDao;
    @Autowired
    private ProjectFromDao projectFromDao;
    @Autowired
    private ProjectFidelityDao projectFidelityDao;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private OpenningReportService openningReportService;
    @Autowired
    private MainTutorageService mainTutorageService;
    @Autowired
    private TaskDocService taskDocService;
    @Autowired
    private PaperProjectService paperProjectService;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<GraduateProject, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        graduateProjectDao = (GraduateProjectDao) basicDao;
    }

    @MethodDescription("获取通过教研室审核的所有课题")
    public Page<GraduateProject> getPageByAuditedDirector(Tutor director, Department department, Integer pageNo, Integer pageSize, Boolean approve) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        //获取department内的所有课题
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获取department内的所有课题
                predicates.add(cb.equal(root.get("proposer").get("department").as(Department.class), department));
                //已送审的才能进行审核
                predicates.add(cb.equal(root.get("proposerSubmitForApproval").as(Boolean.class), true));
                //未分配学生的课题
                predicates.add(cb.isNull(root.get("student").as(Student.class)));
                //不能审核自己的题目
                predicates.add(cb.notEqual(root.get("proposer").as(Tutor.class), director));
                //教研室审核通过的题目
                if (approve != null)
                    predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), approve));
                //获取当年的报题
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    /**
     * 查询当前教师所在教研室并且已被学生选择的课题
     *
     * @param tutor 教师
     * @return 课题的list集合
     */
    public List<GraduateProject> getProjectListByDepartmentWithStudent(Tutor tutor) {
        List<GraduateProject> graduateProjectList = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //查询当前教师所在教研室的课题
                Predicate predicate1 = criteriaBuilder.equal(root.get("major").get("department").as(Department.class), tutor.getDepartment());
                //查询已被学生选择的课题
                Predicate predicate2 = criteriaBuilder.isNotNull(root.get("student").as(Student.class));
                //查询当年的课题
                Predicate predicate3 = criteriaBuilder.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                criteriaQuery.where(criteriaBuilder.and(predicate1, predicate2, predicate3));
                //按照学生的学号升序排列
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("student").get("no").as(String.class)));
                return criteriaQuery.getRestriction();
            }
        });
        return graduateProjectList;
    }

    @MethodDescription("根据主指导获取所有课题")
    public Page<GraduateProject> getPageByMainTutorage(Tutor mainTutorage, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage);
                //教研室审核通过的课题
                Predicate c2 = cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true);
                //获取当年的报题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                //根据学生的学号升序排列
                query.orderBy(cb.asc(root.get("student").get("no").as(String.class)));
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("根据主指导和学生成功选题获取课题")
    public Page<GraduateProject> getPageBymainTutorageAndStudent(Tutor mainTutorage, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage);
                //学生选题
                Predicate c2 = cb.isNotNull(root.get("student").as(Student.class));
                //获取当年的报题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据title和是否有评阅人获取课题")
    public Page<GraduateProject> getPageByTitleAndReviewer(String title, String reviewerName, String hasReviewerName, Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获取教研室的所有课题
                predicates.add(cb.equal(root.get("proposer").get("department").get("id").as(Integer.class), departmentId));
                //学生不为空
                predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                //根据题目查询
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                //获取当年的报题
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                //根据评阅人获取
                if (reviewerName != null) {
                    predicates.add(cb.like(root.get("reviewer").get("name").as(String.class), "%" + reviewerName + "%"));
                } else {
                    if (hasReviewerName != null) {
                        if ("true".equals(hasReviewerName)) {
                            predicates.add(cb.isNotNull(root.get("reviewer").as(Tutor.class)));
                        } else if ("false".equals(hasReviewerName)) {
                            predicates.add(cb.isNull(root.get("reviewer").as(Tutor.class)));
                        }
                    }
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.desc(root.get("proposer").get("id").as(Integer.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("得到当前用户所在教研室所有老师的所有课题")
    public Page<GraduateProject> getPageByDepartment(Tutor proposer, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //获取当前用户教研室下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), proposer.getDepartment());
                //获取当年的课题
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                query.orderBy(cb.desc(root.get("proposer").get("id").as(Integer.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取当前用户所在教研室下的所有课题")
    public List<GraduateProject> getPageByDepartment(Department department) {
        List<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //获取教研室下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), department);
                //已被学生选择的课题
                Predicate c2 = cb.isNotNull(root.get("student").as(Student.class));
                //获取当年的课题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                query.orderBy(cb.desc(root.get("proposer").get("id")));
                return query.getRestriction();
            }
        });
        return result;
    }

    @MethodDescription("获取用户教研室内所有学生的课题")
    public Page<GraduateProject> getPageByDepartmentWithStudent(Tutor tutor, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //教研室内的所有课题
                Predicate c1 = cb.equal(root.get("proposer").get("department").as(Department.class), tutor.getDepartment());
                //学生不能为空
                Predicate c2 = cb.isNotNull(root.get("student").as(Student.class));
                //获得当年的报题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                query.orderBy(cb.desc(root.get("proposer").get("id")));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取用户教研室内所有学生的课题")
    public Page<GraduateProject> getPageByDepartmentWithStudentAndCondition(Tutor tutor, Integer pageNo, Integer pageSize, String studentNo, String studentName) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //教研室内的所有课题
                Predicate c1 = cb.equal(root.get("proposer").get("department").as(Department.class), tutor.getDepartment());
                //学生不能为空
                Predicate c2 = cb.isNotNull(root.get("student").as(Student.class));
                //获得当年的报题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                Predicate c4 = null;
                Predicate c5 = null;
                if (studentNo != null) {
                    c4 = cb.equal(root.get("student").get("name").as(String.class), studentNo);
                }
                if (studentName != null) {
                    c5 = cb.equal(root.get("student").get("no").as(String.class), studentName);
                }
                query.where(cb.and(c1, c2, c3, c4, c5));
                query.orderBy(cb.desc(root.get("proposer").get("id")));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("获取当前用户教研室内所有学生的课题")
    public List<GraduateProject> getPageByDepartmentWithStudent(Tutor tutor) {
        List<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                //教研室下的所有课题
                Predicate c1 = cb.equal(root.get("major").get("department").as(Department.class), tutor.getDepartment());
                //学生不为空
                Predicate c2 = cb.isNotNull(root.get("student").as(Student.class));
                //获取当年的课题
                Predicate c3 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2, c3));
                return query.getGroupRestriction();
            }
        });
        return result;
    }

    @MethodDescription("获取当前用户所申报的所有课题")
    public List<GraduateProject> getGraduateProjectByTutor(Tutor tutor) {
        List<GraduateProject> graduateProjectList = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("proposer").as(Tutor.class), tutor));
                Predicate[] p = new Predicate[predicates.size()];
                criteriaQuery.where(criteriaBuilder.and(predicates.toArray(p)));
                return criteriaQuery.getRestriction();
            }
        });
        return graduateProjectList;
    }

    @MethodDescription("在有限制条件下，获取当前用户教研室所有老师的课题")
    public Page<GraduateProject> getPageByLimit(Tutor tutor, Integer pageNo, Integer pageSize, HashMap<String, String> conditionMap) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //教研室下的所有课题
                predicates.add(cb.equal(root.get("major").get("department").as(Department.class), tutor.getDepartment()));
                //限制条件的查询
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    if (value != null) {
                        predicates.add(cb.like(root.get(key).as(String.class), "%" + value + "%"));
                    }
                }
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.orderBy(cb.desc(root.get("proposer").get("id").as(Integer.class)));
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("根据限选专业获得校优秀候选课题")
    public Page<GraduateProject> getPagesExcellentProjectByMajor(Tutor tutor, Integer pageNo, Integer pageSize, Boolean recommonded) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageNo = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("major").get("department").as(Department.class), tutor.getDepartment()));
                predicates.add(cb.equal(root.get("recommended").as(Boolean.class), recommonded));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("获取所有发布者发布的课题")
    public Page<GraduateProject> getPagesByProposer(Tutor proposer, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                Predicate c1 = cb.equal(root.get("proposer").as(Tutor.class), proposer);
                Predicate c2 = cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear());
                query.where(cb.and(c1, c2));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("获取发布者发布，并且学生是否选择的课题")
    public Page<GraduateProject> getPagesByProposerIfStudenSelected(Tutor proposer, boolean selected, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("proposer").as(Tutor.class), proposer));
                if (selected) {
                    predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                } else {
                    predicates.add(cb.isNull(root.get("student").as(Student.class)));
                }
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("不用分页，获取发布者发布，并且学生是否选择的课题")
    public List<GraduateProject> getAllProjectsByProposerIfStuentSelected(Tutor proposer, boolean selected) {
        List<GraduateProject> graduateProjectList = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("proposer").as(Tutor.class), proposer));
                if (selected) {
                    predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                } else {
                    predicates.add(cb.isNull(root.get("student").as(Student.class)));
                }
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        });
        return graduateProjectList;
    }

    @MethodDescription("根据title，教师姓名获取需要审核的课题")
    public Page<GraduateProject> getPagesByTitleAndProposerFromAuditByDirector(Tutor director, String tutorName, String title, Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获得教研室内所有课题
                //不能审核自己的题目
                predicates.add(cb.notEqual(root.get("proposer").as(Tutor.class), director));
                predicates.add(cb.and(cb.like(root.get("proposer").get("name").as(String.class), "%" + tutorName + "%"), cb.equal(root.get("proposer").get("department").get("id").as(Integer.class), departmentId)));
                //已经送审的才能进行审核
                predicates.add(cb.equal(root.get("proposerSubmitForApproval").as(Boolean.class), true));
                predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据title，教师姓名获得毕业论文明细表的课题")
    public Page<GraduateProject> getPagesByTitleAndProposerFromProjectByDirector(Tutor director, String title, String tutorName, Integer departmentId, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获得department内的课题
                predicates.add(cb.like(root.get("proposer").get("name").as(String.class), "%" + title + "%"));
                predicates.add(cb.equal(root.get("proposer").get("department").get("id").as(Integer.class), departmentId));
                predicates.add(cb.equal(root.get("proposerSubmitForApproval").as(Boolean.class), true));
                predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据title获取所有发布者的课题")
    public Page<GraduateProject> getPagesByProposerWithConditions(Tutor proposer, Integer pageNo, Integer pageSize, HashMap<String, String> conditionMap) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {

            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("proposer").as(Tutor.class), proposer));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    predicates.add(cb.like(root.get(key), "%" + value + "%"));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据title获取主指导所有课题")
    public Page<GraduateProject> getPagesByMainTutorageWithConditions(Tutor mainTutorage, Integer pageNo, Integer pageSize, HashMap<String, String> conditionMap) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage));
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    if (value != null) {
                        if (value.indexOf(",") >= 0) {
                            value = value.substring(0, value.length() - 1);
                        }
                        predicates.add(cb.like(root.get(key), "%" + value + "%"));
                    }
                }
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.asc(root.get("student").get("no").as(String.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }


    @MethodDescription("根据title和课题的类型获取主指导所有课题")
    public Page<GraduateProject> getPagesByMainTutorageWithConditionsAndCategory(String graduateProjectCategory, Tutor mainTutorage, Integer pageNo, Integer pageSize, HashMap<String, String> conditionMap) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("mainTutorage").get("tutor").as(Tutor.class), mainTutorage));
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    if (value != null) {
                        if (value.indexOf(",") >= 0) {
                            value = value.substring(0, value.length() - 1);
                        }
                        predicates.add(cb.like(root.get(key), "%" + value + "%"));
                    }
                }
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                if (graduateProjectCategory != null) {
                    predicates.add(cb.equal(root.get("category").as(String.class), graduateProjectCategory));
                }
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.asc(root.get("student").get("no").as(String.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("根据院长获取所有课题")
    public Page<GraduateProject> getPagesToDeanAudit(Tutor dean, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获得校内课题
                predicates.add(cb.equal(root.get("mainTutorage").get("tutor").get("department").get("school").as(School.class), dean.getDepartment().getSchool()));
                //已送审
                predicates.add(cb.isNotNull(root.get("proposerSubmitForApproval").as(Boolean.class)));
                //不能审核自己
                predicates.add(cb.notEqual(root.get("proposer").as(Tutor.class), dean));
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    /**
     * 对应功能：查询论文明细表
     * 适应需要根据课题名称查询的功能
     */
    @MethodDescription("根据学院获取全部课题")
    public Page<GraduateProject> getPagesBySchoolAndTitle(School school, String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //学院内所有课题
                predicates.add(cb.equal(root.get("major").get("department").get("school").as(School.class), school));
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                //已送审
                predicates.add(cb.isNotNull(root.get("proposerSubmitForApproval").as(Boolean.class)));
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                //已经选择学生的课题
                predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    /**
     * 对应功能：查询论文明细表
     * 适应需要根据课题名称查询的功能
     */
    @MethodDescription("根据title获取全部课题")
    public Page<GraduateProject> getpagesByTitle(String title, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //学院内所有课题
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                //已送审
                predicates.add(cb.isNotNull(root.get("proposerSubmitForApproval").as(Boolean.class)));
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                //已经选择学生的课题
                predicates.add(cb.isNotNull(root.get("student").as(Student.class)));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                return query.getRestriction();
            }

        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("根据答辩小组组长和title获取全部课题")
    public Page<GraduateProject> getPagesByReplyGroupLeaderWitdConditionMap(Tutor leader, HashMap<String, String> conditionMap, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("replyGroup").get("leader").as(Tutor.class), leader));
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    predicates.add(cb.like(root.get(key), "%" + value + "%"));
                }
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.asc(root.get("student").get("no").as(String.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("根据评阅人和title获取课题")
    public Page<GraduateProject> getPagesByReviewer(Tutor reviewer, HashMap<String, String> conditionMap, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(cb.equal(root.get("reviewer").as(Tutor.class), reviewer));
                for (String key : conditionMap.keySet()) {
                    String value = conditionMap.get(key);
                    if (value != null) {
                        predicates.add(cb.like(root.get(key), "%" + value + "%"));
                    }
                }
                //教研室审核通过
                predicates.add(cb.equal(root.get("auditByDirector").get("approve").as(Boolean.class), true));
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                query.where(cb.and(predicates.toArray(p)));
                query.orderBy(cb.asc(root.get("student").get("no").as(String.class)));
                return query.getRestriction();
            }
        }, new PageRequest(pageNo, pageSize));
        return result;
    }

    @MethodDescription("将对应的Form的action值加入Model中")
    public void addPostActionUrlToModel(ModelMap modelMap,
                                        HttpServletRequest request) {
        String postActionUrl = CommonHelper.getRequestUrl(request);
        modelMap.addAttribute("postActionUrl", postActionUrl);
    }

    @MethodDescription("获取校优候选人")
    public Page<GraduateProject> getPagesForSchoolExcellenceCandidate(School school, String title, String tutorName, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                predicates.add(cb.equal(root.get("major").get("department").get("school").as(School.class), school));
                predicates.add(cb.equal(root.get("recommended").as(Boolean.class), true));
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                if (tutorName != null) {
                    predicates.add(cb.like(root.get("proposer").get("name").as(String.class), "%" + tutorName + "%"));
                }
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("获取省优候选人")
    public Page<GraduateProject> getPagesForProvinceExcellentCandidate(String title, String tutorName, Integer pageNo, Integer pageSize) {
        pageNo = CommonHelper.getPageNo(pageNo, pageSize);
        pageSize = CommonHelper.getPageSize(pageSize);
        Page<GraduateProject> result = graduateProjectDao.findAll(new Specification<GraduateProject>() {
            @Override
            public Predicate toPredicate(Root<GraduateProject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // TODO Auto-generated method stub
                List<Predicate> predicates = new ArrayList<Predicate>();
                //获取被院级管理员推优的课题
                predicates.add(cb.equal(root.get("schoolExcellentProject").get("recommended"), true));
                ////获取是校级优秀的课题
                predicates.add(cb.isNotNull(root.get("schoolExcellentProject").as(SchoolExcellentProject.class)));
                if (title != null) {
                    predicates.add(cb.like(root.get("title").as(String.class), "%" + title + "%"));
                }
                if (title != null) {
                    predicates.add(cb.like(root.get("proposer").get("name").as(String.class), "%" + tutorName + "%"));
                }
                predicates.add(cb.equal(root.get("year").as(Integer.class), CommonHelper.getYear()));
                Predicate[] p = new Predicate[predicates.size()];
                return cb.and(predicates.toArray(p));
            }
        }, new PageRequest(pageNo, pageSize, new Sort(Direction.DESC, "id")));
        return result;
    }

    @MethodDescription("克隆一个课题")
    public void cloneProject(HttpSession httpSession, Integer projectIdToClone, GraduateProject newProject) {
        // TODO Auto-generated method stub
        //获得原Project
        GraduateProject originalProject = graduateProjectDao.findOne(projectIdToClone);
        //为新Project赋值
        //报题目人
        newProject.setProposer(originalProject.getProposer());
        //标题、副标题、 类别、 真实性、 来源、 年份
        newProject.setTitle(originalProject.getTitle());
        newProject.setSubTitle(originalProject.getSubTitle());
        newProject.setCategory(originalProject.getCategory());
        newProject.setProjectFrom(originalProject.getProjectFrom());
        newProject.setProjectFidelity(originalProject.getProjectFidelity());
        newProject.setYear(originalProject.getYear());
        newProject.setMajor(originalProject.getMajor());
        //内容
        newProject.setContent(originalProject.getContent());
        //基本要求
        newProject.setBasicRequirement(originalProject.getBasicRequirement());
        //基本技能
        newProject.setBasicSkill(originalProject.getBasicSkill());
        //参考文献
        newProject.setReference(originalProject.getReference());
        //由于客户要求，“申报即送审“，故直接将”送审“状态设为true
        newProject.setProposerSubmitForApproval(true);
        //送审就通过，需要创建另外一个Audit对象
        Audit auditByDirector = auditService.saveNewAudit();
        //设置当前用户的教研室主任为审批者
        auditByDirector.setAuditor(tutorService.getDirector((Tutor) CommonHelper.getCurrentActor(httpSession)));
        newProject.setAuditByDirector(auditByDirector);
        //持久化新Project
        graduateProjectDao.save(newProject);
    }

    /**
     * 增加新的或更新GraduateProject对象
     *
     * @param toEditProject 让Spring注入GraduateProject对象，当作超类对象处理。
     * @param httpSession
     * @param year          从JSP接受的year。或间接从addDesignProject获得
     * @return
     */
    public void addOrUpdateProject(GraduateProject toEditProject, HttpSession httpSession, Integer year, Integer majorId) {
        GraduateProject projectReadyToUpdateOrAdd = null;

        if (toEditProject.getId() == null) {//如果是新增加的题目，则Spring注入的对象要持久化
            projectReadyToUpdateOrAdd = toEditProject;
            //由于客户要求，“申报即送审“，故直接将”送审“状态设为true
            projectReadyToUpdateOrAdd.setProposerSubmitForApproval(true);
            //审核状态“送审即通过”
            Audit auditByDirector = auditService.saveNewAudit();
            //设置当前用户的教研室主任为审批者
            auditByDirector.setAuditor(tutorService.getDirector((Tutor) CommonHelper.getCurrentActor(httpSession)));
            projectReadyToUpdateOrAdd.setAuditByDirector(auditByDirector);

        } else {
            projectReadyToUpdateOrAdd = graduateProjectDao.findOne(toEditProject.getId());
            projectReadyToUpdateOrAdd.setTitle(toEditProject.getTitle());
            projectReadyToUpdateOrAdd.setSubTitle(toEditProject.getSubTitle());
            projectReadyToUpdateOrAdd.setProjectType(toEditProject.getProjectType());
            projectReadyToUpdateOrAdd.setProjectFidelity(toEditProject.getProjectFidelity());
            projectReadyToUpdateOrAdd.setProjectFrom(toEditProject.getProjectFrom());
            projectReadyToUpdateOrAdd.setContent(toEditProject.getContent());
            projectReadyToUpdateOrAdd.setBasicRequirement(toEditProject.getBasicRequirement());
            projectReadyToUpdateOrAdd.setBasicSkill(toEditProject.getBasicSkill());
            projectReadyToUpdateOrAdd.setReference(toEditProject.getReference());

            //审核状态“送审即通过”。不改变审批者，也不能改变审批日期
            Audit auditByDirector = projectReadyToUpdateOrAdd.getAuditByDirector();
            auditByDirector.setApprove(true);
        }

        //用jsp传入的year给project的year赋值
        projectReadyToUpdateOrAdd.setYear(year);
        //清空当前project的限选专业，以方便更新
        projectReadyToUpdateOrAdd.setMajor(null);
        //题目设置报题人
        projectReadyToUpdateOrAdd.setProposer((Tutor) CommonHelper.getCurrentActor(httpSession));
        /*//持久化
        this.saveOrUpdate(projectReadyToUpdateOrAdd);*/
        //题目设置限选专业
        projectReadyToUpdateOrAdd.setMajor(majorService.findById(majorId));

        this.saveOrUpdate(projectReadyToUpdateOrAdd);
    }

    //准备基础数据列表：学院、题目类型、题目性质、题目来源
    private void prepareProjectAssObjectToModel(ModelMap modelMap) {
        //获得所有题目来源
        List<ProjectFrom> projectFromList = projectFromDao.findAll();
        List<ProjectType> projectTypeList = projectTypeDao.findAll();
        List<ProjectFidelity> projectFidelityList = projectFidelityDao.findAll();
        //获得所有学院
        List<School> schoolList = schoolDao.findAll();
        modelMap.addAttribute("schoolList", schoolList);
        modelMap.addAttribute("majorList", majorService.findAll());
        modelMap.addAttribute("projectFromList", projectFromList);
        modelMap.addAttribute("projectTypeList", projectTypeList);
        modelMap.addAttribute("projectFidelityList", projectFidelityList);
    }


    // private static final Logger logger = Logger.getLogger(GraduateProjectService.class);

    /**
     * 准备增加或修改GraduateProject对象.因为不涉及具体类型之间的差异--开题报告，故不区分具体类型。
     *
     * @param session
     * @param modelMap
     * @param projectIdToEdit 要修改的GraduateProject
     * @return
     */
    public void toAddOrUpdateProject(HttpSession session,
                                     ModelMap modelMap,
                                     Integer projectIdToEdit) {
        //题目的限选专业
        Major selectedMajor = null;
//		题目可能是已经存的，需要修改。也可能是新增加的。
        GraduateProject toEditProject = null;
        //获得要修改的题目
        if (projectIdToEdit == null) {
            //新增加的project,从model中获取
            toEditProject = (GraduateProject) modelMap.get("project");
            //当前用户默认的限选专业
            //logger.debug("tutor------:" + (Tutor) CommonHelper.getCurrentActor(session));
            selectedMajor = majorService.getTutorDefaultMajor((Tutor) CommonHelper.getCurrentActor(session));
            //logger.debug("select-----------" + selectedMajor);
            //logger.debug("selectMajor=====================" + selectedMajor.getDescription());
        } else {
            //已经存在的题目，需要用id从数据库中获取
            toEditProject = graduateProjectDao.findOne(projectIdToEdit);
            //获得题目的限选专业
            selectedMajor = toEditProject.getMajor();
        }
        //logger.debug("------" + toEditProject.getTitle() + "   " + toEditProject.getContent());
        //往model中存入基础信息列表
        this.prepareProjectAssObjectToModel(modelMap);
        //要修改的project存入model，供jsp调用
        modelMap.addAttribute("toEditProject", toEditProject);
        //要修改的selectedMajors存入model，供jsp调用
        modelMap.addAttribute("selectedMajor", selectedMajor);

    }

    public void delete(Integer id) {
        GraduateProject graduateProject = this.findById(id);
        Student student = graduateProject.getStudent();
        if (student != null) {
            throw new MessageException("您要删除的课题已经分配给学生:" + student.getName() + "(" + student.getNo() + ")，不能删除");
        }
        Boolean test = graduateProject instanceof PaperProject;


        //删除论文题目的时候需要删除与它双向关联的开题报告。
        if (graduateProject instanceof PaperProject) {

            logger.error("-------------true or false");
            logger.error(graduateProject instanceof PaperProject);
            PaperProject paperProject = (PaperProject) graduateProject;
            if (paperProject.getOpenningReport() != null) {
                OpenningReport openningReport = ((PaperProject) graduateProject).getOpenningReport();
                openningReport.setPaperProject(null);
                openningReportService.saveOrUpdate(openningReport);
                ((PaperProject) graduateProject).setOpenningReport(null);
                paperProjectService.saveOrUpdate((PaperProject) graduateProject);
                openningReportService.deleteObject(openningReport);
            }
        }
        if (graduateProject.getMainTutorage() != null) {
            //取消与maintutorage的双向关联并删除主指导
            MainTutorage mainTutorage = graduateProject.getMainTutorage();
            mainTutorage.setGraduateProject(null);
            mainTutorage.setTutor(null);
            mainTutorageService.saveOrUpdate(mainTutorage);
            graduateProject.setMainTutorage(null);
            this.saveOrUpdate(graduateProject);
            mainTutorageService.deleteObject(mainTutorage);
        }
        if (graduateProject.getTaskDoc() != null) {
            TaskDoc taskDoc = graduateProject.getTaskDoc();
            taskDoc.setGraduateProject(null);
            taskDocService.saveOrUpdate(taskDoc);
            graduateProject.setTaskDoc(null);
            this.saveOrUpdate(graduateProject);
            taskDocService.deleteObject(taskDoc);

        }
        graduateProjectDao.delete(graduateProject);

    }


}
