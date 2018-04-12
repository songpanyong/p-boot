package com.guohuai.cms.platform.mail;

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
import com.guohuai.basic.component.ext.web.PageResp;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/mail", produces = "application/json")
public class MailBootController extends BaseController{

	@Autowired
	private MailService mailService;
	
	
//	@RequestMapping(value = "/list", name="后台分页查询站内信",  method = {RequestMethod.POST,RequestMethod.GET})
//	@ResponseBody
//	public ResponseEntity<PageResp<MailBTResp>> mailQuery(HttpServletRequest request, String userOid, 
//				@RequestParam int page, @RequestParam int rows) {
//		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));				
//		PageResp<MailBTResp> rep = this.mailService.queryBTPage4Detail(pageable, userOid);
//		
//		return new ResponseEntity<PageResp<MailBTResp>>(rep, HttpStatus.OK);
//	}
	
	@RequestMapping(value = "/queryPage", name="后台分页查询站内信",  method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PageResp<MailBTResp>> informationQuery(HttpServletRequest request,
         		@And({
         			@Spec(params = "phone", path = "phone", spec = Like.class),
         			@Spec(params = "mailType", path = "mailType", spec = Equal.class),
         			@Spec(params = "status", path = "status", spec = In.class),
         			@Spec(params = "createTimeBegin", path = "createTime", spec = DateAfterInclusive.class, config = "yyyy-MM-dd"),
    				@Spec(params = "createTimeEnd", path = "createTime", spec = DateBeforeInclusive.class, config = "yyyy-MM-dd") 
				}) 
         		Specification<MailEntity> mailspec,
				@RequestParam int page, 
				@RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));	
		PageResp<MailBTResp> rep = this.mailService.queryBTPage4List(mailspec, pageable);
		
		return new ResponseEntity<PageResp<MailBTResp>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/detail", name="后台获取站内信详情",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> login(@RequestParam String mailOid) {
		MailBTResp resp = this.mailService.getBTDetail(mailOid);
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	/**
	 * 审核操作
	 * @param approveResult 申请结果（pass：通过，refused：拒绝）
	 * @param aoid
	 * @return
	 */
	@RequestMapping(value = "/approve", name="后台审核站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> dealApprove(HttpServletRequest request, @RequestParam String approveResult, @RequestParam String aoid,  @RequestParam String approveNote){
		this.mailService.approve(approveResult, aoid, approveNote, this.getLoginUser());
		
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", name="后台删除站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> deleteApprove(HttpServletRequest request, @RequestParam String aoid, @RequestParam String approveNote){
		this.mailService.delete(aoid, this.getLoginUser(), approveNote);
		
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", name="后台添加/修改站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> add(HttpServletRequest request, @Valid MailBTAddReq req){
		
		this.mailService.addBT(req, this.getLoginUser());
		
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sendMail", name="后台发送站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> sendMail(HttpServletRequest request,
			@RequestParam(required = true) String userOid,
			@RequestParam(required = true) String mesTempCode,
			@RequestParam(required = false) String mesParam){
		
		BaseResp resp = this.mailService.sendMail(userOid, mesTempCode, mesParam);
		
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
}
