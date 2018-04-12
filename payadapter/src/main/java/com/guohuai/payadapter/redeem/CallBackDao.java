package com.guohuai.payadapter.redeem;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CallBackDao extends JpaRepository<CallBackInfo, String>, JpaSpecificationExecutor<CallBackInfo> {

	/**
	 * 查询未处理的定单
	 * @param type
	 * @return
	 */
	@Query(value = "select * from t_bank_callback where status='0' and type =?1  order by createTime asc limit 200", nativeQuery = true)
	public List<CallBackInfo> queryCallBackAll(String type);

	// 通过payNO和银行流水查单条记录
	@Query(value = "select * from t_bank_callback where  payNO=?1 and type =?2 order by createTime asc", nativeQuery = true)
	public CallBackInfo queryCallBackOne(String payNo,String type);
	
	// 通过orderNO和银行流水查单条记录
	@Query(value = "select * from t_bank_callback where  orderNO=?1 and type =?2 order by createTime asc", nativeQuery = true)
	public CallBackInfo queryCallBackOneByOrderNo(String orderNO,String type);

	// 每次查询时更新数据
	@Query(value = "update t_bank_callback set status=?1,returnCode=?2,returnMsg=?3,count=?4,updateTime=SYSDATE() where oid=?5 ", nativeQuery = true)
	@Modifying
	public int updateCallBack(String status, String returnCode, String returnMsg, int count, String oid);
	
	@Query(value = "select * from t_bank_callback where status='0' and type =?1  order by createTime asc limit 200", nativeQuery = true)
	public List<CallBackInfo> querySettlementCallBackAll(String type);
	
	@Query(value = "select * from t_bank_callback where status='0'  and count=20  and type =?1  order by createTime asc", nativeQuery = true)
	public List<CallBackInfo> querySettlementCallBackMin(String type);
	
	@Query(value = "update t_bank_callback set channelNo=?1  where  payNO=?2", nativeQuery = true)
	@Modifying
	@Transactional
	public int updateByPayNo(String channelNo,String payNo);
}
