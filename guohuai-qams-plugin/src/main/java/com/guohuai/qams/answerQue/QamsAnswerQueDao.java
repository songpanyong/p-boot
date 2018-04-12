package com.guohuai.qams.answerQue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface QamsAnswerQueDao extends JpaRepository<QamsAnswerQue, String>, JpaSpecificationExecutor<QamsAnswerQue> {

	@Query(value = "select * from T_QAMS_ANSWER_QUE where issue_id = ?1", nativeQuery = true)
	QamsAnswerQue getQamsAnswerQue(String sid);

	@Query(value = "select * from T_QAMS_ANSWER_QUE where que_id = ?1 and author_id = ?2", nativeQuery = true)
	QamsAnswerQue getAnswerQueByUser(String queId,String loginUser);


}
