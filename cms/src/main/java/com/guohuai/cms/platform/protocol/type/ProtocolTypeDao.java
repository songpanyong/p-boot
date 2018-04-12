package com.guohuai.cms.platform.protocol.type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProtocolTypeDao extends JpaRepository<ProtocolTypeEntity, String>, JpaSpecificationExecutor<ProtocolTypeEntity> {

}
