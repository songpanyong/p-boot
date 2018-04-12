package com.guohuai.qams.answerQue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InvestorBaseAccountInfoDao extends JpaRepository<InvestorBaseAccountInfoEntity, String>, JpaSpecificationExecutor<InvestorBaseAccountInfoEntity> {

	
}
