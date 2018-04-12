package com.guohuai.tulip.platform.banner;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.util.Clock;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;


@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerDao bannerDao;

	/**
	 * 列表查询
	 */
	@Transactional
	public PageResp<BannerQueryRep> bannerQuery(Specification<BannerEntity> spec, Pageable pageable) {
		Page<BannerEntity> banners = this.bannerDao.findAll(spec, pageable);

		PageResp<BannerQueryRep> pagesRep = new PageResp<BannerQueryRep>();

		for (BannerEntity en : banners) {
			BannerQueryRep rep = new BannerQueryRep();
			rep.setOid(en.getOid());
			rep.setChannelOid(en.getChannelOid());
			rep.setTitle(en.getTitle());
			rep.setImageUrl(en.getImageUrl());
			rep.setIsLink(en.getIsLink());
			rep.setToPage(en.getToPage());
			rep.setLinkUrl(en.getLinkUrl());
			rep.setApproveStatus(en.getApproveStatus());
			rep.setRemark(en.getRemark());
			rep.setApproveOpe(en.getApproveOpe());
			rep.setApproveTime(en.getApproveTime());
			rep.setReleaseStatus(en.getReleaseStatus());
			rep.setReleaseOpe(en.getReleaseOpe());
			rep.setReleaseTime(en.getReleaseTime());
			rep.setOperator(en.getOperator());
			rep.setUpdateTime(en.getUpdateTime());
			pagesRep.getRows().add(rep);
		}
		pagesRep.setTotal(banners.getTotalElements());
		return pagesRep;
	}

	/**
	 * 批量上下架查询
	 */
	@Transactional
	public PageResp<BannerQueryRep> sortQuery(Specification<BannerEntity> spec) {

		List<BannerEntity> banners = this.bannerDao.findAll(spec, new Sort(Direction.ASC, "sorting"));
		PageResp<BannerQueryRep> pagesRep = new PageResp<BannerQueryRep>();

		for (BannerEntity en : banners) {
			BannerQueryRep rep = new BannerQueryRep();
			rep.setOid(en.getOid());
			rep.setChannelOid(en.getChannelOid());
			rep.setTitle(en.getTitle());
			rep.setImageUrl(en.getImageUrl());
			rep.setReleaseStatus(en.getReleaseStatus());
			pagesRep.getRows().add(rep);
		}
		pagesRep.setTotal(banners.size());
		return pagesRep;
	}

	/**
	 * 新增/修改Banner
	 */
	public BaseResp addBanner(BannerAddReq req, String operator) {
		BaseResp rep = new BaseResp();

		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BannerEntity banner;
		if (null != req.getOid() && !"".equals(req.getOid())) {
			banner = this.getBannerByOid(req.getOid());
			if (banner == null) {
				return new BaseResp(-1, "无效的banner id");
			}
		} else {
			banner = new BannerEntity();
			banner.setCreateTime(now);
		}
//		banner.setChannelOid(channelOid);
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
	 */
	public BaseResp delBanner(String oid) {
		BaseResp rep = new BaseResp();
		BannerEntity banner = this.getBannerByOid(oid);
		if (BannerEntity.BANNER_releaseStatus_ok.equals(banner.getReleaseStatus())) {
			//error.define[20001]=此Banner处于上架状态，无法删除(CODE:20001)
			return new BaseResp(20001, "此Banner处于上架状态，无法删除");
		}
		this.bannerDao.delete(banner);
		return rep;
	}

	/**
	 * 设置上下架
	 */
	public BaseResp setActive(String[] oids, String channelOid, String releaseOpe) {
		BaseResp rep = new BaseResp();
		if (ArrayUtils.isEmpty(oids)) {
			this.bannerDao.delactiviting(releaseOpe);
		} else {
			this.bannerDao.delactiviting(oids, releaseOpe);
			for (int i = 0; i < oids.length; i++) {
				this.bannerDao.activiting(oids[i], i + 1, releaseOpe);
			}
		}
		return rep;
	}

	/**
	 * 审批处理
	 */
	public BaseResp dealApprove(BannerApproveReq req, String operator) {
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseResp rep = new BaseResp();
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
	 */
	private BannerEntity getBannerByOid(String oid) {
		return this.bannerDao.findOne(oid);
	}

	/**
	 * 根据渠道获取上架的Banner
	 *
	 * @param channelOid
	 * @return
	 */
	@Transactional
	public PageResp<BannerAPPQueryRep> getOnShelfBanners(String channelOid) {

		List<BannerEntity> bannerList = this.bannerDao.findByReleaseStatusOrderBySortingAsc(BannerEntity.BANNER_releaseStatus_ok);
		PageResp<BannerAPPQueryRep> pagesRep = new PageResp<BannerAPPQueryRep>();

		for (BannerEntity en : bannerList) {
			BannerAPPQueryRep rep = new BannerAPPQueryRep();
			rep.setOid(en.getOid());
			rep.setTitle(en.getTitle());
			rep.setImageUrl(en.getImageUrl());
			rep.setLinkUrl(en.getLinkUrl());
			rep.setIsLink(en.getIsLink());
			rep.setToPage(en.getToPage());
			pagesRep.getRows().add(rep);
		}
		pagesRep.setTotal(bannerList.size());
		return pagesRep;
	}
}
