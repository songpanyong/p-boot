package com.guohuai.points.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.entity.PointSettingEntity;
import com.guohuai.points.request.SettingRequest;
import com.guohuai.points.service.SettingService;

/**
 * @author mr_gu
 */
@RestController
@RequestMapping(value = "/points/setting")
public class SettingController {

	@Autowired
	private SettingService settingService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> save(@Valid SettingRequest req) {
		BaseResp response = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getName())) {
			response.setErrorCode(-1);
			response.setErrorMessage("积分名不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getPoints()) {
			response.setErrorCode(-1);
			response.setErrorMessage("积分数不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getAmount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("所需金额不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getTotalCount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("总数量不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		
		response = settingService.savePoints(req);
		return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> update(@Valid SettingRequest req) {
		BaseResp response = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getOid())){
			response.setErrorCode(-1);
			response.setErrorMessage("无需要修改的商品");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getName())) {
			response.setErrorCode(-1);
			response.setErrorMessage("积分名不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getPoints()) {
			response.setErrorCode(-1);
			response.setErrorMessage("积分数不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getAmount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("所需金额不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getTotalCount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("总数量不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}

		response = settingService.updatePoints(req);
		return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> edit(@Valid SettingRequest req) {
		BaseResp response = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getOid())){
			response.setErrorCode(-1);
			response.setErrorMessage("无需要修改的商品");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		
		response = settingService.editPoints(req);
    	return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
	}

	/**
	 * 获取积分产品列表
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseEntity<PageResp<PointSettingEntity>> page(SettingRequest form) {
		PageResp<PointSettingEntity> rows = settingService.page(form);
		return new ResponseEntity<PageResp<PointSettingEntity>>(rows, HttpStatus.OK);
	}

}
