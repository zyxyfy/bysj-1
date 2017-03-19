package com.newview.bysj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newview.bysj.dao.QuizDao;
import com.newview.bysj.domain.Quiz;
import com.newview.bysj.jpaRepository.MyRepository;

@Service("quizService")
public class QuizService extends BasicService<Quiz, Integer> {

    QuizDao quizDao;

    @Override
    @Autowired
    public void setDasciDao(MyRepository<Quiz, Integer> basicDao) {
        this.basicDao = basicDao;
        quizDao = (QuizDao) basicDao;
    }

}
