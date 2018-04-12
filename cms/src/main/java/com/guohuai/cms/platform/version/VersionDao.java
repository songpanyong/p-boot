package com.guohuai.cms.platform.version;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface VersionDao extends JpaRepository<VersionEntity, String>, JpaSpecificationExecutor<VersionEntity>{
	@Query("select versionNo from VersionEntity  where upgradeType='increment'  and status='on' and publishTime=(select max(publishTime) from VersionEntity where upgradeType='increment'  and status='on')")
	public String getVersionNoByIncrement();
	
	@Query("select versionNo from VersionEntity  where upgradeType='version'  and status='on' and system=?1 and publishTime=(select max(publishTime) from VersionEntity where upgradeType='version' and system=?1 and status='on')")
	public String getVersionNoByVersion(String system);
	/**
	 * 获取最近的增量版本信息
	 * @return
	 */
	@Query("from VersionEntity  where upgradeType='increment'  and status='on' and publishTime=(select max(publishTime) from VersionEntity where upgradeType='increment'  and status='on')")
	public VersionEntity getIncremmentLast();
	/**
	 * 获取最近的升级版本的版本信息
	 * @return
	 */
	@Query("from VersionEntity  where upgradeType='version'  and status='on' and system=?1 and publishTime=(select max(publishTime) from VersionEntity where upgradeType='version' and system=?1 and status='on')")
	public VersionEntity getVersionLast(String system);
	/**
	 * 是否有重复版本号
	 * @param system
	 * @return
	 */
	@Query("select count(*) from VersionEntity  where  system=?1 and versionNo=?2 and oid!=?3")
	public int isHasSameVersion(String system,String versionNo,String oid);
	
	@Query(value = "SELECT t2.* FROM T_PLATFORM_VERSION t2 , "
			+ " (SELECT t1.oid ooid,MAX(t1.versionNo) oversionNo "
			+ " FROM T_PLATFORM_VERSION t1 WHERE t1.system= ?1 AND t1.versionNo > ?2 AND t1.status='on' LIMIT 1) t3 "
			+ " WHERE t3.ooid=t2.oid", nativeQuery = true)
	public VersionEntity getMaxVersion(String system, String version);
}
