package com.guohuai.cms.platform.notice;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.cms.platform.channel.ChannelEntity;

public interface NoticeDao extends JpaRepository<NoticeEntity, String>, JpaSpecificationExecutor<NoticeEntity>{

	/**
	 * 根据渠道获取首页上架的公告
	 * @return
	 */
	@Query("FROM NoticeEntity WHERE releaseStatus = 'ok' AND page = 'is' AND channel.oid = ?1")
	public List<NoticeEntity> getPageNotice(String channelOid);
	
	/**
	 * 查询首页推荐的个数
	 * @param channelEntity 
	 * @return
	 */
	@Query("SELECT count(*) FROM NoticeEntity WHERE releaseStatus = 'ok' AND page = 'is' and channel = ?1 ")
	public int getCountOfPageNotice(ChannelEntity channelEntity);
	
	/**
	 * 查询置顶的个数
	 * @return
	 */
	@Query("SELECT count(*) FROM NoticeEntity WHERE releaseStatus = 'ok' AND top = '1'  and channel = ?1 ")
	public int getCountOfTopNotice(ChannelEntity channelEntity);
	
	@Query("SELECT count(*) FROM NoticeEntity WHERE channel.oid = ?1")
	public int getCountInChannel(String channelOid);
}
