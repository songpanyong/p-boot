package com.guohuai.points.controller;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.request.AddressRequest;
import com.guohuai.points.response.AddressResponse;
import com.guohuai.points.service.AddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/points/address/")
@Slf4j
public class AddressController {

	@Autowired
	private AddressService addressService;

	@RequestMapping(value = "page")
	@ResponseBody
	public ResponseEntity<PageResp<AddressResponse>> page(AddressRequest req) {
		log.info("收货地址查询：{}", JSONObject.toJSON(req));
		if (StringUtil.isEmpty(req.getUserOid())) {
		log.info("收货地址查询：userOid为空！");
			PageResp<AddressResponse> resp = new PageResp<>();
			resp.setErrorCode(-1);
			resp.setErrorMessage("userOid为空！");
			return new ResponseEntity<PageResp<AddressResponse>>(resp, HttpStatus.OK);
		}
		PageResp<AddressResponse> pageResp = addressService.page(req);
		return new ResponseEntity<PageResp<AddressResponse>>(pageResp, HttpStatus.OK);
	}

	@RequestMapping(value = "findById")
	@ResponseBody
	public ResponseEntity<BaseResp> findById(String oid) {

		if (StringUtil.isEmpty(oid)) {
			BaseResp baseResp = new BaseResp(-1, "id为空！");
			return new ResponseEntity<BaseResp>(baseResp, HttpStatus.OK);
		}
		log.info("收货地址查询单条记录ID：{}", oid);
		AddressResponse addressResponse = addressService.findById(oid);

		return new ResponseEntity<BaseResp>(addressResponse, HttpStatus.OK);
	}

	@RequestMapping(value = "save")
	@ResponseBody
	public ResponseEntity<BaseResp> save(AddressRequest req) {
		log.info("收货地址save：{}", req);
		BaseResp res = checkSaveRequest(req);
		log.info("收货地址save校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		res = addressService.save(req);
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "delete")
	@ResponseBody
	public ResponseEntity<BaseResp> delete(AddressRequest req) {
		log.info("收货地址删除：{}", req);
		BaseResp res = checkDeleteRequest(req);
		log.info("收货地址删除校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		res = addressService.delete(req);
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	@RequestMapping(value = "default")
	@ResponseBody
	public ResponseEntity<BaseResp> setDefault(AddressRequest req) {
		log.info("收货地址设置默认：{}", req);
		BaseResp res = checkSetDefaultRequest(req);
		log.info("收货地址设置默认校验结果：{}", res);
		if (res.getErrorCode() == -1) {
			return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
		}
		try {
			res = addressService.setDefault(req);
		} catch (Exception e) {
			log.info("收货地址设置默认失败：{}", e.getMessage());
			res.setErrorCode(-1);
			res.setErrorMessage("未知错误，请联系客服！");
		}
		return new ResponseEntity<BaseResp>(res, HttpStatus.OK);
	}

	/**
	 * 检查设置默认地址请求参数
	 */
	private BaseResp checkSetDefaultRequest(AddressRequest req) {
		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getUserOid())) {
			baseResp.setErrorMessage("UserId为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}


	/**
	 * 检查新增的请求参数
	 */
	private BaseResp checkSaveRequest(AddressRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");

		if (StringUtil.isEmpty(req.getUserOid())) {
			baseResp.setErrorMessage("UserId为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getName())) {
			baseResp.setErrorMessage("收货人为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getTakeAddress())) {
			baseResp.setErrorMessage("收货地址为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getPhone())) {
			baseResp.setErrorMessage("联系电话为空！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}

	/**
	 * 检查删除收货地址的请求参数
	 */
	private BaseResp checkDeleteRequest(AddressRequest req) {

		BaseResp baseResp = new BaseResp(-1, "失败");
		if (StringUtil.isEmpty(req.getOid())) {
			baseResp.setErrorMessage("oid为空！");
			return baseResp;
		}
		if (StringUtil.isEmpty(req.getIsdel())) {
			baseResp.setErrorMessage("删除标识为空！");
			return baseResp;
		}
		if (!"yes".equalsIgnoreCase(req.getIsdel())) {
			baseResp.setErrorMessage("删除标识格式错误！");
			return baseResp;
		}
		baseResp.setErrorMessage("成功");
		baseResp.setErrorCode(0);
		return baseResp;
	}
}

