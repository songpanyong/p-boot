package com.guohuai.payment.ucfpay.cmd.request;

import lombok.Builder;
import lombok.Data;


/**
 * 单笔代付请求. 
 * @author JIANGJIANMIN
 *
 */
@Data
@Builder
public class WithdrawRequest {
	String service;//*接口名称
	String secId;//*签名算法
	String version;//*接口版本
	String reqSn;//本次请求序列号，需调用先锋支付类库生成 *序列 号
	String merchantId;//*商户号
	String merchantNo;//yyyyMMddhhmmssSSS *商户订单号
	String source;//*来源
	String amount;//*金额
	String transCur;//*币种
	String userType;//*用户类型  固定值： 或者 （ 1 2 1：对私 2：对公）
	String accountNo;//*账户号
	String accountName;//*账户名称
	String accountType;//*账户类型
	String mobileNo;//手机号
	String bankNo;//*银行编码 ，如：ICBC ABC
	String issuer;//*联行号（注：当use rType=2时联行号 为必填项，如userType=1时联行号为 非必填项）
	String branchProvince;//开户省
	String branchCity;//开户市
	String branchName;//开户支行名称
	String noticeUrl;//*后台通知地址
	String memo;//保留域
	String sign;//签名字段值，商户可放到后台处理，DEMO为了直观展示放在表单中 *订单签名
}
