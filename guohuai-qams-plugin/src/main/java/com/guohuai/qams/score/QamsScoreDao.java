package com.guohuai.qams.score;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.guohuai.qams.que.QamsQue;

public interface QamsScoreDao extends JpaRepository<QamsScore, String>, JpaSpecificationExecutor<QamsScore> {


	@Query("from QamsScore q where q.qamsQue.sid=:qid")
	public List<QamsScore> findAllByQueSid(@Param("qid")String qid);
}

