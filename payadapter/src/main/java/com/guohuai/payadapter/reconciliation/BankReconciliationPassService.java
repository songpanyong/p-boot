package com.guohuai.payadapter.reconciliation;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.Clock;
/**
 * 对账Service
 * @author chendonghui
 * 
 */
@Service
@Transactional
public class BankReconciliationPassService {

	@Autowired
	private BankReconciliationPassDao passDao;

//	/**
//    * 页面查询信息
//    * @param spec
//    * @param pageable
//    * @return
//    */
//	public PagesRep<BankReconciliationPassRep> activityFindAll(Specification<BankReconciliationPassEntity> spec, Pageable pageable) {
//		Page<BankReconciliationPassEntity> products = this.passDao.findAll(spec, pageable);
//		PagesRep<BankReconciliationPassRep> pagesRep = new PagesRep<BankReconciliationPassRep>();
//		
//		for (BankReconciliationPassEntity pe : products) {
//			BankReconciliationPassRep rep = new BankReconciliationPassRepBuilder().oid(pe.getOid()).build();
//			pagesRep.add(rep);
//		}
//		pagesRep.setTotal(products.getTotalElements());	
//		return pagesRep;
//	}
	
	/**
	 * 新增
	 * @param req
	 * @param operator
	 * @return
	 */
    public BankReconciliationPassEntity addReconciliation(BankReconciliationPassReq req) {
    	Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
    	BankReconciliationPassEntity reconciliation = new BankReconciliationPassEntity();
    	
    	reconciliation.setChannelId(req.getChannelId());
    	reconciliation.setProductId( req.getProductId());
    	reconciliation.setOrderId(req.getOrderId()); 
    	reconciliation.setTransactionCurrency(req.getTransactionCurrency()); 
    	reconciliation.setTransactionAmount(req.getTransactionAmount()); 
    	reconciliation.setPaymentBankNo(req.getPaymentBankNo()); 
    	reconciliation.setBeneficiaryBankNo(req.getBeneficiaryBankNo());
    	reconciliation.setTradStatus(req.getTradStatus());
    	reconciliation.setFailDetail(req.getFailDetail()); 
    	reconciliation.setErrorCode(req.getErrorCode()); 
    	reconciliation.setTransactionTime(req.getTransactionTime()); 
    	reconciliation.setAccountDate(req.getAccountDate()); 
    	reconciliation.setCheckMark(req.getCheckMark()); 
    	reconciliation.setCreateTime(now);
    	
    	reconciliation=this.passDao.save(reconciliation);
    	return reconciliation;
    }

}
