package com.guohuai.cms.platform.push;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PushDao extends JpaRepository<PushEntity, String>, JpaSpecificationExecutor<PushEntity>{
	
	@Query("select count(*) FROM PushEntity WHERE  title=?1 and oid!=?2 ")
	public int isHasSamePushTitle(String title,String oid);

}
