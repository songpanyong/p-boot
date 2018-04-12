package com.guohuai.cms.platform.push;

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
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
@RestController
@RequestMapping(value = "/cms/boot/push", produces = "application/json")
public class PushController extends BaseController{
	
	@Autowired
	private PushService pushService;
	
	@RequestMapping(value = "/pushQuery", name="推送分页查询", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<PushQueryRep>> pushQuery(HttpServletRequest request,
			@And({  @Spec(params = "title", path = "title", spec = Like.class),
					@Spec(params = "status", path = "status", spec =Equal.class),
					@Spec(params = "type", path = "type", spec =Equal.class),
			     	@Spec(params = "pushTimeBegin", path = "pushTime", spec = DateAfterInclusive.class),
					@Spec(params = "pushTimeEnd", path = "pushTime", spec = DateBeforeInclusive.class),
				}) 
         		Specification<PushEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));				
		PagesRep<PushQueryRep> rep = this.pushService.pushFindAll(spec, pageable);
		
		return new ResponseEntity<PagesRep<PushQueryRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addPush", name="新增、编辑推送信息", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addPush(@Valid PushAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		rep = this.pushService.addPush(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "getPush", name="获取推送详情", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PushQueryRep> getPush(@RequestParam String oid) {		
		PushQueryRep rep = this.pushService.getPush(oid);
		return new ResponseEntity<PushQueryRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "editPush", name="修改推送", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> editPush(@Valid PushAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		rep = this.pushService.addPush(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	@RequestMapping(value = "pushReview", name="推送审核", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> pushReview(@Valid PushReviewRep req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.pushService.pushReview(req, operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/pushPubilsh", name="推送上/下架", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> pushPubilsh(@RequestParam String oid) {
		String operator = super.getLoginUser();
		BaseRep rep = this.pushService.pushPubilsh(oid, operator);	
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delPush", name="删除推送", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delPush(@RequestParam String oid) {
		BaseRep rep = this.pushService.delPush(oid);	
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/isHasSamePushTitle", name="标题名称重复判断",method = RequestMethod.POST)
	@ResponseBody
	public int isHasSamePushTitle(@RequestParam String title,@RequestParam String oid) {
		return this.pushService.isHasSamePushTitle(title,oid);	
	}

	@RequestMapping(value = "/sendPush", name="后台发送个人推送", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> sendPush(HttpServletRequest request,
			@RequestParam(required = true) String userOid, 
			@RequestParam(required = true) String mesTempCode, 
			@RequestParam(required = false) String mesParam) {
		
		BaseResp resp = this.pushService.sendPush2Person(userOid, mesTempCode, mesParam);	
		
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
}
