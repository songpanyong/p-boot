package com.guohuai.tulip.platform.facade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.tulip.enums.ErrorCodeEnum;
import com.guohuai.tulip.platform.coupon.CouponEntity;
import com.guohuai.tulip.platform.coupon.CouponService;
import com.guohuai.tulip.platform.coupon.StatisticsCouponDetailResp;
import com.guohuai.tulip.platform.coupon.StatisticsCouponResp;
import com.guohuai.tulip.platform.coupon.userCoupon.UserCouponService;
import com.guohuai.tulip.platform.facade.obj.CouponWriteOffReq;
import com.guohuai.tulip.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@EnableAsync
@Configuration
@Transactional
public class FacadeCouponService {

	@Autowired
	private CouponService couponService;
	@Autowired
	private UserCouponService userCouponService;
	
	/**
	 * 检查卡券核销参数
	 * 
	 * @param req
	 * @return
	 */
	private BaseResp checkCouponWriteOffReq(CouponWriteOffReq req){
		BaseResp resp = new BaseResp();
		resp.setErrorCode(0);
		if(StringUtil.isEmpty(req.getCouponOid())){
			resp.setErrorCode(ErrorCodeEnum.COUPON_ID_ISEMPTY.getCode());
			resp.setErrorMessage(ErrorCodeEnum.COUPON_ID_ISEMPTY.getMessage());
			return resp;
		}
		
		if(null == req.getSettlementTime()){
			resp.setErrorCode(ErrorCodeEnum.COUPON_WRITEOFF_TIME_ISEMPTY.getCode());
			resp.setErrorMessage(ErrorCodeEnum.COUPON_WRITEOFF_TIME_ISEMPTY.getMessage());
			return resp;
		}
		
		if(null == req.getWriteOffAmount()){
			resp.setErrorCode(ErrorCodeEnum.COUPON_WRITEOFF_AMOUNT_ISEMPTY.getCode());
			resp.setErrorMessage(ErrorCodeEnum.COUPON_WRITEOFF_AMOUNT_ISEMPTY.getMessage());
			return resp;
		}
		return resp;
	}
	
	/**
	 * 卡券核销
	 * 
	 * @param req
	 * @return
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public BaseResp writeOffCoupon(CouponWriteOffReq req){
		log.info("卡券核销，请求参数[{}]", JSONObject.toJSONString(req));
		BaseResp resp = this.checkCouponWriteOffReq(req);
		if(resp.getErrorCode() != 0){
			return resp;
		}
		int num = userCouponService.updateWriteOffAmount(req.getCouponOid(), req.getWriteOffAmount(), req.getSettlementTime());
		if(num < 1){
			resp.setErrorCode(ErrorCodeEnum.COUPON_WRITEOFF_FAIL.getCode());
			resp.setErrorMessage(ErrorCodeEnum.COUPON_WRITEOFF_FAIL.getMessage());
			return resp;
		}
		
		couponService.updateWriteOffTotalAmount(req.getCouponOid(), req.getWriteOffAmount());
		log.info("卡券核销，返回值[{}]", JSONObject.toJSONString(resp));
		return resp;
	}
	
	/**
	 * 平台信息管理--运营兑付统计查询接口
	 * @return
	 */
	public StatisticsCouponResp statisticsCouponAmount(){
		StatisticsCouponResp resp = new StatisticsCouponResp();
		//1.统计已创建总金额，需求固定只查询红包、代金券的已创建总金额
		List<String> types = new ArrayList<String>();
		types.add(CouponEntity.COUPON_TYPE_redPackets);
		types.add(CouponEntity.COUPON_TYPE_coupon);
		List<Object[]> createList = couponService.findAllCreateAmount(types);
		if(CollectionUtils.isNotEmpty(createList)){
			Map<String, Object> createMap = new HashMap<String, Object>(4);//最多四种卡券
			for (Object[] object : createList) {
				createMap.put(object[0] + "", object[1]);
			}
			//2.统计兑付信息
			List<StatisticsCouponDetailResp> operateCashList1 = new ArrayList<>();
			List<StatisticsCouponDetailResp> operateCashList2 = new ArrayList<>();
			List<Object[]> cashList = userCouponService.findSumCashInfo();
			for (Object[] cash : cashList) {
				//[0]卡券类型，[1]已发放金额，[2]已兑付金额（非代金券），[3]代金券已兑付金额,[4]已过期金额
				StatisticsCouponDetailResp detail = new StatisticsCouponDetailResp();
				detail.setCouponType(cash[0]+"");
				detail.setAllGrantAmount(new BigDecimal(cash[1] +"")); //已发放金额
				detail.setAllCashAmount(new BigDecimal(cash[2] +"")); // 已兑付金额
				if(CouponEntity.COUPON_TYPE_coupon.equals(cash[0])){
					BigDecimal allcashAmount = new BigDecimal(cash[3] +"");
					//代金券已兑付=已使用的（3）+已核销的（2）
					detail.setAllCashAmount(allcashAmount.add(new BigDecimal(cash[2] +"")));
				}
				detail.setAllDueAmount(new BigDecimal(cash[4] +"")); // 已过期金额
				//待兑付总额 = 已发放-已兑付-已过期
				detail.setUnCashAmount(detail.getAllGrantAmount().subtract(detail.getAllCashAmount()).subtract(detail.getAllDueAmount()));
				if(CouponEntity.COUPON_TYPE_coupon.equals(cash[0]) || CouponEntity.COUPON_TYPE_redPackets.equals(cash[0])){
					detail.setAllCreateAmount(new BigDecimal(createMap.get(cash[0]) + ""));//已创建总金额
					operateCashList1.add(detail); //存入红包、代金券兑付统计信息
				} else {
					operateCashList2.add(detail);
				}
			}
			resp.setOperateCashList1(operateCashList1);
			resp.setOperateCashList2(operateCashList2);
		}
		return resp;
	}
}
