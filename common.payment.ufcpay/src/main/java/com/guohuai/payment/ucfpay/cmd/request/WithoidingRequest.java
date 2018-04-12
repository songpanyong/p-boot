package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Builder;
import lombok.Data;


/**
 * 单笔代扣请求. 
 * @author CHENDONGHUI
 *
 */
@Data
@Builder
public class WithoidingRequest {
	String service;//*接口名称
	String secId;//*签名算法
	String version;//*接口版本
	String merchantId;//*商户号
	String merchantNo;//yyyyMMddhhmmssSSS *商户订单号
	String amount;//*金额
	String transCur;//*币种
	String userType;//*用户类型
	String accountType;//*账户类型
	String accountNo;//*账户号
	String accountName;//*账户名称
	String bankId;//*银行编码
	String certificateType;//证件类型
	String certificateNo;//证件号码
	String mobileNo;//手机号
	String branchProvince;//开户省
	String branchCity;//开户市
	String branchName;//开户支行名称
	String productName;//*商品名称
	String productInfo;//商品信息
	String noticeUrl;//*后台通知地址
	String expireTime;//订单超时时间
	String memo;//保留域
	String reqSn;//本次请求序列号，需调用先锋支付类库生成 *序列 号
	String sign;//签名字段值，商户可放到后台处理，DEMO为了直观展示放在表单中 *订单签名
}
