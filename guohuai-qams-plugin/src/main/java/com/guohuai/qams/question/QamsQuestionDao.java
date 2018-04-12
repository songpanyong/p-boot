package com.guohuai.qams.question;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QamsQuestionDao extends JpaRepository<QamsQuestion, String>, JpaSpecificationExecutor<QamsQuestion> {

	@Query(value = "select * from T_QAMS_ISSUE where que_SID = ?1 and (`status` <> 'INVALID' or `status` is null) order by que_sort asc", nativeQuery = true)
	List<QamsQuestion> getListByQueId(String queId);


	/**
	 * 重新计算所有问题的个数、最大值、最小值
	 * @return
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	@Query(value = "UPDATE T_QAMS_QUESTIONNAIRE e,( "
			+ "SELECT COUNT(c.que_SID) number,IFNULL(SUM(c.maxNum),'0') maxScore,IFNULL(SUM(c.minNum), '0') minScore,c.que_SID que_SID FROM ( "
			+ "SELECT a.issue_id issue_id,MAX(a.score) maxNum,MIN(a.score) minNum,b.que_SID que_SID FROM T_QAMS_ANSWER a "
			+ "LEFT JOIN T_QAMS_ISSUE b ON a.issue_id=b.sid "
			+ "GROUP BY a.issue_id) c "
			+ "GROUP BY c.que_SID) d "
			+ "SET e.max_score = d.maxScore, e.min_score = d.minScore, e.number=d.number WHERE e.sid = d.que_SID ", nativeQuery = true)
	@Modifying
	public int updateAllQuestion();
}
