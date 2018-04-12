package com.guohuai.tulip.platform.userinvest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public interface UserInvestDao
		extends JpaRepository<UserInvestEntity, String>, JpaSpecificationExecutor<UserInvestEntity> {

	@Query(value = "SELECT * FROM T_TULIP_USER_INVEST_LOG WHERE userId=?1", nativeQuery = true)
	public UserInvestEntity findUserInvestByUserId(String userId);

	@Query(value = "SELECT * FROM T_TULIP_USER_INVEST_LOG where phone in (?1)", nativeQuery = true)
	public List<UserInvestEntity> findUserInvestByPhones(List<String> phones);

	public List<UserInvestEntity> findUserInvestByFriendId(String friendId);

	@Query(value = "UPDATE T_TULIP_USER_INVEST_LOG "
			+ "SET investAmount=investAmount+?1,investCount=IFNULL(investCount,0)+1,firstInvestTime=IF(firstInvestTime IS NULL,NOW(),firstInvestTime),firstInvestAmount=IF(firstInvestAmount=0,?1,firstInvestAmount) "
			+ "WHERE userId=?2", nativeQuery = true)
	@Modifying
	public int updateInvestAmount(BigDecimal investAmount, String userId);

	@Query(value = "UPDATE T_TULIP_USER_INVEST_LOG "
			+ "SET friends=IFNULL(friends,0)+1 "
			+ "WHERE userId=?1", nativeQuery = true)
	@Modifying
	public int updateFriends(String userId);

	@Query(value = "UPDATE T_TULIP_USER_INVEST_LOG s "
			+ "SET s.name = ?2,s.birthday = ?3 "
			+ "WHERE s.userId = ?1", nativeQuery = true)
	@Modifying
	public int updateReferee(String userId, String name, Date birthday);

	@Query(value = "SELECT * FROM T_TULIP_USER_INVEST_LOG "
			+ "WHERE DAY(NOW())=DAY(birthday) "
			+ "AND MONTH(NOW())=MONTH(birthday)", nativeQuery = true)
	public List<UserInvestEntity> findUserByBirthDay();
	
	@Query(value = "UPDATE T_TULIP_USER_INVEST_LOG s "
			+ "SET s.phone = ?2 WHERE s.userId = ?1", nativeQuery = true)
	@Modifying
	public int updatePhone(String userId, String phone);

    @Query(value = "SELECT * FROM T_TULIP_USER_INVEST_LOG WHERE oid > ?1 order by oid limit 200", nativeQuery = true)
    public List<UserInvestEntity> batchFindUser(String lastOid);
	
}
