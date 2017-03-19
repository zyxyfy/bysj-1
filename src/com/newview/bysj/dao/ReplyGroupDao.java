package com.newview.bysj.dao;

import com.newview.bysj.domain.ReplyGroup;
import com.newview.bysj.jpaRepository.MyRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyGroupDao extends MyRepository<ReplyGroup, Integer> {
    @Query("select max(num) from ReplyGroup")
    Integer getNum();
}
