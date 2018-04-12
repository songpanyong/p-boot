package com.guohuai.cms.platform.notice;

import java.sql.Date;
import java.sql.Timestamp;
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
import com.guohuai.cms.platform.channel.ChannelEntity;
import com.guohuai.cms.platform.channel.ChannelService;
import com.guohuai.cms.platform.notice.NoticeAPPQueryRep.NoticeAPPQueryRepBuilder;
import com.guohuai.cms.platform.notice.NoticeH5QueryRep.NoticeH5QueryRepBuilder;
import com.guohuai.cms.platform.notice.NoticeQueryRep.NoticeQueryRepBuilder;

@Service
@Transactional
public class NoticeService {

	@Autowired
	private NoticeDao noticeDao;
	@Autowired
	private ChannelService channelService;
	@Autowired
	private AdminUtil adminUtil;
	
	@Transactional
	public PagesRep<NoticeQueryRep> noticeQuery(Specification<NoticeEntity> spec, Pageable pageable) {		
		Page<NoticeEntity> notices = this.noticeDao.findAll(spec, pageable);
		PagesRep<NoticeQueryRep> pagesRep = new PagesRep<NoticeQueryRep>();

		for (NoticeEntity en : notices) {
			NoticeQueryRep rep = new NoticeQueryRepBuilder()
					.oid(en.getOid())
					.channelOid(en.getChannel().getOid())
					.title(en.getTitle())
					.sourceFrom(en.getSourceFrom())
					.subscript(en.getSubscript())
					.top(en.getTop())
					.page(en.getPage())
					.linkHtml(en.getLinkHtml())
					.linkUrl(en.getLinkUrl())
					.approveStatus(en.getApproveStatus())
					.remark(en.getRemark())
					.onShelfTime(en.getOnShelfTime())
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
		pagesRep.setTotal(notices.getTotalElements());	
		return pagesRep;
	}
	
	/**
	 * 新增/修改公告
	 * @param req
	 * @param operator
	 * @return
	 */
	public BaseRep addNotice(NoticeAddReq req, String operator){
		BaseRep rep = new BaseRep();
		ChannelEntity channel = this.channelService.findByOid(req.getChannelOid());
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		NoticeEntity notice = null;
		if(req.getOid() != null && !"".equals(req.getOid())){
			notice = this.getNoticeByOid(req.getOid());
		}else{
			notice = new NoticeEntity();
			notice.setCreateTime(now);
		}		
		notice.setChannel(channel);
		notice.setTitle(req.getTitle());
		notice.setLinkUrl(req.getLinkUrl());
		notice.setLinkHtml(req.getLinkHtml());
		notice.setSubscript(req.getSubscript());
		notice.setSourceFrom(req.getSourceFrom());
		notice.setPage(NoticeEntity.NOTICE_page_no);
		notice.setTop(NoticeEntity.NOTICE_top_2);
		notice.setReleaseStatus(NoticeEntity.NOTICE_releaseStatus_wait);
		notice.setApproveStatus(NoticeEntity.NOTICE_approveStatus_toApprove);
		notice.setOperator(operator);
		notice.setUpdateTime(now);
		this.noticeDao.save(notice);
		return rep;
	}
	
	/**
	 * 删除公告
	 * @param oid
	 * @return
	 */
	public BaseRep delNotice(String oid){
		BaseRep rep = new BaseRep();
		NoticeEntity notice = this.getNoticeByOid(oid);
		if(NoticeEntity.NOTICE_releaseStatus_ok.equals(notice.getReleaseStatus())){
			//error.define[30001]=此公告处于上架状态，无法删除(CODE:30001)
			throw MoneyException.getException(30001);
		}
		this.noticeDao.delete(notice);
		return rep;
	}
	
	/**
	 * 审批处理	
	 * @param req
	 * @param operaotr
	 * @return
	 */
	public BaseRep dealApprove(NoticeApproveReq req, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		NoticeEntity notice = this.getNoticeByOid(req.getOid());
		notice.setApproveStatus(req.getApproveStatus());
		notice.setRemark(req.getRemark());
		notice.setApproveTime(now);
		notice.setApproveOpe(operator);
		this.noticeDao.save(notice);
		return rep;
	}
	
	/**
	 * 首页推荐
	 * @param oid
	 * @param page
	 * @param operator
	 * @return
	 */
	public BaseRep setPage(String oid, String page, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		NoticeEntity notice = this.getNoticeByOid(oid);
		
		// 如果是首页推荐，判断是否有首页推荐记录
		if(NoticeEntity.NOTICE_page_is.equals(page)){
			if(this.noticeDao.getCountOfPageNotice(notice.getChannel()) > 0){
				// error.define[30002]=已有一条首页推荐，无法再添加(CODE:30002)
				throw MoneyException.getException(30002);
			}
		}
		
		notice.setPage(page);
		notice.setOperator(operator);
		notice.setUpdateTime(now);
		this.noticeDao.save(notice);
		return rep;
	}
	
	/**
	 * 是否置顶
	 * @param oid
	 * @param top
	 * @param operator
	 * @return
	 */
	public BaseRep setTop(String oid, String top, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		NoticeEntity notice = this.getNoticeByOid(oid);
		
		// 如果是置顶，判断是否有置顶记录
		if(NoticeEntity.NOTICE_top_1.equals(top)){
			if(this.noticeDao.getCountOfTopNotice(notice.getChannel()) > 0){
				// error.define[30003]=已有一条置顶公告，无法再添加(CODE:30003)
				throw MoneyException.getException(30003);
			}
		}
		
		notice.setTop(top);
		notice.setOperator(operator);
		notice.setUpdateTime(now);
		this.noticeDao.save(notice);
		return rep;
	}
	
	/**
	 * 上下架处理
	 * @param oid
	 * @param releaseStatus
	 * @param onShelfTime 上架时间
	 * @param operator
	 * @return
	 */
	public BaseRep onShelf(String oid, String releaseStatus, Date onShelfTime, String operator){
		Timestamp now = new Timestamp(Clock.DEFAULT.getCurrentTimeInMillis());
		BaseRep rep = new BaseRep();
		NoticeEntity notice = this.getNoticeByOid(oid);
		notice.setReleaseStatus(releaseStatus);
		if(NoticeEntity.NOTICE_releaseStatus_ok.equals(releaseStatus)){
			notice.setOnShelfTime(onShelfTime);
		}else{
			notice.setOnShelfTime(null);
		}
		notice.setReleaseTime(now);
		notice.setReleaseOpe(operator);
		this.noticeDao.save(notice);
		return rep;
	}
	
	/**
	 * 根据OID获取公告
	 * @param oid
	 * @return
	 */
	public NoticeEntity getNoticeByOid(String oid){
		NoticeEntity notice = this.noticeDao.findOne(oid);
		if(notice==null){
			//error.define[30000]=此公告不存在(CODE:30000)
			throw MoneyException.getException(30000);
		}
		return notice;
	}
	
	/**
	 * 获取公告信息
	 * @param oid
	 * @return
	 */
	@Transactional
	public NoticeH5InfoRep getNoticeInof(String oid) {		
		NoticeH5InfoRep rep = new NoticeH5InfoRep();
		NoticeEntity notice = this.getNoticeByOid(oid);
        rep.setOid(notice.getOid());
		rep.setLinkUrl(notice.getLinkUrl());
		rep.setLinkHtml(notice.getLinkHtml());
		rep.setTitle(notice.getTitle());
		return rep;
	}
	
	/**
	 * 根据渠道获取发布的首页公告
	 * @param channelOid
	 * @return
	 */
	@Transactional
	public PagesRep<NoticeAPPQueryRep> getPageNotice(String channelOid) {		
		List<NoticeEntity> notices = this.noticeDao.getPageNotice(channelOid);
		
		PagesRep<NoticeAPPQueryRep> pagesRep = new PagesRep<NoticeAPPQueryRep>();
        if(notices!=null&&notices.size()>0){
        	for (NoticeEntity en : notices) {
    			NoticeAPPQueryRep rep = new NoticeAPPQueryRepBuilder()
    					.oid(en.getOid())
    					.title(en.getTitle())
    					.subscript(en.getSubscript())
    					.linkUrl(en.getLinkUrl())
    					.top(en.getTop())
    					.page(en.getPage())
    					.releaseTime(DateUtil.format(en.getReleaseTime(), DateUtil.defaultDatePattern))
    					.releaseTimeFull(DateUtil.format(en.getReleaseTime(), DateUtil.fullDatePattern))
    					.onShelfTime(DateUtil.format(en.getOnShelfTime(), DateUtil.fullDatePattern))
    					.build();
    			pagesRep.add(rep);
    		}
    		pagesRep.setTotal(notices.size());
        }
			
		return pagesRep;
	}
	
	/**
	 * 获取发布状态为发布的公告，并按‘置顶’排序
	 * @return
	 */
	@Transactional
	public PagesRep<NoticeAPPQueryRep> getNoticesOrderByTop(Specification<NoticeEntity> spec, Pageable pageable) {		
		
		Page<NoticeEntity> notices = this.noticeDao.findAll(spec, pageable);
		
		PagesRep<NoticeAPPQueryRep> pagesRep = new PagesRep<NoticeAPPQueryRep>();
		
		if(notices != null && notices.getSize() > 0){
			for (NoticeEntity en : notices) {
				NoticeAPPQueryRep rep = new NoticeAPPQueryRepBuilder()
						.oid(en.getOid())
						.title(en.getTitle())
						.linkUrl(en.getLinkUrl())
						.linkHtml(en.getLinkHtml())
						.subscript(en.getSubscript())
						.sourceFrom(en.getSourceFrom())
						.page(en.getPage())
						.top(en.getTop())
						.build();
				rep.setReleaseTime(DateUtil.formatSqlDate(en.getOnShelfTime(), DateUtil.defaultDatePattern));
				pagesRep.add(rep);
			}
			pagesRep.setTotal(notices.getTotalElements());	
			pagesRep.setPages(notices.getTotalPages());
		}
		return pagesRep;
	}
	
	/**
	 * 获取发布状态为发布的公告，并按‘置顶’排序(微信端H5使用)
	 * @return
	 */
	@Transactional
	public PagesRep<NoticeH5QueryRep> getNoticesOrderByTopH5(Specification<NoticeEntity> spec, Pageable pageable) {		
		Page<NoticeEntity> notices = this.noticeDao.findAll(spec, pageable);
		
		PagesRep<NoticeH5QueryRep> pagesRep = new PagesRep<NoticeH5QueryRep>();
		
		if(notices != null && notices.getSize() > 0){
			for (NoticeEntity en : notices) {
				NoticeH5QueryRep rep = new NoticeH5QueryRepBuilder()
						.oid(en.getOid())
						.title(en.getTitle())
						.subscript(en.getSubscript())
						.linkUrl(en.getLinkUrl())
						.build();
				rep.setReleaseTime(DateUtil.formatSqlDate(en.getOnShelfTime(), DateUtil.defaultDatePattern));
				pagesRep.add(rep);
			}
			pagesRep.setTotal(notices.getTotalElements());	
			pagesRep.setPages(notices.getTotalPages());
		}
		return pagesRep;
	}
}
