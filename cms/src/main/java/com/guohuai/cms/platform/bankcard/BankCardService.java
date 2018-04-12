package com.guohuai.cms.platform.bankcard;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.bankcard.BankCardQueryRep.BankCardQueryRepBuilder;

@Service
@Transactional
public class BankCardService {

	@Autowired
	private BankCardDao bankCardDao;
	@Autowired
	private AdminUtil adminUtil;
	
	/**
	 * 根据OID获取
	 * @param oid
	 * @return
	 */
	public BankCardEntity getByOid(String oid){
		BankCardEntity en = this.bankCardDao.findOne(oid);
		if(en==null){
			//银行卡不存在！(CODE:14000)
			throw MoneyException.getException(14000);
		}
		
		return en;
	}
	
	/**
	 * 新增
	 */
	public BankCardEntity saveEntity(BankCardEntity en){
		en.setCreateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.updateEntity(en);
	}
	
	/**
	 * 修改
	 */
	public BankCardEntity updateEntity(BankCardEntity en){
		en.setUpdateTime(new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis()));
		return this.bankCardDao.save(en);
	}
	
	/**
	 * 列表查询
	 */
	@Transactional
	public PagesRep<BankCardQueryRep> query(Specification<BankCardEntity> spec, Pageable pageable) {		
		Page<BankCardEntity> banners = this.bankCardDao.findAll(spec, pageable);

		PagesRep<BankCardQueryRep> pagesRep = new PagesRep<BankCardQueryRep>();

		for (BankCardEntity en : banners) {
			BankCardQueryRep rep = new BankCardQueryRepBuilder()
					.oid(en.getOid())
					.bankCode(en.getBankCode())
					.bankName(en.getBankName())
					.peopleBankCode(en.getPeopleBankCode())
					.bankLogo(en.getBankLogo())
					.bankBigLogo(en.getBankBigLogo())
					.bgColor(en.getBgColor())
					.withdrawDayLimit(en.getWithdrawDayLimit())
					.payDayLimit(en.getPayDayLimit())
					.withdrawOneLimit(en.getWithdrawOneLimit())
					.payOneLimit(en.getPayOneLimit())
					.withdrawMoonLimit(en.getWithdrawMoonLimit())
					.payMoonLimit(en.getPayMoonLimit())
					.status(en.getStatus())
					.creator(this.adminUtil.getAdminName(en.getCreator()))
					.approver(this.adminUtil.getAdminName(en.getApprover()))
					.approveRemark(en.getApproveRemark())
					.approveTime(en.getApproveTime())
					.updateTime(en.getUpdateTime())
					.createTime(en.getCreateTime())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(banners.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/修改
	 * @return
	 */
	public BaseRep add(BankCardAddReq req, String operator){
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		BankCardEntity en;
		if(null != req.getOid() && !"".equals(req.getOid())){
			en = this.getByOid(req.getOid());
		}else{
			if (checkCodeExt(req.getBankCode())){
				//银行卡Code已存在！(CODE:14001)
				throw MoneyException.getException(14001);
			}
			en = new BankCardEntity();
			
			en.setCreateTime(now);
		}			
		en.setBankCode(req.getBankCode());
		en.setBankName(req.getBankName());
		en.setPeopleBankCode(req.getPeopleBankCode());
		en.setBankLogo(req.getBankLogo());
		en.setBankBigLogo(req.getBankBigLogo());
		en.setBgColor(req.getBgColor());
		en.setWithdrawDayLimit(req.getWithdrawDayLimit());
		en.setPayDayLimit(req.getPayDayLimit());
		en.setWithdrawOneLimit(req.getWithdrawOneLimit());
		en.setPayOneLimit(req.getPayOneLimit());
		en.setWithdrawMoonLimit(req.getWithdrawMoonLimit());
		en.setPayMoonLimit(req.getPayMoonLimit());
		en.setStatus(BankCardEntity.BANKCARD_status_toApprove);
		en.setCreator(operator);
		en.setUpdateTime(now);
		this.bankCardDao.save(en);
		return rep;
	}
	
	// 检测银行code是否已存在
	private boolean checkCodeExt(final String bankCode) {
		Specification<BankCardEntity> sa = new Specification<BankCardEntity>() {
			@Override
			public Predicate toPredicate(Root<BankCardEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate a = cb.equal(root.get("bankCode").as(String.class), bankCode);
				return cb.and(a);
			}
		};
		
		List<BankCardEntity> list = this.bankCardDao.findAll(sa);
		
		if (list != null && !list.isEmpty()){
			return true;
		}
		return false;
	}

	/**
	 * 删除Banner
	 * @param oid
	 * @return
	 */
	public BaseRep del(String oid){
		BaseRep rep = new BaseRep();
		BankCardEntity en = this.getByOid(oid);
		this.bankCardDao.delete(en);
		return rep;
	}
	
	/**
	 * 审批处理	
	 * @param req
	 * @param operaotr
	 * @return
	 */
	public BaseRep deal(BankCardApproveReq req, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		BankCardEntity en = this.getByOid(req.getOid());
		en.setStatus(req.getApproveStatus());
		en.setApproveRemark(req.getRemark());
		en.setApproveTime(now);
		en.setApprover(operator);
		this.bankCardDao.save(en);
		return rep;
	}

	// 客户端获取银行卡配置信息
	public BankCardCTRep queryByCodes(String codes) {
		BankCardCTRep resp = new BankCardCTRep();
		List<BankCardCTBaseResp> list = new ArrayList<>();
		if (codes != null && !codes.isEmpty()){
			List<String> codeList = JSON.parseArray(codes, String.class);
			
			if (codeList != null && codeList.size()>0){
				for (String code: codeList){
					BankCardEntity en = this.bankCardDao.findByBankCode(code);
					BankCardCTBaseResp item = new BankCardCTBaseResp();
					if (en != null && en.getStatus().equals(BankCardEntity.BANKCARD_status_pass)){
						item.setBankCode(en.getBankCode());
						item.setPeopleBankCode(en.getPeopleBankCode());
						item.setBankLogo(en.getBankLogo());
						item.setBankBigLogo(en.getBankBigLogo());
						item.setBankName(en.getBankName());
						item.setBgColor(en.getBgColor());
						item.setPayDayLimit(en.getPayDayLimit());
						item.setPayOneLimit(en.getPayOneLimit());
						item.setWithdrawDayLimit(en.getWithdrawDayLimit());
						item.setWithdrawOneLimit(en.getWithdrawOneLimit());
						item.setWithdrawMoonLimit(en.getWithdrawMoonLimit());
						item.setPayMoonLimit(en.getPayMoonLimit());
					}
					
					list.add(item);
				}
			}
		}
		resp.setDatas(list);
		return resp;
	}

	/**
	 * 客户端获取所有银行信息
	 * @return
	 */
	public BankCardCTRep queryAll() {
		Specification<BankCardEntity> sa = new Specification<BankCardEntity>() {
			@Override
			public Predicate toPredicate(Root<BankCardEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate a = cb.equal(root.get("status").as(String.class), BankCardEntity.BANKCARD_status_pass);
				
				query.where(a);
				query.orderBy(cb.asc(root.get("createTime")));
				
				return query.getRestriction();
			}
		};
		
		List<BankCardEntity> ens = this.bankCardDao.findAll(sa);
		
		BankCardCTRep resp = new BankCardCTRep();
		List<BankCardCTBaseResp> list = new ArrayList<>();
		
		for(BankCardEntity en : ens){
			BankCardCTBaseResp item = new BankCardCTBaseResp();
			item.setBankCode(en.getBankCode());
			item.setPeopleBankCode(en.getPeopleBankCode());
			item.setBankLogo(en.getBankLogo());
			item.setBankBigLogo(en.getBankBigLogo());
			item.setBankName(en.getBankName());
			item.setBgColor(en.getBgColor());
			item.setPayDayLimit(en.getPayDayLimit());
			item.setPayOneLimit(en.getPayOneLimit());
			item.setWithdrawDayLimit(en.getWithdrawDayLimit());
			item.setWithdrawOneLimit(en.getWithdrawOneLimit());
			item.setWithdrawMoonLimit(en.getWithdrawMoonLimit());
			item.setPayMoonLimit(en.getPayMoonLimit());
			
			list.add(item);
		}
		
		resp.setDatas(list);
		return resp;
	}
}
