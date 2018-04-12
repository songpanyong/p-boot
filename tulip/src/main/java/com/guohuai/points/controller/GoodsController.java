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
import com.guohuai.points.entity.PointGoodsEntity;
import com.guohuai.points.request.GoodsRequest;
import com.guohuai.points.service.GoodsService;
/**
 * @author mr_gu
 */

@RestController
@RequestMapping(value = "/points/goods")
public class GoodsController {

	@Autowired
	private GoodsService goodsService;

	@RequestMapping(value = "/save", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> save(@Valid GoodsRequest req) {
		BaseResp response = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getName())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品名不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getType())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品类型不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getNeedPoints()) {
			response.setErrorCode(-1);
			response.setErrorMessage("所需积分不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getTotalCount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品数量不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getRemark())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品介绍不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		
		if(PointGoodsEntity.GOODSTYPE_VIRTUAL.equals(req.getType())){
			//虚拟商品，则虚拟类型、虚拟卡券id不能为空
			if(StringUtil.isEmpty(req.getVirtualCouponType()) || StringUtil.isEmpty(req.getIssueVirtualCouponId())) {
				response.setErrorCode(-1);
				response.setErrorMessage("虚拟卡券类型、虚拟卡券id不能为空");
				return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
			}
		}
		
//		if(StringUtil.isEmpty(req.getFileOid())) {
//			r.with(Constant.RESULT, "商品图片不能为空");
//			return new ResponseEntity<Response>(r, HttpStatus.OK);
//		}
		
		response = goodsService.saveGoods(req);
		return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> update(@Valid GoodsRequest req) {
		BaseResp response = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getOid())){
			response.setErrorCode(-1);
			response.setErrorMessage("无需要修改的商品");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getName())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品名不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getType())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品类型不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getNeedPoints()) {
			response.setErrorCode(-1);
			response.setErrorMessage("所需积分不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(null == req.getTotalCount()) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品数量不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(req.getRemark())) {
			response.setErrorCode(-1);
			response.setErrorMessage("商品介绍不能为空");
			return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
		}
		
		if(PointGoodsEntity.GOODSTYPE_VIRTUAL.equals(req.getType())){
			//虚拟商品，则虚拟类型、虚拟卡券id不能为空
			if(StringUtil.isEmpty(req.getVirtualCouponType()) || StringUtil.isEmpty(req.getIssueVirtualCouponId())) {
				response.setErrorCode(-1);
				response.setErrorMessage("虚拟卡券类型、虚拟卡券id不能为空");
				return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
			}
		}

		response = goodsService.updateGoods(req);
		return new ResponseEntity<BaseResp>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/edit", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> edit(@Valid GoodsRequest req) {
		BaseResp repponse = new BaseResp();
		//验证参数
		if(StringUtil.isEmpty(req.getOid())){
			repponse.setErrorCode(-1);
			repponse.setErrorMessage("无需要修改的商品");
			return new ResponseEntity<BaseResp>(repponse, HttpStatus.OK);
		}
		
		repponse = goodsService.editGoods(req);
    	return new ResponseEntity<BaseResp>(repponse, HttpStatus.OK);
	}

	/**
	 * 获取积分商品列表
	 *
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/page", method = {RequestMethod.POST, RequestMethod.GET})
	public
	@ResponseBody
	ResponseEntity<PageResp<PointGoodsEntity>> page(GoodsRequest form) {
		PageResp<PointGoodsEntity> rows = goodsService.page(form);
		return new ResponseEntity<PageResp<PointGoodsEntity>>(rows, HttpStatus.OK);
	}

}
