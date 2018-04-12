package com.guohuai.cms.platform.partner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.cms.component.web.PagesRep;

@RestController
@RequestMapping(value = "/cms/client/partner", produces = "application/json")
public class PartnerClientController extends BaseController{

	@Autowired
	private PartnerService partnerService;
	
	@RequestMapping(name = "客户端查询合作伙伴信息", value = "find", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<PagesRep<PartnerAPPQueryRep>> find(@RequestParam String channelOid) {
		PagesRep<PartnerAPPQueryRep> resp = partnerService.findPartner(channelOid);
		
		return new ResponseEntity<PagesRep<PartnerAPPQueryRep>>(resp, HttpStatus.OK);
	}
}
