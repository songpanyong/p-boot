package com.guohuai.tulip.platform.banner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BannerDao extends JpaRepository<BannerEntity, String>, JpaSpecificationExecutor<BannerEntity> {
	public List<BannerEntity> findByReleaseStatusOrderBySortingAsc(String releaseStatus);

	/**
	 * 上架当前渠道下的banner
	 */
	@Query(value = "UPDATE BannerEntity SET releaseStatus = 'ok', sorting = ?2, releaseTime = SYSDATE(), releaseOpe = ?3 WHERE oid = ?1 ")
	@Modifying
	public int activiting(String oid, int sorting,String releaseOpe);

	/**
	 * 下架当前渠道下的bnnner
	 */
	@Query(value = "UPDATE BannerEntity SET releaseStatus = 'no', releaseTime = SYSDATE(), releaseOpe = ?2 WHERE oid not in ?1 AND approveStatus in ('pass') ")
	@Modifying
	public int delactiviting(String[] oids,String releaseOpe);

	/**
	 * 下架当前渠道下全部banner
	 */
	@Query(value = "UPDATE BannerEntity SET releaseStatus = 'no', releaseTime = SYSDATE(), releaseOpe = ?1 WHERE approveStatus in ('pass') ")
	@Modifying
	public int delactiviting(String releaseOpe);

/*	*//**
	 * 获取渠道下banner条数
	 *//*
	@Query(value = "SELECT count(*) FROM BannerEntity WHERE channel.oid = ?1")
	public int getCountInChannel(String channelOid);*/

}
