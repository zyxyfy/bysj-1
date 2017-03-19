package com.newview.bysj.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.dbunit.database.ResultSetTableMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.ReplyGroupMemberScoreDao;
import com.newview.bysj.domain.ReplyGroupMemberScore;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("replyGroupMemberScoreService")
public class ReplyGroupMemberScoreService extends BasicService<ReplyGroupMemberScore, Integer> {

    EntityManagerFactory emf = null;
    ReplyGroupMemberScoreDao replyGroupMemberScoreDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<ReplyGroupMemberScore, Integer> basicDao) {
        // TODO Auto-generated method stub
        this.basicDao = basicDao;
        replyGroupMemberScoreDao = (ReplyGroupMemberScoreDao) basicDao;
    }


    //根据老师的id和答辩小组的id来获取唯一的结果
    public ReplyGroupMemberScore getUniqueResult(Integer tutorId, Integer replyGroupId) {
        return null;
    }

    public ResultSet getAllValuas() throws Exception {
        /*EntityManager em = replyGroupMemberScoreDao.getEntityManager();
		String sql = "select max(in_dex) from replyGroupMemberScore";
		System.out.println("000000000000");
		Query query = em.createQuery(sql);
		System.out.println("------");
		List<String> ins = query.getResultList();
		System.out.println("======");*/
        String driver = "com.mysql.jdbc.Driver";
        String username = "root";
        String password = "1234";
        String url = "jdbc:mysql://localhost:3306/bysj3?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&ampjdbc:mysql://localhost:3306/bysj3?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8&amp";

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement sta = conn.createStatement();
        String sql = "select max(in_dex) from replyGroupMemberScore";
        ResultSet rs = sta.executeQuery(sql);

        return rs;
    }

    public Integer findIn_dex() {
        Integer index = replyGroupMemberScoreDao.findIndex();
        return index;
    }


}
