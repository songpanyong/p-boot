package com.guohuai.tulip.platform.signin;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SignInDao extends JpaRepository<SignInEntity, String>, JpaSpecificationExecutor<SignInEntity>{

	List<SignInEntity> findByUserId(String userId);

	List<SignInEntity> findByUserIdAndSignInTimeBetween(String userId, Timestamp startTime, Timestamp endTime);
	
	@Query(value = "SELECT * FROM T_TULIP_SIGN_IN "
			+ "WHERE userId = ?1 AND signDate=?2 ", nativeQuery = true)
	SignInEntity findByUserIdAndSignDate(String userId, Date signDate);

}
