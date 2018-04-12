package com.guohuai.payment.ucfpay.cmd;

import lombok.Builder;
import lombok.Data;


/**
 * 单笔订单查询请求. 
 * @author CHENDONGHUI
 *
 */
@Data
@Builder
public class ReqWithdrawQueryRequest {
	String service;//*接口名称
	String secId;//*签名算法
	String version;//*接口版本
	String merchantId;//*商户号
	String merchantNo;//yyyyMMddhhmmssSSS *商户订单号
	String reqSn;//本次请求序列号，需调用先锋支付类库生成 *序列 号
	String sign;//签名字段值，商户可放到后台处理，DEMO为了直观展示放在表单中 *订单签名
}
