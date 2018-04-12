package com.guohuai.payadapter.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.common.payment.jytpay.utils.DateTimeUtils;
import com.guohuai.payadapter.component.CallBackEnum;
import com.guohuai.payadapter.component.Constant;
import com.guohuai.payadapter.listener.event.TradeRecordEvent;
import com.guohuai.payment.baofu.api.BaoFuService;
import com.guohuai.payment.baofu.cmd.CertPayQueryRequest;
import com.guohuai.payment.baofu.cmd.CertPayQueryRequest.CertPayQueryRequestBuilder;
import com.guohuai.payment.baofu.cmd.CertPayQueryResp;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: BaofooQryListener
 * @Description: 宝付认证支付查询
 * @date 2016年11月8日 下午4:41:01
 *
 */
@Slf4j
@Component
public class BaofooPayQryListener {

	@Autowired
	private BaoFuService baofuService;
	
	@Value("${withOutThirdParty:no}")
	private String withOutThirdParty;
	
	@EventListener(condition = "#event.channel == '10'")
	public void payEvent(TradeRecordEvent event) {
		String trans_no = StringUtil.uuid();//商户流水号
		log.info("宝付认证支付查询订单  payEvent: {},查询流水号{}", JSONObject.toJSON(event),trans_no);
		String orig_trans_id = event.getOrderNo();
		String trade_date = DateTimeUtils.getNowDateStr(DateTimeUtils.DATETIME_FORMAT_YYYYMMDDHHMMSS);
		
		CertPayQueryRequestBuilder request = CertPayQueryRequest.builder().trans_serial_no(trans_no).orig_trans_id(orig_trans_id).orig_trade_date(trade_date);
		CertPayQueryRequest req = request.build();
		CertPayQueryResp res = new CertPayQueryResp();
		
		//不经过三方直接返回结果
		if("yes".equals(withOutThirdParty)){
			log.info("withOutThirdParty = yes");
			event.setReturnCode(Constant.SUCCESS);
			event.setErrorDesc("交易成功");
			return;
		}
		
		try {
			res =  baofuService.certPayQuery(req);
		} catch (Exception e) {
			log.error( "宝付认证支付查询订单异常:exception:{}", e);
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("系统异常");
			e.printStackTrace();
		}
		log.info("接口返回参数："+res);
		if(null !=res){
			if("0000".equals(res.getResp_code())
					||"BF00114".equals(res.getResp_code())
					||"BF00100".equals(res.getResp_code())
					||"BF00112".equals(res.getResp_code())
					||"BF00113".equals(res.getResp_code())
					||"BF00115".equals(res.getResp_code())
					||"BF00144".equals(res.getResp_code())
					||"BF00202".equals(res.getResp_code())){
					if("S".equals(res.getOrder_stat())){
						event.setReturnCode(Constant.SUCCESS);
						event.setStatus(CallBackEnum.SUCCESS.getCode());
					}else if( "F".equals(res.getOrder_stat()) || "FF".equals(res.getOrder_stat())){
						event.setReturnCode(Constant.FAIL);
						event.setStatus(CallBackEnum.FAIL.getCode());
					}else{
						event.setReturnCode(Constant.INPROCESS);
						event.setStatus(CallBackEnum.PROCESSING.getCode());
					}
			}else{
				event.setReturnCode(Constant.FAIL);
				event.setErrorDesc(res.getResp_msg());
			}
		}else{
			event.setReturnCode(Constant.FAIL);
			event.setErrorDesc("宝付认证支付交易返回参数为空");
			event.setStatus(CallBackEnum.FAIL.getCode()); 
		}

		log.info("宝付认证支付查询返回结果,returnCode:{},returnMsg:{}",event.getReturnCode(),event.getErrorDesc());
	}

}
