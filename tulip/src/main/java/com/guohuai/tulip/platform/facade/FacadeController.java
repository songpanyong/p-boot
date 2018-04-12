package com.guohuai.tulip.platform.facade;

import java.sql.Timestamp;
import java.util.List;

import org.drools.core.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.annotation.SystemControllerLog;
import com.guohuai.tulip.component.api.InvestorLabelResp;
import com.guohuai.tulip.platform.eventAnno.AuthenticationEvent;
import com.guohuai.tulip.platform.eventAnno.BearerEvent;
import com.guohuai.tulip.platform.eventAnno.BindingCardEvent;
import com.guohuai.tulip.platform.eventAnno.CashEvent;
import com.guohuai.tulip.platform.eventAnno.CustomEvent;
import com.guohuai.tulip.platform.eventAnno.EmployeeEvent;
import com.guohuai.tulip.platform.eventAnno.FriendEvent;
import com.guohuai.tulip.platform.eventAnno.InvalidBidsEvent;
import com.guohuai.tulip.platform.eventAnno.InvestEvent;
import com.guohuai.tulip.platform.eventAnno.RechargeEvent;
import com.guohuai.tulip.platform.eventAnno.RedeemEvent;
import com.guohuai.tulip.platform.eventAnno.RefundEvent;
import com.guohuai.tulip.platform.eventAnno.RegisterEvent;
import com.guohuai.tulip.platform.eventAnno.SignEvent;
import com.guohuai.tulip.platform.facade.obj.CouponInterestRep;
import com.guohuai.tulip.platform.facade.obj.CouponInterestReq;
import com.guohuai.tulip.platform.facade.obj.CouponWriteOffReq;
import com.guohuai.tulip.platform.facade.obj.EventRep;
import com.guohuai.tulip.platform.facade.obj.EventReq;
import com.guohuai.tulip.platform.facade.obj.MyCouponRep;
import com.guohuai.tulip.platform.facade.obj.MyCouponReq;
import com.guohuai.tulip.platform.userinvest.UserInvestEntity;
import com.guohuai.tulip.platform.userinvest.UserInvestRep;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/tulip/boot/facade", produces = "application/json")
public class FacadeController extends BaseController {
	@Autowired
	private FacadeService facadeService;
	@Autowired
	ApplicationEventPublisher eventPublisher;
	@Autowired
	private UserInvestService userInvestService;
	@Autowired
	private FacadeCouponService facadeCouponService;

	/**
	 * 获取卡券列表
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "获取个人卡券列表")
	@RequestMapping(value = "/getMyCouponList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResp<MyCouponRep>> getMyCouponList(@RequestBody MyCouponReq param) {
		PageResp<MyCouponRep> pages = this.facadeService.getMyCouponList(param);
		return new ResponseEntity<PageResp<MyCouponRep>>(pages, HttpStatus.OK);
	}

	/**
	 * 获取卡券详情
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "获取卡券详情")
	@RequestMapping(value = "/getCouponDetail", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<MyCouponRep> getCouponDetail(@RequestBody MyCouponReq param) {
		MyCouponRep rep = this.facadeService.getCouponDetail(param);
		return new ResponseEntity<MyCouponRep>(rep, HttpStatus.OK);
	}

	/**
	 * 注册事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "注册事件")
	@RequestMapping(value = "/onRegister", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onRegister(@RequestBody RegisterEvent event) {
		
		UserInvestEntity userEntity = new UserInvestEntity();
		BeanUtils.copyProperties(event, userEntity, "investCount");
		userEntity.setRegisterTime(new Timestamp(System.currentTimeMillis()));
		userEntity.setType(UserInvestEntity.TYPE_REFEREE);
		if (!StringUtils.isEmpty(userEntity.getFriendId())) {// 如果存在推荐人的情况
			userInvestService.updateFriends(userEntity.getFriendId());
		}
		userInvestService.createUserInvestEntity(userEntity);
		
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	

	/**
	 * 实名制事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "实名制事件")
	@RequestMapping(value = "/onSetRealName", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onSetRealName(@RequestBody AuthenticationEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 申购事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "申购事件")
	@RequestMapping(value = "/onInvestment", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onInvestment(@RequestBody InvestEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	/**
	 * 赎回事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "赎回事件")
	@RequestMapping(value = "/onRedeem", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onRedeem(@RequestBody RedeemEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 到期兑付事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "到期兑付事件")
	@RequestMapping(value = "/onBearer", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onBearer(@RequestBody BearerEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 提现事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "提现事件")
	@RequestMapping(value = "/onCash", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onCash(@RequestBody CashEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 退款事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "退款事件")
	@RequestMapping(value = "/onRefund", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onRefund(@RequestBody RefundEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	@SystemControllerLog(description = "卡券下发")
	@RequestMapping(value = "/checkIssuedCouponCount", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> checkIssuedCouponCount(String data) {

		log.info("卡券下发请求参数："+data);
		JSONObject jj = JSONObject.parseObject(data);
		if (jj == null) {
			return new ResponseEntity<BaseResp>(new BaseResp(-1,"参数错误"), HttpStatus.OK);
		}
		String eventId = jj.getString("eventId");
		List phones= jj.getJSONArray("phones");
		String type = jj.getString("type");
		
		return new ResponseEntity<BaseResp>(facadeService.checkIssuedCouponCount(eventId, phones, type), HttpStatus.OK);
	}
	
	/**
	 * 卡券下发
	 *
	 * @return
	 */
	@SystemControllerLog(description = "卡券下发")
	@RequestMapping(value = "/issuedCoupon", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> issuedCoupon(String data) {
        String uid = super.getLoginUser();

		log.info("卡券下发请求参数："+data);
		JSONObject jj = JSONObject.parseObject(data);
		if (jj == null) {
			return new ResponseEntity<BaseResp>(new BaseResp(-1,"参数错误"), HttpStatus.OK);
		}
		String eventId = jj.getString("eventId");
		List phones= jj.getJSONArray("phones");
		String type = jj.getString("type");

		return new ResponseEntity<BaseResp>(facadeService.issuedCoupon(uid, eventId, phones, type), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/issuedGroupCoupon", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> issuedGroupCoupon(String data) {

		log.info("卡券下发请求参数："+data);
		JSONObject jj = JSONObject.parseObject(data);
		if (jj == null) {
			return new ResponseEntity<BaseResp>(new BaseResp(-1,"参数错误"), HttpStatus.OK);
		}
		String eventId = jj.getString("eventId");
		String labelCode = jj.getString("labelCode");

		return new ResponseEntity<BaseResp>(facadeService.issuedGroupCoupon(eventId, labelCode), HttpStatus.OK);
	}

	@RequestMapping(value = "/getInvestorlabelList", method = {RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<List<InvestorLabelResp>> getInvestorlabelList() {
		List<InvestorLabelResp> rep = this.facadeService.getInvestorlabelList();
		return new ResponseEntity<List<InvestorLabelResp>>(rep, HttpStatus.OK);
	}

	/**
	 * 获取卡券列表
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "获取申购可用卡券列表")
	@RequestMapping(value = "/getCouponList", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<PageResp<MyCouponRep>> getMyAllCouponList(@RequestBody MyCouponReq param) {
		PageResp<MyCouponRep> pages = this.facadeService.getCouponList(param);
		return new ResponseEntity<PageResp<MyCouponRep>>(pages, HttpStatus.OK);
	}

	/**
	 * 计算卡券利息
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "计算卡券利息")
	@RequestMapping(value = "/couponInterest", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<CouponInterestRep> couponInterest(@RequestBody CouponInterestReq param) {
		CouponInterestRep rep = new CouponInterestRep();
		rep = this.facadeService.couponInterest(param);
		return new ResponseEntity<CouponInterestRep>(rep, HttpStatus.OK);
	}

	/**
	 * 核销卡券
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "核销卡券")
	@RequestMapping(value = "/verificationCoupon", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> verificationCoupon(@RequestBody List<MyCouponReq> param) {
		this.facadeService.verificationCoupon(param);
		return new ResponseEntity<BaseResp>(HttpStatus.OK);
	}


	/**
	 * 推荐人事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "推荐人事件")
	@RequestMapping(value = "/onReferee", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onReferee(@RequestBody FriendEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 绑卡事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "绑卡事件")
	@RequestMapping(value = "/onBindingCard", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onBindingCard(@RequestBody BindingCardEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 充值事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "充值事件")
	@RequestMapping(value = "/onRecharge", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onRecharge(@RequestBody RechargeEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 签到事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "签到事件")
	@RequestMapping(value = "/onSign", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onSign(@RequestBody SignEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	/**
	 * 员工认证事件
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/onEmployee", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onEmployee(@RequestBody EmployeeEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	/**
	 * 检查是否签到
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "检查是否签到")
	@RequestMapping(value = "/checkSignIn", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<Boolean> checkSign(String param) {
		Boolean isSign = this.facadeService.checkSignIn(param);
		return new ResponseEntity<Boolean>(isSign, HttpStatus.OK);
	}

	/**
	 * 流标事件
	 *
	 * @param param
	 * @return
	 */
	@SystemControllerLog(description = "流标事件")
	@RequestMapping(value = "/onInvalidBids", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onInvalidBids(@RequestBody InvalidBidsEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}

	/**
	 * 获取活动奖励金额
	 *
	 * @return
	 */
	@SystemControllerLog(description = "获取活动奖励金额")
	@RequestMapping(value = "/getEventInfo", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<EventRep> getEventInfo(@RequestBody EventReq param) {
		EventRep rep = this.facadeService.getEventCouponMoneyInfo(param);
		return new ResponseEntity<EventRep>(rep, HttpStatus.OK);
	}


	/**
	 * 根据用户ID获取推荐人ID
	 *
	 * @param uid
	 * @return
	 */
	@SystemControllerLog(description = "根据用户ID获取推荐人ID")
	@RequestMapping(value = "/getFriendIdByUid", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<UserInvestRep> getFriendIdByUid(@RequestParam String uid) {
		UserInvestRep rep = this.facadeService.getFriendIdByUid(uid);
		return new ResponseEntity<UserInvestRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/useUserCoupon", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<MyCouponRep> useUserCoupon(@RequestParam String couponId){
		MyCouponReq req=new MyCouponReq();
		req.setCouponId(couponId);
		req.setUserId("67dc0aba35b046f78c273b46b308264a");

		MyCouponRep rep = this.facadeService.useUserCoupon(req);
		return new ResponseEntity<MyCouponRep>(rep, HttpStatus.OK);
	}
	/**
	 * 根据奖励金额和奖励卡券类型获取事件ID
	 *
	 * @param money
	 * @param couponType
	 * @return
	 */
	@RequestMapping(value = "/getCustomEventId", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<EventRep> getCustomEventId(@RequestParam String money, @RequestParam String couponType) {
		EventRep rep = this.facadeService.getCustomEventId(money, couponType);
		return new ResponseEntity<EventRep>(rep, HttpStatus.OK);
	}
	
	@SystemControllerLog(description = "自定义事件")
	@RequestMapping(value = "/onCustom", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> onCustom(@RequestBody CustomEvent event) {
		eventPublisher.publishEvent(JSONObject.toJSONString(event));
		BaseResp resp = new BaseResp();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	/**
	 * 卡券核销
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/writeOff", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> writeOff(CouponWriteOffReq req){
		BaseResp resp = this.facadeCouponService.writeOffCoupon(req);
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/statisticsCouponAmount", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> statisticsCouponAmount(){
		BaseResp resp = this.facadeCouponService.statisticsCouponAmount();
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
}
