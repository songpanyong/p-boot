package com.guohuai.points.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.points.entity.DeliveryEntity;


public interface DeliveryManageDao extends JpaRepository<DeliveryEntity, String>, JpaSpecificationExecutor<DeliveryEntity> {
	
	@Query(value = "SELECT *  FROM T_TULIP_delivery WHERE oid=?1  FOR UPDATE", nativeQuery = true)
	DeliveryEntity forUpdateByOid(String oid);

	@Modifying
	@Query(value = "UPDATE T_TULIP_delivery SET isdel=\"yes\"  WHERE oid=?1", nativeQuery = true)
	int deleteOrder(String oid);
}
