package com.guohuai.qams.que;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface QamsQueDao extends JpaRepository<QamsQue, String>, JpaSpecificationExecutor<QamsQue> {


	@Query("from QamsQue q where q.status != 'INVALID' order by q.sid asc")
	public List<QamsQue> findAllOrderBySidAsc();

	@Query(value = "SELECT a.sid, a.name FROM T_QAMS_QUESTIONNAIRE a where a.status != 'INVALID'", nativeQuery = true)
	public List<Object> findAllNameList();
	@Transactional
    @Modifying
	@Query(value = "update QamsQue a set a.number=:number,a.minScore=:minScore,a.maxScore=:maxScore where a.sid=:qid")
	public void updateScore(@Param("qid")String qid,@Param("number")int number,@Param("minScore")String minScore,@Param("maxScore")String maxScore);
	@Query(value = "from QamsQue a where a.type=?1 and a.status is not 'INVALID'")
	public List<QamsQue> findByType(String type);
	@Query(value = "SELECT a.sid, a.name FROM T_QAMS_QUESTIONNAIRE a", nativeQuery = true)
	public List<Object> findAllName(); 
}
