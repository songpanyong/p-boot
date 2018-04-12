package com.guohuai.cms.platform.activity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ActivityDao extends JpaRepository<ActivityEntity, String>, JpaSpecificationExecutor<ActivityEntity>{
	
	/**
	 * 获得发布的相应的资讯列表
	 * @param typeName
	 * @return
	 */
	@Query("FROM ActivityEntity WHERE STATUS = 'on' AND location in ?2 AND channel.oid = ?1")
	public List<ActivityEntity> getActivityPubilshed(String channelOid, String[] location);
	
	/**
	 * 是否包含有相同位置的活动已经上架
	 * @param location
	 * @return
	 */
	@Query("select count(*) FROM ActivityEntity WHERE STATUS = 'on'  and location = ?1")
	public int isHasPublishedSameLocation(String location);
	
	@Query("SELECT count(*) FROM ActivityEntity WHERE channel.oid = ?1")
	public int getCountInChannel(String channelOid);

}
