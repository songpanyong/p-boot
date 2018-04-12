package com.guohuai.cms.platform.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;
import com.guohuai.cms.platform.activity.ActivityEntity;
import com.guohuai.cms.platform.activity.ActivityService;
import com.guohuai.cms.platform.actrule.ActRuleInfoRep;
import com.guohuai.cms.platform.actrule.ActRuleService;
import com.guohuai.cms.platform.advice.AdviceAddReq;
import com.guohuai.cms.platform.advice.AdviceService;
import com.guohuai.cms.platform.app.AppInfoRep.AppInfoRepBuilder;
import com.guohuai.cms.platform.banner.BannerAPPQueryRep;
import com.guohuai.cms.platform.banner.BannerService;
import com.guohuai.cms.platform.information.InformationAppRep;
import com.guohuai.cms.platform.information.InformationEntity;
import com.guohuai.cms.platform.information.InformationService;
import com.guohuai.cms.platform.information.type.InformationTypeEntity;
import com.guohuai.cms.platform.information.type.InformationTypeService;
import com.guohuai.cms.platform.notice.NoticeAPPQueryRep;
import com.guohuai.cms.platform.notice.NoticeEntity;
import com.guohuai.cms.platform.notice.NoticeH5InfoRep;
import com.guohuai.cms.platform.notice.NoticeH5QueryRep;
import com.guohuai.cms.platform.notice.NoticeService;
import com.guohuai.cms.platform.protocol.ProtocolInfoRep;
import com.guohuai.cms.platform.protocol.ProtocolService;
import com.guohuai.cms.platform.push.PushEntity;
import com.guohuai.cms.platform.push.PushQueryRep;
import com.guohuai.cms.platform.push.PushService;
import com.guohuai.cms.platform.version.VersionResp;
import com.guohuai.cms.platform.version.VersionService;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
@RestController
@RequestMapping(value = "/cms/app", produces = "application/json")
public class AppContorller extends BaseController {

	@Autowired
	private BannerService bannerService;
	@Autowired
	private NoticeService noticeService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private InformationTypeService InformationTypeService;
	@Autowired
	private AdviceService adviceService;
	@Autowired
	private VersionService versionService;
	@Autowired
	private ProtocolService protocolService;
	@Autowired
	private ActRuleService actRuleService;
	@Autowired
	private PushService pushService;
	/**
	 * APP首页获取相关信息
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "home", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AppInfoRep> getHomeInfo(@RequestParam String channelOid) {
		Map<String, Object> homeMap = new HashMap<String, Object>();
		// 获取上架Banner信息
		homeMap.put("banner", this.bannerService.getOnShelfBanners(channelOid));
		// 获取发布的首页公告
		homeMap.put("notice", this.noticeService.getPageNotice(channelOid));
		// 获取发布的活动信息-轮播
		String[] carousel = {ActivityEntity.ACTIVITY_location_carousel};
		homeMap.put("actcarousel", this.activityService.getActivityPubilshed(channelOid, carousel));
		// 获取发布的活动信息-左/右
		String[] acts = {ActivityEntity.ACTIVITY_location_left, ActivityEntity.ACTIVITY_location_right};
		homeMap.put("activity", this.activityService.getActivityPubilshed(channelOid, acts));
		// 获取推荐资讯
		homeMap.put("information", this.informationService.getHomeInformation(channelOid));
		
		AppInfoRep rep = new AppInfoRepBuilder().info(homeMap).build();
		
		return new ResponseEntity<AppInfoRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 我的页面活动列表
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "mypageacts", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<AppInfoRep> getMyPageActs(@RequestParam String channelOid) {
		Map<String, Object> mypageMap = new HashMap<String, Object>();
		
		// 获取发布的活动信息-轮播
		String[] mypage = {ActivityEntity.ACTIVITY_location_mypage};
		mypageMap.put("mypage", this.activityService.getActivityPubilshed(channelOid, mypage));
		
		AppInfoRep rep = new AppInfoRepBuilder().info(mypageMap).build();
		
		return new ResponseEntity<AppInfoRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 根据渠道获取上架的Banner
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "banner", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<BannerAPPQueryRep>> getBanner(@RequestParam String channelOid) {
		
		PagesRep<BannerAPPQueryRep> rep = this.bannerService.getOnShelfBanners(channelOid);
		
		return new ResponseEntity<PagesRep<BannerAPPQueryRep>>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 获取资讯类型
	 * @return
	 */
	@RequestMapping(value = "getInformationType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<InformationTypeEntity>> getInformationType() {
		
		List<InformationTypeEntity> rep = this.InformationTypeService.getInformationType();
		
		return new ResponseEntity<List<InformationTypeEntity>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取发布的公告，并按‘置顶’排序
	 * @param channelOid
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/getNotices", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<NoticeAPPQueryRep>> getNoticesOrderByTop(@RequestParam final String channelOid,		
			@RequestParam int page, 
			@RequestParam int rows) {
		
		Specification<NoticeEntity> spec = new Specification<NoticeEntity>() {
			@Override
			public Predicate toPredicate(Root<NoticeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {				
				Predicate channel = cb.equal(root.get("channel").get("oid").as(String.class), channelOid);

				Predicate release = cb.equal(root.get("releaseStatus").as(String.class), NoticeEntity.NOTICE_releaseStatus_ok);
				
				return cb.and(channel, release);
			}
		};
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.ASC, "top"), new Order(Direction.DESC, "onShelfTime")));
		
		PagesRep<NoticeAPPQueryRep>  rep = this.noticeService.getNoticesOrderByTop(spec, pageable);
		
		return new ResponseEntity<PagesRep<NoticeAPPQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取发布的公告，并按‘置顶’排序(微信端H5使用)
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/getNoticesH5", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<NoticeH5QueryRep>> getNoticesOrderByTopH5(@RequestParam final String channelOid,
			@RequestParam int page, 
			@RequestParam int rows) {
		
		Specification<NoticeEntity> spec = new Specification<NoticeEntity>() {
			@Override
			public Predicate toPredicate(Root<NoticeEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {				
				Predicate channel = cb.equal(root.get("channel").get("oid").as(String.class), channelOid);

				Predicate release = cb.equal(root.get("releaseStatus").as(String.class), NoticeEntity.NOTICE_releaseStatus_ok);
				return cb.and(channel, release);
			}
		};
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.ASC, "top"), new Order(Direction.DESC, "onShelfTime")));
		
		PagesRep<NoticeH5QueryRep>  rep = this.noticeService.getNoticesOrderByTopH5(spec, pageable);
		
		return new ResponseEntity<PagesRep<NoticeH5QueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取公告详情--微信
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/getNoticeInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<NoticeH5InfoRep> getOneNotice(@RequestParam String oid) {
		
		NoticeH5InfoRep rep = this.noticeService.getNoticeInof(oid);
		
		return new ResponseEntity<NoticeH5InfoRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取首页资讯列表--PC
	 * @return
	 */
	@RequestMapping(value = "getHomeInformations", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<InformationAppRep>> getHomeInformations(String channelOid) {
		
		//获取推荐资讯
		List<InformationAppRep> infos = this.informationService.getHomeInformation(channelOid);
		
		return new ResponseEntity<List<InformationAppRep>>(infos, HttpStatus.OK);		
	}
	
	/**
	 * 获取资讯列表--APP
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "getInformations", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<InformationAppRep>> getInformationList(HttpServletRequest request,
			@And({	@Spec(params = "channelOid", path = "channel.oid", spec =Equal.class),
					@Spec(params = "status", path = "status", spec =Equal.class),
		     	    @Spec(params = "type", path = "type", spec = Equal.class),
			}) 
     		Specification<InformationEntity> spec,
	@RequestParam int page, 
	@RequestParam int rows) {
	Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "publishTime")));				

		PagesRep<InformationAppRep> rep = this.informationService.findInformationtoApp(spec, pageable);
		
		return new ResponseEntity<PagesRep<InformationAppRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取全部的资讯信息-APP
	 * @param channelOid
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/getInformationsAlltoApp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<InformationAppRep>> getAllInformations(@RequestParam final String channelOid,
			@RequestParam int page,
			@RequestParam int rows) {
			
		Specification<InformationEntity> spec = new Specification<InformationEntity>() {
			@Override
			public Predicate toPredicate(Root<InformationEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {				
				Predicate channel = cb.equal(root.get("channel").get("oid").as(String.class), channelOid);
				
				Predicate status = cb.equal(root.get("status").as(String.class), InformationEntity.INFORMATION_status_published);
				return cb.and(channel, status);
			}
		};
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "publishTime")));
		
		PagesRep<InformationAppRep>  rep = this.informationService.getInformationsAlltoApp(spec, pageable);
		
		return new ResponseEntity<PagesRep<InformationAppRep>>(rep, HttpStatus.OK);
	}	
		
	/**
	 * 获取资讯详情
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/getOneInformation", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<InformationAppRep> getOneInformation(@RequestParam String oid) {
		
		InformationAppRep rep = this.informationService.getOneInformation(oid);
		
		return new ResponseEntity<InformationAppRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取协议信息
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/getProtocolInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ProtocolInfoRep> getProtocolInfo(@RequestParam String typeId) {
		
		ProtocolInfoRep rep = this.protocolService.getProtocolInfo(typeId);
		
		return new ResponseEntity<ProtocolInfoRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取活动规则信息
	 * @param typeId
	 * @return
	 */
	@RequestMapping(value = "/getActRuleInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ActRuleInfoRep> getActRuleInfo(@RequestParam String typeId) {
		
		ActRuleInfoRep rep = this.actRuleService.getActRuleInfo(typeId);
		
		return new ResponseEntity<ActRuleInfoRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 反馈意见获取
	 * @param content
	 * @param phone
	 * @param os
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/addAdvice", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addAdvice(@RequestParam String content,@RequestParam String phone,@RequestParam String os,@RequestParam String name) {		
		AdviceAddReq req=new AdviceAddReq();
		req.setContent(content);
		req.setUserID(phone);
		req.setPhoneType(os);
		req.setUserName(name);
		return new ResponseEntity<BaseRep>(this.adviceService.addAdvice(req), HttpStatus.OK);
	}
	
	/**
	 * 向外提供最近的版本信息（增量，ios，android版本信息）
	 * @return
	 */
	@RequestMapping(value = "/getVersionUpdateInfo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getVersionUpdateInfo() {
		Map<String,Object> map=this.versionService.getVersionUpdateInfo();
		return map;
	}
	
	/**
	 * 获取最新安卓版本信息的下载地址息 (增量，ios，android版本信息）
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/getVersionByAndroid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getVersionByAndroid() {
		Map<String,Object> map=this.versionService.getVersionByAndroid();
		return map;
	}
	
	
	
	@RequestMapping(value = "/pushQuery", name="推送分页查询", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<PushQueryRep>> pushQuery(HttpServletRequest request,
			@And({ 
					@Spec(params = "status", path = "status", spec =Equal.class),
					@Spec(params = "type", path = "type", spec =Equal.class)
				}) 
         		Specification<PushEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		final String userOid = this.getLoginUser();
		
		Specification<PushEntity> sa = new Specification<PushEntity>() {
			@Override
			public Predicate toPredicate(Root<PushEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate a = cb.equal(root.get("pushUserOid").as(String.class), userOid);
				Predicate b = cb.equal(root.get("pushType").as(String.class), PushEntity.PUSH_pushType_person);
				return cb.and(a,b);
			}
		};
		Specification<PushEntity> sb = new Specification<PushEntity>() {
			@Override
			public Predicate toPredicate(Root<PushEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate a = cb.equal(root.get("pushType").as(String.class), PushEntity.PUSH_pushType_all);
				
				return a;
			}
		};
		Specification<PushEntity> sp1 = Specifications.where(spec).and(sa);
		Specification<PushEntity> sp2 = Specifications.where(spec).and(sb);
		Specification<PushEntity> sp = Specifications.where(sp1).or(sp2);
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));				
		PagesRep<PushQueryRep> rep = this.pushService.pushFindAll(sp, pageable);
		
		return new ResponseEntity<PagesRep<PushQueryRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getLastVersion", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<VersionResp> getLastVersion(@RequestParam String version, @RequestParam String system) {
		VersionResp resp=this.versionService.getLastVersion(version, system);
		return new ResponseEntity<VersionResp>(resp, HttpStatus.OK);
	}
}
