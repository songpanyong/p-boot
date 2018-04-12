package com.guohuai.cms.platform.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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

@RestController
@RequestMapping(value = "/cms/client/mail", produces = "application/json")
public class MailClientController extends BaseController{

	@Autowired
	private MailService mailService;
	
	@RequestMapping(value = "/query", name="前台分页查询站内信",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<PageResp<MailCTResp>> query(@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<MailCTResp> resp = this.mailService.queryCTPage(pageable, this.getLoginUser());
		
		return new ResponseEntity<PageResp<MailCTResp>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/queryPage", name="前台分页查询站内信",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<PageResp<MailCTResp>> queryPage(@RequestParam int page, @RequestParam int rows, @RequestParam(required = false) String isRead) {
		PageResp<MailCTResp> resp = this.mailService.queryCTPage(page, rows, isRead, this.getLoginUser());
		
		return new ResponseEntity<PageResp<MailCTResp>>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/detail",	name="前台获取站内信详情",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> detail(@RequestParam String mailOid) {
		MailCTResp resp = this.mailService.getCTDetail(mailOid, this.getLoginUser());
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/noreadnum",	name="前台获取未读站内信数量",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> noreadnum() {
		MailNoNumCTResp resp = this.mailService.getNoReadNum(this.getLoginUser());
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allread",	name="前台站内信全部置为已读",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> allread() {
		this.mailService.allread(this.getLoginUser());
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
}
