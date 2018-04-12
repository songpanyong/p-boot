package com.guohuai.cms.platform.images;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImagesDao extends JpaRepository<ImagesEntity, String>, JpaSpecificationExecutor<ImagesEntity>{

}
