package com.guohuai.cms.platform.channel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.component.util.Clock;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.activity.ActivityDao;
import com.guohuai.cms.platform.banner.BannerDao;
import com.guohuai.cms.platform.channel.ChannelSelectRep.ChannelSelectRepBuilder;
import com.guohuai.cms.platform.information.InformationDao;
import com.guohuai.cms.platform.notice.NoticeDao;

@Service
@Transactional
public class ChannelService {

	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private BannerDao bannerDao;
	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private InformationDao informationDao;
	
	/**
	 * 获取渠道下拉列表
	 * @return
	 */
	public Map<String, List<ChannelSelectRep>> allSelectChannels() {
		Map<String, List<ChannelSelectRep>> map = new HashMap<String, List<ChannelSelectRep>>();
		List<ChannelSelectRep> selects = new ArrayList<ChannelSelectRep>();
		
		List<ChannelEntity> channels = this.channelDao.findAll();
		
		for (ChannelEntity en : channels) {
			ChannelSelectRep rep = new ChannelSelectRepBuilder().id(en.getOid())
					.text(en.getName())
					.build();
			selects.add(rep);
		}
		map.put("channelTypes", selects);
		return map;
	}
	
	public ChannelEntity findByOid(String channelOid) {
		ChannelEntity channel = this.channelDao.findOne(channelOid);
		if (null == channel) {
			// 渠道不存在(CODE:10000)
			throw MoneyException.getException(10000);
		}
		return channel;
	}
	
	public boolean findByCode(String code) {
		ChannelEntity channel = this.channelDao.findByCode(code);
		if (null != channel) {
			// 渠道编号已经存在！(CODE:10001)
			throw MoneyException.getException(10001);
		}
		return false;
	}
	
	/**
	 * 渠道列表查询
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public PagesRep<ChannelEntity> channelQuery(Specification<ChannelEntity> spec, Pageable pageable) {		
		
		Page<ChannelEntity> channels = this.channelDao.findAll(spec, pageable);
		PagesRep<ChannelEntity> pagesRep = new PagesRep<ChannelEntity>();

		for (ChannelEntity en : channels) {
			pagesRep.add(en);
		}
		pagesRep.setTotal(channels.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/编辑渠道
	 * @param req
	 */
	public BaseRep addChannel(ChannelAddReq req) {
		BaseRep rep = new BaseRep();
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		
		ChannelEntity channel;
		if (null != req && !"".equals(req.getOid())) {
			channel = this.findByOid(req.getOid());
		} else {
			channel = new ChannelEntity();
			channel.setCreateTime(now);
			if (!this.findByCode(req.getCode())) {
				channel.setCode(req.getCode());
			}
		}
		channel.setName(req.getName());
		channel.setUpdateTime(now);
		this.channelDao.save(channel);
		
		return rep;
	}
	
	/**
	 * 删除渠道
	 * @param channelOid
	 * @return
	 */
	public BaseRep delChannel(String channelOid) {
		BaseRep rep = new BaseRep();
		
		int numBanner = this.bannerDao.getCountInChannel(channelOid);
		int numNotice = this.noticeDao.getCountInChannel(channelOid);
		int numActivity = this.activityDao.getCountInChannel(channelOid);
		int numInfo = this.informationDao.getCountInChannel(channelOid);
		
		if (numBanner > 0) {
			// 此渠道下还有Banner在使用，无法删除！(CODE:10006)
			throw MoneyException.getException(10006);
		}
		
		if (numNotice > 0) {
			// 此渠道下还有公告在使用，无法删除！(CODE:10003)
			throw MoneyException.getException(10003);
		}
		
		if (numActivity > 0) {
			// 此渠道下还有活动在使用，无法删除！(CODE:10004)
			throw MoneyException.getException(10004);
		}
		
		if (numInfo > 0) {
			// 此渠道下还有资讯在使用，无法删除！(CODE:10005)
			throw MoneyException.getException(10005);
		}
		ChannelEntity channel = this.findByOid(channelOid);

		this.channelDao.delete(channel);
		return rep;
	}
}
