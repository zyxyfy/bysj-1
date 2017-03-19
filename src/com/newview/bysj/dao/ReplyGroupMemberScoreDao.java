package com.newview.bysj.dao;

import org.springframework.data.jpa.repository.Query;

import com.newview.bysj.domain.ReplyGroupMemberScore;
import com.newview.bysj.jpaRepository.MyRepository;

public interface ReplyGroupMemberScoreDao extends MyRepository<ReplyGroupMemberScore, Integer> {
    @Query("select max(in_dex) from ReplyGroupMemberScore")
    public Integer findIndex();
}
