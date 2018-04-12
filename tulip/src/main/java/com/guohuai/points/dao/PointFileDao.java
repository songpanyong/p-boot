package com.guohuai.points.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.points.entity.PointFileEntity;


public interface PointFileDao extends JpaRepository<PointFileEntity, String>, JpaSpecificationExecutor<PointFileEntity> {
	
	public List<PointFileEntity> findByGoodsOid(String goodsOid);
}
