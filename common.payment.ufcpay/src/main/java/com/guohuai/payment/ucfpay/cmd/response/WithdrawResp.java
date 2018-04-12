package com.guohuai.payment.ucfpay.cmd.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 单笔代付回复.
 * @author jiang_jm
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawResp {
//	{"resCode":"00000","resMessage":"成功"}
	String resCode;//返回Code
	String resMessage;//返回信息
	String merchantId;//商户号
	String merchantNo;//商户订单号
	String tradeNo;//交易号
	String status;//状态
	String tradeTime;//交易时间
	String memo;//保留域
	String amount;
	String transCur;
	String noticeUrl;
}
