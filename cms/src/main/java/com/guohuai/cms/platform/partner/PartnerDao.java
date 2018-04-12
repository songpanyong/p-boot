package com.guohuai.cms.platform.partner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.guohuai.cms.platform.channel.ChannelEntity;

public interface PartnerDao extends JpaRepository<PartnerEntity, String>, JpaSpecificationExecutor<PartnerEntity>{

	public List<PartnerEntity> findByChannelAndReleaseStatus(ChannelEntity channel, String releaseStatus);
	
	/**
	 * 上架当前渠道下的banner
	 * @param oid
	 * @param sorting
	 * @param channelOid
	 * @param releaseOpe
	 * @return
	 */
	@Query("UPDATE PartnerEntity SET releaseStatus = 'ok', sorting = ?2, releaseTime = SYSDATE(), releaseOpe = ?4 WHERE oid = ?1 AND channel.oid = ?3")
	@Modifying
	public int activiting(String oid, int sorting, String channelOid, String releaseOpe);

	/**
	 * 下架当前渠道下的bnnner
	 * @param oids
	 * @param channelOid
	 * @param releaseOpe
	 * @return
	 */
	@Query("UPDATE PartnerEntity SET releaseStatus = 'no', releaseTime = SYSDATE(), releaseOpe = ?3 WHERE oid not in ?1 AND approveStatus in ('pass') AND channel.oid = ?2")
	@Modifying
	public int delactiviting(String[] oids, String channelOid, String releaseOpe);

	/**
	 * 下架当前渠道下全部banner
	 * @param releaseOpe
	 * @param channelOid
	 * @return
	 */
	@Query("UPDATE PartnerEntity SET releaseStatus = 'no', releaseTime = SYSDATE(), releaseOpe = ?2 WHERE approveStatus in ('pass') AND channel.oid = ?1")
	@Modifying
	public int delactiviting(String channelOid, String releaseOpe);
	
	/**
	 * 获取渠道下banner条数
	 * @param channelOid
	 * @return
	 */
	@Query("SELECT count(*) FROM PartnerEntity WHERE channel.oid = ?1")
	public int getCountInChannel(String channelOid);
	
}
