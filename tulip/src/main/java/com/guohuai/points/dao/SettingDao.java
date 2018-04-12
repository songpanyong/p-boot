package com.guohuai.points.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.guohuai.points.entity.PointSettingEntity;


public interface SettingDao extends JpaRepository<PointSettingEntity, String>, JpaSpecificationExecutor<PointSettingEntity> {
	
}
