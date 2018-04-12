package com.guohuai.cms.platform.activity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.AdminUtil;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.activity.ActivityQueryRep.ActivityQueryRepBuilder;
import com.guohuai.cms.platform.channel.ChannelEntity;
import com.guohuai.cms.platform.channel.ChannelService;

@Service
@Transactional
public class ActivityService {
	
	@Autowired
	private ActivityDao activityDao;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private AdminUtil adminUtil;
	
	/**
    * 页面查询信息
    * @param spec
    * @param pageable
    * @return
    */
	public PagesRep<ActivityQueryRep> activityFindAll(Specification<ActivityEntity> spec, Pageable pageable) {
		Page<ActivityEntity> products = this.activityDao.findAll(spec, pageable);
		PagesRep<ActivityQueryRep> pagesRep = new PagesRep<ActivityQueryRep>();
		
		for (ActivityEntity pe : products) {
			ActivityQueryRep rep = new ActivityQueryRepBuilder()
					.oid(pe.getOid())
					.channelOid(pe.getChannel().getOid())
					.title(pe.getTitle())
					.location(pe.getLocation())
					.linkUrl(pe.getLinkUrl())
					.status(pe.getStatus())
					.publishTime(pe.getPublishTime())
					.picUrl(pe.getPicUrl())
					.toPage(pe.getToPage())
					.linkType(pe.getLinkType())
					.beginTime(DateUtil.format(pe.getBeginTime(), DateUtil.fullDatePattern))
					.endTime(DateUtil.format(pe.getEndTime(), DateUtil.fullDatePattern))
					.build();
			pagesRep.add(rep);
		}
		pagesRep.setTotal(products.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增、编辑活动
	 * @param req
	 * @param operator
	 * @return
	 */
    public ActivityEntity addActivity(ActivityAddReq req, String operator) {
    	Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
    	
    	ChannelEntity channel = channelService.findByOid(req.getChannelOid());
    	
    	ActivityEntity activity = null;
    	if (null != req.getOid() && !"".equals(req.getOid())) {
    		activity = this.getOne(req.getOid());
		} else {
			activity = new ActivityEntity();
		}
    	activity.setChannel(channel);
    	activity.setCreateTime(now);
    	activity.setTitle(req.getTitle());
    	activity.setLocation(req.getLocation());
    	activity.setLinkType(req.getLinkType());
    	activity.setLinkUrl(req.getLinkUrl());
    	//跳转的APP页面
    	activity.setToPage(req.getToPage());
    	activity.setStatus(ActivityEntity.ACTIVITY_status_pending);
    	activity.setCreator(operator);	
    	activity.setPicUrl(req.getPicUrl());
    	activity.setBeginTime(req.getBeginTime());
    	activity.setEndTime(req.getEndTime());
    	activity=this.activityDao.save(activity);
    	return activity;
    }
    
    /**
	 * 获取活动实体
	 * @param oid
	 * @return
	 */
	public ActivityEntity getOne(String oid){
		ActivityEntity en = this.activityDao.findOne(oid);
		if(en==null){
			//error.define[60000]=此活动不存在(CODE:60000)
			throw MoneyException.getException(60000);
		}
		return en;
	}
	
	/**
	 * 获取活动信息详情
	 * @param oid
	 * @return
	 */
	public ActivityQueryRep getActivity(String oid) {
		ActivityEntity pe = this.activityDao.getOne(oid);
		ActivityQueryRep rep = new ActivityQueryRepBuilder()
				.oid(pe.getOid())
				.channelOid(pe.getChannel().getOid())
				.title(pe.getTitle())
				.status(pe.getStatus())
				.publishTime(pe.getPublishTime())
				.picUrl(pe.getPicUrl())
				.review(this.adminUtil.getAdminName(pe.getReview()))
				.reviewRemark(pe.getReviewRemark())
				.reviewTime(pe.getReviewTime())
				.publisher(this.adminUtil.getAdminName(pe.getPublisher()))
				.creator(this.adminUtil.getAdminName(pe.getCreator()))
				.createTime(pe.getCreateTime())
				.location(pe.getLocation())
				.linkUrl(pe.getLinkUrl())
				.toPage(pe.getToPage())
				.linkType(pe.getLinkType())
				.beginTime(DateUtil.format(pe.getBeginTime(), DateUtil.fullDatePattern))
				.endTime(DateUtil.format(pe.getEndTime(), DateUtil.fullDatePattern))
				.build();
		return rep;
		
	}
	
	/**
	 * 活动审核
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep activityReview(ActivityReviewRep req, String operator) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		ActivityEntity activity =  this.getOne(req.getOid());
		activity.setReview(operator);
		activity.setReviewTime(now);
		if(req.getApprResult().equals(ActivityEntity.ACTIVITY_reviewStatus_pass)){
			activity.setStatus(ActivityEntity.ACTIVITY_status_reviewed);
		}else if(req.getApprResult().equals(ActivityEntity.ACTIVITY_reviewStatus_refused)){
			activity.setStatus(ActivityEntity.ACTIVITY_status_refused);
		}
		activity.setReviewRemark(req.getRemark());
		this.activityDao.save(activity);
		return rep;
	}
	
	/**
	 * 活动上/下架
	 * @param oid
	 * @return
	 */
	public BaseRep activityPubilsh(String oid,String operator) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		ActivityEntity activity =  this.getOne(oid);
		if(activity.getStatus().equalsIgnoreCase(ActivityEntity.ACTIVITY_status_reviewed)){
			activity.setStatus(ActivityEntity.ACTIVITY_status_on);
			activity.setPublishTime(now);
		}else if(activity.getStatus().equalsIgnoreCase(ActivityEntity.ACTIVITY_status_on)){
			activity.setStatus(ActivityEntity.ACTIVITY_status_off);
			activity.setPublishTime(null);
		}else if(activity.getStatus().equalsIgnoreCase(ActivityEntity.ACTIVITY_status_off)){
			activity.setStatus(ActivityEntity.ACTIVITY_status_on);
			activity.setPublishTime(now);
		}
		activity.setPublisher(operator);
		this.activityDao.save(activity);
		return rep;
	}
	
	/**
	 * 根据渠道获取发布的活动信息--APP
	 * @param channelOid
	 * @param location
	 * @return
	 */
	public List<ActivityAppRep> getActivityPubilshed(String channelOid, String[] location) {
		List<ActivityEntity> activitys = this.activityDao.getActivityPubilshed(channelOid, location);
		
		List<ActivityAppRep> reps = new ArrayList<ActivityAppRep>();
		
		if(activitys != null && activitys.size() > 0){
			for (ActivityEntity activity : activitys) {
				ActivityAppRep rep = new ActivityAppRep();
				rep.setOid(activity.getOid());
				rep.setPicUrl(activity.getPicUrl());
				rep.setTitle(activity.getTitle());
				rep.setLocation(activity.getLocation());
				rep.setLinkUrl(activity.getLinkUrl());
				rep.setToPage(activity.getToPage());
				rep.setBeginTimeFull(DateUtil.format(activity.getBeginTime(), DateUtil.fullDatePattern));
				rep.setEndTimeFull(DateUtil.format(activity.getEndTime(), DateUtil.fullDatePattern));
				rep.setBeginTime(DateUtil.format(activity.getBeginTime(), DateUtil.defaultDatePattern));
				rep.setEndTime(DateUtil.format(activity.getEndTime(), DateUtil.defaultDatePattern));
				reps.add(rep);
			}
		}
		return reps;
	}
	
	/**
	 * 活动删除
	 * @param oid
	 * @return
	 */
	public BaseRep activityDel(String oid) {
		BaseRep rep = new BaseRep();
		ActivityEntity activity = this.getOne(oid);
		this.activityDao.delete(activity);
		return rep;
	}
	
    /**
     * 是否包含有相同位置的活动已经上架
     * @param location
     * @return
     */
	public int isHasPublishedSameLocation(String location) {
		return activityDao.isHasPublishedSameLocation(location);
	}

}
