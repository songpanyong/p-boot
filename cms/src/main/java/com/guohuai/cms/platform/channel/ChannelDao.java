package com.guohuai.cms.platform.channel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelDao extends JpaRepository<ChannelEntity, String>, JpaSpecificationExecutor<ChannelEntity> {

	public ChannelEntity findByCode(String code);
}
