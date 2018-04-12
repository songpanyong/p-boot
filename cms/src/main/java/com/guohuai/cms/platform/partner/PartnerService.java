package com.guohuai.cms.platform.partner;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.channel.ChannelEntity;
import com.guohuai.cms.platform.channel.ChannelService;
import com.guohuai.cms.platform.partner.PartnerAPPQueryRep.PartnerAPPQueryRepBuilder;
import com.guohuai.cms.platform.partner.PartnerQueryRep.PartnerQueryRepBuilder;

@Service
@Transactional
public class PartnerService {

	@Autowired
	private PartnerDao partnerDao;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private AdminUtil adminUtil;
	
	/**
	 * 列表查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	@Transactional
	public PagesRep<PartnerQueryRep> query(Specification<PartnerEntity> spec, Pageable pageable) {		
		Page<PartnerEntity> ens = this.partnerDao.findAll(spec, pageable);

		PagesRep<PartnerQueryRep> pagesRep = new PagesRep<PartnerQueryRep>();

		for (PartnerEntity en : ens) {
			PartnerQueryRep rep = new PartnerQueryRepBuilder()
					.oid(en.getOid())
					.channelOid(en.getChannel().getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.isLink(en.getIsLink())
					.isNofollow(en.getIsNofollow())
					.linkUrl(en.getLinkUrl())
					.approveStatus(en.getApproveStatus())
					.remark(en.getRemark())
					.approveOpe(this.adminUtil.getAdminName(en.getApproveOpe()))
					.approveTime(en.getApproveTime())
					.releaseStatus(en.getReleaseStatus())
					.releaseOpe(this.adminUtil.getAdminName(en.getReleaseOpe()))
					.releaseTime(en.getReleaseTime())
					.operator(this.adminUtil.getAdminName(en.getOperator()))
					.updateTime(en.getUpdateTime())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(ens.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 批量上下架查询
	 * @param spec
	 * @return
	 */
	@Transactional
	public PagesRep<PartnerQueryRep> sortQuery(Specification<PartnerEntity> spec) {		
		
		List<PartnerEntity> ens = this.partnerDao.findAll(spec, new Sort(Direction.ASC, "sorting"));
		PagesRep<PartnerQueryRep> pagesRep = new PagesRep<PartnerQueryRep>();

		for (PartnerEntity en : ens) {
			PartnerQueryRep rep = new PartnerQueryRepBuilder()
					.oid(en.getOid())
					.channelOid(en.getChannel().getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.releaseStatus(en.getReleaseStatus())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(ens.size());	
		return pagesRep;
	}
	
	
	/**
	 * 根据渠道获取上架的
	 * @param channelOid
	 * @return
	 */
	@Transactional
	public PagesRep<PartnerAPPQueryRep> findPartner(String channelOid) {		
		final ChannelEntity channel = this.channelService.findByOid(channelOid);
		
		Specification<PartnerEntity> sa = new Specification<PartnerEntity>() {
			@Override
			public Predicate toPredicate(Root<PartnerEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate b = cb.equal(root.get("channel").get("oid").as(String.class), channel.getOid());
				Predicate c = cb.equal(root.get("releaseStatus").as(String.class), PartnerEntity.PARTNER_releaseStatus_ok);
				
				query.where(cb.and(b, c));
				query.orderBy(cb.asc(root.get("sorting")));
				
				return query.getRestriction();
			}
		};
		
		List<PartnerEntity> ens = this.partnerDao.findAll(sa);
		PagesRep<PartnerAPPQueryRep> pagesRep = new PagesRep<PartnerAPPQueryRep>();
		
		for (PartnerEntity en : ens) {
			PartnerAPPQueryRep rep = new PartnerAPPQueryRepBuilder()
					.oid(en.getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.linkUrl(en.getLinkUrl())
					.isLink(en.getIsLink())
					.isNofollow(en.getIsNofollow())
					.sorting(en.getSorting())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(ens.size());	
		return pagesRep;
	}
	
	/**
	 * 新增/修改
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep add(PartnerAddReq req, String operator){
		BaseRep rep = new BaseRep();
		ChannelEntity channel = this.channelService.findByOid(req.getChannelOid());
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		PartnerEntity en;
		if(null != req.getOid() && !"".equals(req.getOid())){
			en = this.getByOid(req.getOid());
		}else{
			en = new PartnerEntity();
			en.setCreateTime(now);
		}			
		en.setChannel(channel);
		en.setTitle(req.getTitle());
		en.setLinkUrl(req.getLinkUrl());
		en.setImageUrl(req.getImageUrl());
		en.setApproveStatus(PartnerEntity.PARTNER_approveStatus_toApprove);
		//新增/修改的时候默认待发布
		en.setReleaseStatus(PartnerEntity.PARTNER_releaseStatus_wait);
		en.setOperator(operator);
		en.setUpdateTime(now);
		en.setIsLink(req.getIsLink());
		en.setIsNofollow(req.getIsNofollow());
		this.partnerDao.save(en);
		return rep;
	}
	
	/**
	 * 删除
	 * @param oid
	 * @return
	 */
	public BaseRep del(String oid){
		BaseRep rep = new BaseRep();
		PartnerEntity en = this.getByOid(oid);
		if(PartnerEntity.PARTNER_releaseStatus_ok.equals(en.getReleaseStatus())){
			//error.define[15003]=此合作伙伴处于上架状态，无法删除(CODE:15003)
			throw MoneyException.getException(15003);
		}
		this.partnerDao.delete(en);
		return rep;
	}
	
	/**
	 * 设置上下架
	 * @param oids
	 * @param channelOid
	 * @param releaseOpe
	 * @return
	 */
	public BaseRep setActive(String[] oids, String channelOid, String releaseOpe){
		BaseRep rep = new BaseRep();
		if (null == oids || oids.length == 0) {
			this.partnerDao.delactiviting(channelOid, releaseOpe);
		} else {
			this.partnerDao.delactiviting(oids, channelOid, releaseOpe);					
			for (int i = 0; i < oids.length; i++) {
				this.partnerDao.activiting(oids[i], i+1, channelOid, releaseOpe);
			}
		}
		return rep;
	}
	
	/**
	 * 审批处理	
	 * @param req
	 * @param operaotr
	 * @return
	 */
	public BaseRep dealApprove(PartnerApproveReq req, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		PartnerEntity en = this.getByOid(req.getOid());
		en.setApproveStatus(req.getApproveStatus());
		en.setRemark(req.getRemark());
		en.setApproveTime(now);
		en.setApproveOpe(operator);
		this.partnerDao.save(en);
		return rep;
	}
	
	/**
	 * 根据OID获取
	 * @param oid
	 * @return
	 */
	public PartnerEntity getByOid(String oid){
		PartnerEntity en = this.partnerDao.findOne(oid);
		if(en==null){
			//error.define[15000]=合作伙伴不存在！(CODE:15000)
			throw MoneyException.getException(15000);
		}
		return en;
	}
	
	
}
