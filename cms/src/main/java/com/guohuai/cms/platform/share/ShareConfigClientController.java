package com.guohuai.cms.platform.share;

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
@RequestMapping(value = "/cms/client/shareconfig", produces = "application/json;charset=UTF-8")
public class ShareConfigClientController  extends BaseController {

	@Autowired
	private ShareConfigService shareConfigService;
	
	@RequestMapping(value = "getshareconfig", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<ShareConfigSingleRep> detail(@RequestParam(required = true) String pageCode) {
		ShareConfigSingleRep resp = shareConfigService.getShareConfig(pageCode);
		return new ResponseEntity<ShareConfigSingleRep>(resp, HttpStatus.OK);
	}
}
