package com.guohuai.cms.platform.element;

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
@RequestMapping(value = "/cms/client/element", produces = "application/json;charset=UTF-8")
public class ElementClientController  extends BaseController {

	@Autowired
	private ElementService elementService;
	
	@RequestMapping(name = "元素管理-元素详情", value = "find", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<ElementCTResp> detail(@RequestParam(required = true) String codes) {
		ElementCTResp resp = elementService.queryByCode(codes);
		return new ResponseEntity<ElementCTResp>(resp, HttpStatus.OK);
	}
}
