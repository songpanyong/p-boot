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

/**
 * 发货管理
 */
@RestController
@RequestMapping(value = "/points/deliveryManage/")
@Slf4j
public class DeliveryManageController {

	@Autowired
	private DeliveryManageService deliveryManageService;

	@RequestMapping(value = "page")
	@ResponseBody
	public ResponseEntity<PageResp<DeliveryResponse>> page(DeliveryRequest req) {
		log.info("发货管理查询：{}", JSONObject.toJSON(req));
		PageResp<DeliveryResponse> pageResp = deliveryManageService.page(req);
		return new ResponseEntity<PageResp<DeliveryResponse>>(pageResp, HttpStatus.OK);
	}

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

	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseEntity<BaseResp> save(DeliveryRequest req) {
		log.info("新增或修改发货记录：{}", req);
		BaseResp res = checkSaveForm(req);
		log.info("新增或修改发货记录校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		res = deliveryManageService.save(req);
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "cancel")
	@ResponseBody
	public ResponseEntity<BaseResp> cancel(DeliveryRequest req) {
		log.info("取消发货记录：{}", req);
		BaseResp res = checkCancelForm(req);
		log.info("取消发货记录校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		try {
			res = deliveryManageService.cancel(req);
		} catch (Exception e) {
			res.setErrorCode(-1);
			res.setErrorMessage(e.getMessage());
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	/**
	 * 检查新增或修改发货记录的请求参数
	 *
	 * @param req request
	 * @return BaseResp
	 */
	private BaseResp checkSaveForm(DeliveryRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getSendOperater())) {
			baseResp.setErrorMessage("操作人为空！会话超时，请重新登录！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getLogisticsCompany())) {
			baseResp.setErrorMessage("物流公司为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getLogisticsNumber())) {
			baseResp.setErrorMessage("物流号为空！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}

	/**
	 * 检查取消发货的请求参数
	 *
	 * @param req request
	 * @return BaseResp
	 */
	private BaseResp checkCancelForm(DeliveryRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getCancelOperater())) {
			baseResp.setErrorMessage("操作人为空！会话超时，请重新登录！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getCancelReason())) {
			baseResp.setErrorMessage("取消原因为空！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}
}

