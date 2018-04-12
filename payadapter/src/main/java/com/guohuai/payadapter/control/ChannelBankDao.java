package com.guohuai.payadapter.control;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChannelBankDao extends JpaRepository<ChannelBank, String>, JpaSpecificationExecutor<ChannelBank> {
}
