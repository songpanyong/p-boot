package com.guohuai.tulip.platform.coupon.couponOrder;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.PageResp;

@RestController
@RequestMapping(value = "/tulip/boot/couponorder", produces = "application/json")
public class CouponOrderController extends BaseController {

	@Autowired
	private CouponOrderService couponOrderService;

	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<CouponOrderRep>> query(@Valid CouponOrderReq req) {
		PageResp<CouponOrderRep> rep = this.couponOrderService.query(req);
		return new ResponseEntity<PageResp<CouponOrderRep>>(rep, HttpStatus.OK);
	}
}
