package com.guohuai.tulip.platform.commissionorder;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.facade.FacadeNewService;
import com.guohuai.tulip.platform.intervalsetting.IntervalSettingService;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

@Service
@Transactional
public class CommissionOrderService {

	@Autowired
	private CommissionOrderDao commissionOrderDao;
	@Autowired
	UserInvestService userInvestService;
	@Autowired
	IntervalSettingService intervalSettingService;
	@Autowired
	private FacadeNewService facadeNewService;
	
	public List<CommissionOrderEntity> queryByUserId(String userId){
		return commissionOrderDao.queryByUserId(userId);
	}


	public PageResp<CommissionOrderRep> query(Specification<CommissionOrderEntity> spec, Pageable pageable) {

		Page<CommissionOrderEntity> enchs = this.commissionOrderDao.findAll(spec, pageable);
		PageResp<CommissionOrderRep> pageResp = new PageResp<CommissionOrderRep>();
		for (CommissionOrderEntity ench : enchs) {
			CommissionOrderRep rep = new CommissionOrderRep();
			rep.setOid(ench.getOid());
			rep.setFriendInvest(ench.getFriendInvest());
			rep.setFriendPhone(ench.getFriendPhone());
			rep.setOrderAmount(ench.getOrderAmount());
			rep.setOrderCode(ench.getOrderCode());
			rep.setOrderStatus(ench.getOrderStatus());
			rep.setCreateTime(ench.getCreateTime());
			rep.setType(ench.getType());
			rep.setUserId(ench.getUserId());
			rep.setPhone(ench.getPhone());
			rep.setRejectAdvice(ench.getRejectAdvice());
			rep.setAuditor(ench.getAuditor());
			rep.setAuditTime(ench.getAuditTime());
			rep.setFriendInvestTime(ench.getFriendInvestTime());
			pageResp.getRows().add(rep);
		}
		pageResp.setTotal(enchs.getTotalElements());
		return pageResp;
	}


	public PageResp<CommissionOrderRep> commissionListByOrderStatus(String orderStatus) {
		List<CommissionOrderEntity> commissionList = commissionOrderDao.commissionListByOrderStatus(orderStatus);
		PageResp<CommissionOrderRep> PageResp = new PageResp<CommissionOrderRep>();
		CommissionOrderRep rep = null;
		for (CommissionOrderEntity en : commissionList) {
			rep = new CommissionOrderRep();
			rep.setOid(en.getOid());
			rep.setFriendInvest(en.getFriendInvest());
			rep.setFriendPhone(en.getFriendPhone());
			rep.setOrderAmount(en.getOrderAmount());
			rep.setOrderCode(en.getOrderCode());
			rep.setOrderStatus(en.getOrderStatus());
			rep.setCreateTime(en.getCreateTime());
			rep.setType(en.getType());
			rep.setUserId(en.getUserId());
			rep.setPhone(en.getPhone());
			rep.setRejectAdvice(en.getRejectAdvice());
			rep.setAuditor(en.getAuditor());
			rep.setAuditTime(en.getAuditTime());
			rep.setFriendInvestTime(en.getFriendInvestTime());
			PageResp.getRows().add(rep);
		}
		PageResp.setTotal(commissionList.size());
		return PageResp;
	}


	/**
	 * 审批通过，通过之后下发卡券
	 * @param oid
	 * @param operateName
	 */
	@Transactional
	public BaseResp commissionOrderPass(String oid,String operateName) {
		BaseResp rep = new BaseResp();
		int num =this.commissionOrderDao.commissionOrderPass(oid,operateName);
		if(num < 1 ){
			rep.setErrorCode(-1);
			rep.setErrorMessage("审核通过异常!");
			throw new GHException("审核通过异常!");
		}
		facadeNewService.generatePercentCoupon(oid);
		return rep;
	}
	
	/**
	 * 批量审核通过，通过之后下发卡券
	 * @param oids
	 * @param operateName
	 */
	public void commissionOrderBatchPass(String[] oids,String operateName) {
		this.commissionOrderDao.commissionOrderBatchPass(operateName, oids);
		for (String oid : oids) {
			CommissionOrderEntity commissionOrderEntity = this.commissionOrderDao.findOne(oid);
			if(commissionOrderEntity==null){
				throw new GHException("查询的佣金订单不存在!");
			}
			facadeNewService.generatePercentCoupon(oid);
		}
	}


	public void commissionOrderRefused(String oid, String operateName,String rejectAdvice) {
		CommissionOrderEntity commissionOrderEntity = this.commissionOrderDao.findOne(oid);
		if(commissionOrderEntity==null){
			throw new GHException("查询的佣金订单不存在!");
		}
		this.commissionOrderDao.commissionOrderRefused(oid,operateName,rejectAdvice);
	}


	public CommissionOrderRep commissionOrderDetail(String oid) {
		CommissionOrderEntity commissionOrderEntity = this.commissionOrderDao.findOne(oid);
		CommissionOrderRep commissionOrderRep = new CommissionOrderRep();
		BeanUtils.copyProperties(commissionOrderEntity, commissionOrderRep);
		return commissionOrderRep;
	}
	

	public List<CommissionOrderRep> getcommissionDetail(String oid) {
		List<CommissionOrderRep> rpList=new ArrayList<CommissionOrderRep>();
		UserInvestEntity userInvestEntity = this.userInvestService.findUserInvestByOid(oid);
		String userId = userInvestEntity.getUserId();
		List<CommissionOrderEntity> coList = commissionOrderDao.queryByUserId(userId);
		if(coList==null){
			throw new RuntimeException("该用户无佣金或佣金订单待审核");
		}
		for (CommissionOrderEntity co : coList) {
			CommissionOrderRep commissionOrderRep = new CommissionOrderRep();
			commissionOrderRep.setCreateTime(co.getCreateTime());
			commissionOrderRep.setFriendPhone(co.getFriendPhone());
			commissionOrderRep.setCommissionAmount(co.getOrderAmount());
			commissionOrderRep.setFriendInvest(co.getFriendInvest());
			rpList.add(commissionOrderRep);
		}
		return rpList;
	}

	/**
	 * 查询佣金订单
	 * @param commissionOrderOid
	 * @return
	 */
	public CommissionOrderEntity findCommissionOrderByOid(String oid) {
		CommissionOrderEntity entity =this.commissionOrderDao.findOne(oid);
		return entity;
	}

	/**
	 * 保存佣金订单
	 * @param coList
	 */
	public void createCommissionOrder(List<CommissionOrderEntity> coList) {
		this.commissionOrderDao.save(coList);
	}

	/**
	 * 查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public Page<CommissionOrderEntity> findAll(Specification<CommissionOrderEntity> spec, Pageable pageable) {
		return this.commissionOrderDao.findAll(spec,pageable);
	}
	
}
