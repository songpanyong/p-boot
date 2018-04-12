package com.guohuai.qams.answerRecord;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface QamsAnswerRecordDao extends JpaRepository<QamsAnswerRecord, String>, JpaSpecificationExecutor<QamsAnswerRecord> {

	@Query(value = "select count(*) from T_QAMS_ANSWER_RECORD where an_issue_id = ?1", nativeQuery = true)
	BigDecimal getTotal(String sid);

	@Query(value = "select count(*) from T_QAMS_ANSWER_RECORD where an_issue_id = ?1 and answer_id = ?2", nativeQuery = true)
	BigDecimal getNumber(String qid, String sid);

	@Query(value = "select * from T_QAMS_ANSWER_RECORD where an_issue_id = ?1", nativeQuery = true)
	QamsAnswerRecord getByIssue(String sid);



}
