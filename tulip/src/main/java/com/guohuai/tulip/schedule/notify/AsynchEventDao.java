package com.guohuai.tulip.schedule.notify;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AsynchEventDao extends JpaRepository<AsynchEventEntity, String>, JpaSpecificationExecutor<AsynchEventEntity> {

	/**
	 * 查询未执行的事件
	 * 
	 * @param eventType
	 * @return
	 */
	@Query(value = "SELECT * FROM T_TULIP_ASYNCH_EVENT WHERE eventType = ?1 AND eventStatus = 'unsent'  ORDER BY createTime ASC LIMIT 200 ",nativeQuery=true)
	public List<AsynchEventEntity> getUnsentEventList(String eventType);
	
	/**
	 * 异步发送事件后，修改状态为已发送
	 * @param oids
	 * @return
	 */
	@Query(value="UPDATE T_TULIP_ASYNCH_EVENT SET eventStatus = 'beensent' WHERE oid = ?1 AND eventStatus='unsent' ",nativeQuery=true)
	@Modifying
	public int updateEventTosendSuccess(String oids);
	
}
