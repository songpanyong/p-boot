package com.guohuai.cms.platform.bankcard;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BankCardDao extends JpaRepository<BankCardEntity, String>, JpaSpecificationExecutor<BankCardEntity>{

	// 根据code查找
	BankCardEntity findByBankCode(String code);

}
