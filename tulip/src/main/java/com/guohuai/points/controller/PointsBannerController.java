package com.guohuai.points.controller;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.banner.BannerAPPQueryRep;
import com.guohuai.tulip.platform.banner.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/points/banner", produces = "application/json")
public class PointsBannerController extends BaseController {

	@Autowired
	private BannerService bannerService;
	
	/**
	 * 根据渠道获取上架的Banner
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<BannerAPPQueryRep>> getBanner(String channelOid) {
		
		PageResp<BannerAPPQueryRep> rep = this.bannerService.getOnShelfBanners(channelOid);
		
		return new ResponseEntity<PageResp<BannerAPPQueryRep>>(rep, HttpStatus.OK);		
	}
	
}
