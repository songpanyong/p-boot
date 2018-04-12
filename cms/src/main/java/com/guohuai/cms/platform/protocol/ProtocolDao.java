package com.guohuai.cms.platform.protocol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ProtocolDao extends JpaRepository<ProtocolEntity, String>, JpaSpecificationExecutor<ProtocolEntity> {

	@Query(value = "FROM ProtocolEntity WHERE protocolType.id = ?1")
	public ProtocolEntity findByTypeId(String typeId);
}
