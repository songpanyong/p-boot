package com.guohuai.cms.platform.bankcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;

@RestController
@RequestMapping(value = "/cms/client/bankCard", produces = "application/json")
public class BankCardClientController extends BaseController{

	@Autowired
	private BankCardService bankCardService;
	
	@RequestMapping(name = "客户端查询银行卡配置信息", value = "find", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BankCardCTRep> find(@RequestParam(required = true) String codes) {
		BankCardCTRep resp = bankCardService.queryByCodes(codes);
		return new ResponseEntity<BankCardCTRep>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "客户端查询所有银行卡配置信息", value = "findall", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BankCardCTRep> findall() {
		BankCardCTRep resp = bankCardService.queryAll();
		return new ResponseEntity<BankCardCTRep>(resp, HttpStatus.OK);
	}
}
