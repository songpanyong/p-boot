package com.guohuai.cms.platform.information;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


public interface InformationDao extends JpaRepository<InformationEntity, String>, JpaSpecificationExecutor<InformationEntity>{

	/**
	 * 根据渠道获取推荐的资讯信息
	 * @return
	 */
	@Query("FROM InformationEntity WHERE STATUS = 'published' AND isHome = 1 AND channel.oid = ?1 order by publishTime desc ")
	public List<InformationEntity> getHomeInformation(String channelOid);
	/**
	 * 获得发布的相应的资讯列表
	 * @param typeName
	 * @return
	 */
	@Query("FROM InformationEntity WHERE STATUS='published' AND type=?1  ")
	public List<InformationEntity> getInformationPublished(String type);
	/**
	 * 资讯类型下是否包含有资讯信息
	 * @param name
	 * @return
	 */
	@Query("select count(*) FROM InformationEntity WHERE  type=?1  ")
	public int isHasInfo(String type);
	
	@Query("SELECT count(*) FROM InformationEntity WHERE channel.oid = ?1")
	public int getCountInChannel(String channelOid);
}
