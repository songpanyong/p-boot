package com.guohuai.tulip.platform.sceneprop;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ScenePropDao extends JpaRepository<ScenePropEntity, String>, JpaSpecificationExecutor<ScenePropEntity> {
	
	@Query(value = "SELECT * FROM T_TULIP_SCENE_PROP WHERE isdel = 'no'",nativeQuery=true)
	public List<ScenePropEntity> findAllSceneProp();

	@Query(value="UPDATE T_TULIP_SCENE_PROP "
			+ "SET isdel = ?3 "
			+ "WHERE oid = ?1 AND isdel=?2",nativeQuery=true)
	@Modifying
	public int activeRuleprop(String oid,String fromStatus,String toStatus);
}
