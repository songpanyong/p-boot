package com.guohuai.cms.platform.notice;

import java.sql.Date;

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
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/notice", produces = "application/json")
public class NoticeController extends BaseController{
	
	@Autowired
	private NoticeService noticeService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<NoticeQueryRep>> query(HttpServletRequest request,
		@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
				@Spec(params = "title", path = "title", spec = Like.class),
			    @Spec(params = "subscript", path = "subscript", spec = In.class),
			    @Spec(params = "approveStatus", path = "approveStatus", spec = In.class),
				@Spec(params = "releaseStatus", path = "releaseStatus", spec = In.class),
				@Spec(params = "reqTimeBegin", path = "onShelfTime", spec = DateAfterInclusive.class),
				@Spec(params = "reqTimeEnd", path = "onShelfTime", spec = DateBeforeInclusive.class)}) 
         		Specification<NoticeEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<NoticeQueryRep> rep = this.noticeService.noticeQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<NoticeQueryRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid NoticeAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.addNotice(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> update(@Valid NoticeAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.addNotice(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep = new BaseRep();
		this.noticeService.delNotice(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 审核公告 通过/驳回
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dealapprove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> dealApprove(@Valid NoticeApproveReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.dealApprove(req, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 首页推荐
	 * @param oid
	 * @param top
	 * @return
	 */
	@RequestMapping(value = "/setPage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> setPage(@RequestParam String oid,
			@RequestParam String page) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.setPage(oid, page, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}	
	
	/**
	 * 是否置顶
	 * @param oid
	 * @param top
	 * @return
	 */
	@RequestMapping(value = "/setTop", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> setTop(@RequestParam String oid,
			@RequestParam String top) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.setTop(oid, top, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 上下架处理
	 * @return
	 */
	@RequestMapping(value = "/onshelf", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> dealOnShelf(@RequestParam String oid,
			@RequestParam String releaseStatus, @RequestParam(required=false) Date onShelfTime) {
		String operator = super.getLoginUser();
		BaseRep rep = this.noticeService.onShelf(oid, releaseStatus, onShelfTime, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
