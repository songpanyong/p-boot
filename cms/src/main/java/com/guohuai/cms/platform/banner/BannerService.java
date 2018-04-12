package com.guohuai.cms.platform.banner;

import java.sql.Timestamp;
import java.util.List;

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
import com.guohuai.cms.platform.banner.BannerAPPQueryRep.BannerAPPQueryRepBuilder;
import com.guohuai.cms.platform.banner.BannerQueryRep.BannerQueryRepBuilder;
import com.guohuai.cms.platform.channel.ChannelEntity;
import com.guohuai.cms.platform.channel.ChannelService;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerDao bannerDao;
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
	public PagesRep<BannerQueryRep> bannerQuery(Specification<BannerEntity> spec, Pageable pageable) {		
		Page<BannerEntity> banners = this.bannerDao.findAll(spec, pageable);

		PagesRep<BannerQueryRep> pagesRep = new PagesRep<BannerQueryRep>();

		for (BannerEntity en : banners) {
			BannerQueryRep rep = new BannerQueryRepBuilder()
					.oid(en.getOid())
					.channelOid(en.getChannel().getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.isLink(en.getIsLink())
					.toPage(en.getToPage())
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
		pagesRep.setTotal(banners.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 批量上下架查询
	 * @param spec
	 * @return
	 */
	@Transactional
	public PagesRep<BannerQueryRep> sortQuery(Specification<BannerEntity> spec) {		
		
		List<BannerEntity> banners = this.bannerDao.findAll(spec, new Sort(Direction.ASC, "sorting"));
		PagesRep<BannerQueryRep> pagesRep = new PagesRep<BannerQueryRep>();

		for (BannerEntity en : banners) {
			BannerQueryRep rep = new BannerQueryRepBuilder()
					.oid(en.getOid())
					.channelOid(en.getChannel().getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.releaseStatus(en.getReleaseStatus())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(banners.size());	
		return pagesRep;
	}
	
	
	/**
	 * 根据渠道获取上架的Banner
	 * @param channelOid
	 * @return
	 */
	@Transactional
	public PagesRep<BannerAPPQueryRep> getOnShelfBanners(String channelOid) {		
		ChannelEntity channel = this.channelService.findByOid(channelOid);
				
		List<BannerEntity> bannerList = this.bannerDao.findByChannelAndReleaseStatusOrderBySortingAsc(channel, BannerEntity.BANNER_releaseStatus_ok);
		PagesRep<BannerAPPQueryRep> pagesRep = new PagesRep<BannerAPPQueryRep>();
		
		for (BannerEntity en : bannerList) {
			BannerAPPQueryRep rep = new BannerAPPQueryRepBuilder()
					.oid(en.getOid())
					.title(en.getTitle())
					.imageUrl(en.getImageUrl())
					.linkUrl(en.getLinkUrl())
					.isLink(en.getIsLink())
					.toPage(en.getToPage())
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(bannerList.size());	
		return pagesRep;
	}
	
	/**
	 * 新增/修改Banner
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep addBanner(BannerAddReq req, String operator){
		BaseRep rep = new BaseRep();
		ChannelEntity channel = this.channelService.findByOid(req.getChannelOid());
		
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BannerEntity banner;
		if(null != req.getOid() && !"".equals(req.getOid())){
			banner = this.getBannerByOid(req.getOid());
		}else{
			banner = new BannerEntity();
			banner.setCreateTime(now);
		}			
		banner.setChannel(channel);
		banner.setTitle(req.getTitle());
		banner.setLinkUrl(req.getLinkUrl());
		banner.setImageUrl(req.getImageUrl());
		banner.setApproveStatus(BannerEntity.BANNER_approveStatus_toApprove);
		//新增/修改的时候默认待发布
		banner.setReleaseStatus(BannerEntity.BANNER_releaseStatus_wait);
		banner.setOperator(operator);
		banner.setUpdateTime(now);
		banner.setIsLink(req.getIsLink());
		banner.setToPage(req.getToPage());
		this.bannerDao.save(banner);
		return rep;
	}
	
	/**
	 * 删除Banner
	 * @param oid
	 * @return
	 */
	public BaseRep delBanner(String oid){
		BaseRep rep = new BaseRep();
		BannerEntity banner = this.getBannerByOid(oid);
		if(BannerEntity.BANNER_releaseStatus_ok.equals(banner.getReleaseStatus())){
			//error.define[20001]=此Banner处于上架状态，无法删除(CODE:20001)
			throw MoneyException.getException(20001);
		}
		this.bannerDao.delete(banner);
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
			this.bannerDao.delactiviting(channelOid, releaseOpe);
		} else {
			this.bannerDao.delactiviting(oids, channelOid, releaseOpe);					
			for (int i = 0; i < oids.length; i++) {
				this.bannerDao.activiting(oids[i], i+1, channelOid, releaseOpe);
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
	public BaseRep dealApprove(BannerApproveReq req, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		BannerEntity banner = this.getBannerByOid(req.getOid());
		banner.setApproveStatus(req.getApproveStatus());
		banner.setRemark(req.getRemark());
		banner.setApproveTime(now);
		banner.setApproveOpe(operator);
		this.bannerDao.save(banner);
		return rep;
	}
	
	/**
	 * 根据OID获取Banner
	 * @param oid
	 * @return
	 */
	public BannerEntity getBannerByOid(String oid){
		BannerEntity banner = this.bannerDao.findOne(oid);
		if(banner==null){
			//error.define[20000]=此Banner不存在(CODE:20000)
			throw MoneyException.getException(20000);
		}
		return banner;
	}
	
	
}
