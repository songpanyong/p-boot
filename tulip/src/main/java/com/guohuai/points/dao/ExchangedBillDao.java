package com.guohuai.points.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.points.entity.ExchangedBillEntity;

public interface ExchangedBillDao extends JpaRepository<ExchangedBillEntity, String>, JpaSpecificationExecutor<ExchangedBillEntity> {

	public List<ExchangedBillEntity> findByUserOid(String userOid);

}
