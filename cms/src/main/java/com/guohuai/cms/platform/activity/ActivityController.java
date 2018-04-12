package com.guohuai.cms.platform.activity;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
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

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/activity", produces = "application/json")
public class ActivityController extends BaseController{
	
	@Autowired
	private ActivityService activityService;
	
	/**
	 * 活动页面数据查询
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/activityQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<ActivityQueryRep>> activityQuery(HttpServletRequest request,
			@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
					@Spec(params = "title", path = "title", spec = Like.class),
					@Spec(params = "status", path = "status", spec =Equal.class),
			     	@Spec(params = "publishTimeBegin", path = "publishTime", spec = DateAfterInclusive.class),
					@Spec(params = "publishTimeEnd", path = "publishTime", spec = DateBeforeInclusive.class) }) 
         		Specification<ActivityEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));				
		PagesRep<ActivityQueryRep> rep = this.activityService.activityFindAll(spec, pageable);
		
		return new ResponseEntity<PagesRep<ActivityQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 新增/编辑 活动信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addActivity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addActivity(@Valid ActivityAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		this.activityService.addActivity(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 获取活动详情
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "getActivity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ActivityQueryRep> getActivity(@RequestParam String oid) {		
		
		ActivityQueryRep rep = this.activityService.getActivity(oid);
		
		return new ResponseEntity<ActivityQueryRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 修改活动
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "editActivity", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> editActivity(@Valid ActivityAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		this.activityService.addActivity(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 活动审核
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "activityReview", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> activityReview(@Valid ActivityReviewRep req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.activityService.activityReview(req, operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 活动上/下架
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "activityPubilsh", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> activityPubilsh(@RequestParam String oid) {
		String operator = super.getLoginUser();
		BaseRep rep = this.activityService.activityPubilsh(oid,operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 删除活动
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "activityDel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> activityDel(@RequestParam String oid) {
		BaseRep rep = this.activityService.activityDel(oid);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 是否包含有相同位置的活动已经上架
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "isHasPublishedSameLocation", method = RequestMethod.POST)
	@ResponseBody
	public int isHasPublishedSameLocation(@RequestParam String location) {
		if (ActivityEntity.ACTIVITY_location_carousel.equals(location) ||
				ActivityEntity.ACTIVITY_location_mypage.equals(location)) {
			return 0;
		} else {
			return this.activityService.isHasPublishedSameLocation(location);
		}
	}
	
}
