package com.guohuai.cms.platformext.push;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PushExtDao extends JpaRepository<PushExtEntity, String>, JpaSpecificationExecutor<PushExtEntity>{
	
	@Query("select count(*) FROM PushEntity WHERE  title=?1 and oid!=?2 ")
	public int isHasSamePushTitle(String title,String oid);

}
