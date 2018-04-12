package com.guohuai.qams.answerIssue;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface QamsAnswerIssueDao extends JpaRepository<QamsAnswerIssue, String>, JpaSpecificationExecutor<QamsAnswerIssue> {

	@Query(value = "select * from T_QAMS_ANSWER_ISSUE where issue_id = ?1 and que_id = ?2 and author_id = ?3", nativeQuery = true)
	QamsAnswerIssue getByIssue(String issueId,String answerQueId, String loginUser);
	
	@Query(value = "select * from T_QAMS_ANSWER_ISSUE where issue_id = ?1", nativeQuery = true)
	QamsAnswerIssue getByIssueId(String issueId);

	@Query(value = "select * from T_QAMS_ANSWER_ISSUE where que_id = ?1 and author_id = ?2", nativeQuery = true)
	List<QamsAnswerIssue> getAnswerIssueByQid(String sid,String loginUser);
}
