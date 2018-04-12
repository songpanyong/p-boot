package com.guohuai.payadapter.redeem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CallBackDaoLog extends JpaRepository<CallBackLog, String>, JpaSpecificationExecutor<CallBackLog> {
}
