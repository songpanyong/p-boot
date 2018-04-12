package com.guohuai.points.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.points.entity.PurchaseBillEntity;


public interface PurchaseBillDao extends JpaRepository<PurchaseBillEntity, String>, JpaSpecificationExecutor<PurchaseBillEntity> {
	public List<PurchaseBillEntity> findByUserOid(String userOid);

}
