package com.guohuai.points.controller;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.points.entity.AccountTransEntity;
import com.guohuai.points.service.AccountTransService;
import com.guohuai.points.request.UserPointsRequest;
import com.guohuai.points.request.AccountTransRequest;
import com.guohuai.points.response.AccountTransRes;
import com.guohuai.points.response.UserPointsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户积分记录
 */
@RestController
@RequestMapping(value = "/points/userPoints/")
@Slf4j
public class UserPointsController {

	@Autowired
	private AccountTransService accountTransService;

	@RequestMapping(value = "page")
	@ResponseBody
	public ResponseEntity<PageResp<UserPointsResponse>> page(UserPointsRequest req) {
		log.info("用户积分记录：{}", JSONObject.toJSON(req));
		AccountTransRequest accountTransRequest = new AccountTransRequest();
		BeanUtils.copyProperties(req, accountTransRequest);

		// 兼容前端只传yyyy-mm-dd而service需要yyyy-MM-dd hh:mm:ss
		if (!StringUtil.isEmpty(req.getBeginTime())) {
			accountTransRequest.setBeginTime(req.getBeginTime() + " 00:00:00");
		}
		if (!StringUtil.isEmpty(req.getEndTime())) {
			accountTransRequest.setEndTime(req.getEndTime() + " 00:00:00");
		}

		AccountTransRes userAccountTrans = accountTransService.getUserAccountTrans(accountTransRequest);
		PageResp<UserPointsResponse> pageResp = new PageResp<>();

		for (AccountTransEntity accountTransEntity : userAccountTrans.getRows()) {
			UserPointsResponse userPointsRes = new UserPointsResponse();
			BeanUtils.copyProperties(accountTransEntity, userPointsRes);
			pageResp.getRows().add(userPointsRes);
		}
		pageResp.setTotal(userAccountTrans.getTotal());
		log.info("用户积分记录查询：返回数据条数：{} ,数据总条数：{}", pageResp.getRows().size(), pageResp.getTotal());

		return new ResponseEntity<PageResp<UserPointsResponse>>(pageResp, HttpStatus.OK);
	}

}

