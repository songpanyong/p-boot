package com.guohuai.tulip.platform.coupon.couponRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.mimosa.api.obj.ProductLabelRep;
import com.guohuai.mimosa.api.obj.ProductLabelReq;


@RestController
@RequestMapping(value = "/tulip/boot/couponrange", produces = "application/json")
public class CouponRangeController extends BaseController {
	@Autowired
	CouponRangeService couponRangeService;

	@RequestMapping(value = "getProductList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<List<CouponRangeRep>> getProductList(ProductReq productType) {
		List<CouponRangeRep> rep = this.couponRangeService.getProductList(productType);
		return new ResponseEntity<List<CouponRangeRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "getAllProductList", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<List<CouponRangeRep>> getAllProductList(ProductReq req) {
		List<CouponRangeRep> rep = this.couponRangeService.getAllProductList(req);
		return new ResponseEntity<List<CouponRangeRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "getProductLabelNames", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<List<ProductLabelRep>> getProductLabelNames(ProductLabelReq param) {
		List<ProductLabelRep> rep = this.couponRangeService.getProductLabelNames(param);
		return new ResponseEntity<List<ProductLabelRep>>(rep, HttpStatus.OK);
	}
/*	*//**
	 * @param qRep
	 *            请求数据
	 * @return
	 *//*
	@RequestMapping(value = "/save", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseRep> save(@RequestParam String batch,@RequestParam String productCodes) {
		BaseRep rep = new BaseRep();
		if (!StringUtil.isEmpty(productCodes)) {
			rep = this.couponRangeService.save(batch,productCodes.split(","));
		}
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}*/

}
