package com.guohuai.points.controller;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.request.DeliveryRequest;
import com.guohuai.points.response.DeliveryResponse;
import com.guohuai.points.service.DeliveryManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/points/myOrder/")
@Slf4j
public class MyOrderController {

	@Autowired
	private DeliveryManageService deliveryManageService;

	@RequestMapping(value = "query")
	@ResponseBody
	public ResponseEntity<PageResp<DeliveryResponse>> query(DeliveryRequest req) {
		log.info("我的订单查询：{}", JSONObject.toJSON(req));
		if (StringUtil.isEmpty(req.getUserOid())) {
			log.info("我的订单查询：userOid为空！");
			return null;
		}
		PageResp<DeliveryResponse> pageResp = deliveryManageService.query(req);
		return new ResponseEntity<PageResp<DeliveryResponse>>(pageResp, HttpStatus.OK);
	}

/*
	@RequestMapping(value = "findById")
	@ResponseBody
	public ResponseEntity<BaseResp> findById(String oid) {

		if (StringUtil.isEmpty(oid)) {
			BaseResp baseResp = new BaseResp(-1, "id为空！");
			return new ResponseEntity<BaseResp>(baseResp, HttpStatus.OK);
		}
		log.info("查询单条记录ID：{}", oid);
		DeliveryResponse billRes = deliveryManageService.findById(oid);

		return new ResponseEntity<BaseResp>(billRes, HttpStatus.OK);
	}
*/


	@RequestMapping(value = "confirmReceipt")
	@ResponseBody
	public ResponseEntity<BaseResp> confirmReceipt(DeliveryRequest req) {
		log.info("确认收货：{}", req);
		BaseResp res = checkConfirmReceipt(req);
		log.info("确认收货校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		try {
			res = deliveryManageService.confirmReceipt(req);
		} catch (Exception e) {
			log.info("确认收货失败：", e.getMessage());
			res.setErrorCode(-1);
			res.setErrorMessage("系统繁忙，请稍后重试！");
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "deleteOrder")
	@ResponseBody
	public ResponseEntity<BaseResp> deleteOrder(DeliveryRequest req) {
		log.info("删除订单：{}", req);
		BaseResp res = checkDeleteOrder(req);
		log.info("删除订单校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		try {
			res = deliveryManageService.deleteOrder(req);
		} catch (Exception e) {
			log.info("删除订单失败：", e.getMessage());
			res.setErrorCode(-1);
			res.setErrorMessage("系统繁忙，请稍后重试！");
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	/**
	 * 检查确认收货的请求参数
	 */
	private BaseResp checkConfirmReceipt(DeliveryRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		if (null == req.getState() || 3 != req.getState()) {
			baseResp.setErrorMessage("错误的状态！");
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}

	/**
	 * 检查删除订单的请求参数
	 */
	private BaseResp checkDeleteOrder(DeliveryRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}
}

