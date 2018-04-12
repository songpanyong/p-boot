package com.guohuai.payadapter.control;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface PaymentAmountDao extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment> {
	
	@Query(value="select SUM(amount) from t_bank_payment t where t.commandStatus = '1' and t.type = '01' and t.createTime > curdate() and t.userOid = ?1",nativeQuery = true)
	public BigDecimal findDaliyAmonut(String userOid);
	
	@Query(value="select SUM(amount) from t_bank_payment t where t.commandStatus != '2' and t.type = '01' and t.createTime > curdate() and t.userOid = ?1 and t.cardNo = ?2",nativeQuery = true)
	public BigDecimal findDaliyAmonutByCardNo(String userOid, String cardNo);
}
