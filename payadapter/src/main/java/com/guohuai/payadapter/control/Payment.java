package com.guohuai.payadapter.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.Data;

@Data
@Entity
@Table(
    name = "t_bank_payment"
)
public class Payment  extends UUID implements Serializable{
	private static final long serialVersionUID = -3286518029485346965L;
	private String userOid;
	private String orderNo;//来源订单号（非必需，存进来为了数据冗余用）
	private String payNo;//支付流水号 
	private String commandStatus;//已保存待发送、已发送待处理、支付成功、支付失败，修改中（修改状态用于异常修改状态时用）（对于自动支付的，状态默认为已发送待处理，代表已经生成报文发银行了；对于手工的，状态为已保存待发送，待人工发送银行后改成已发送待处理；已成功或已失败根据银行返回的信息调整；对于支付状态出现异常的，可能人为修改状态，只能修改成成功或是失败，若是修改成成功的，审核完成之后状态改成支付成功，若是修改成失败的，审核完成后状态改成支付失败。）（只有已保存待发送和支付失败状态才可以往银行发送）
	private String refundMark;//支付退票，未退票。（平安银行和快付通都有退票接口）
	private String merchantId;//商户身份ID
	private String productId;//取通道配置 （根据通道结果保存下来）
	private String bankReturnSeriNo;//银行返回流水号
	private BigDecimal amount;//取单据金额
	private String currency;//取固定值（CNY）
	private String realName;
	private String bankCode;//平安银行叫收款人开户行类别
	private String accountProvince;//平安银行大额跨行时需要
	private String accountCity;//平安银行大额跨行时需要
	private String cardNo;
	private String emergencyMark;//平安银行用，特急S走超级网银，加急Y走T+1
	private String crossFlag;//平安银行用，根据接口通道选择的结果写
	private String distanceMark;//平安银行用，需要跟平安银行核实是否必需
	private String platformAccount;//平安银行用叫付款人账号，快付通叫商户银行账号
	private String platformName;//平安银行用，付款人名称，也可以在组报文时从平台的银行表里取
	private String payAddress;//平安银行用，付款人地址
	private String checkCode;//对账使用，根据不同接口使用不同的对账码
	private int reconStatus=0;//对账结果，默认为未对账:0，对账结果有对账成功:1;和对账失败:2
	private String reconciliationMark;//对账详情
	private String channelNo;
	private String type;
	private String failDetail;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp refundTime;//银行查询状态返回时间
	
	private String operator;
	private Timestamp operatorTime;
	private String operatorReson;
	private String operatorStatus;//单笔支付：01；失败重发：02；撤销：03
	
	private Timestamp auditUpdateTime;
	private Timestamp auditOperatorTime;
	private String auditOperatorStatus;//不通过：0；通过：1
	private String auditUpdateStatus;//不通过：0；通过：1
	private String auditOperator;
	private String auditUpdateOperator;
	private String auditOperatorReson;
	private String auditUpdateReson;
	private String updateStatus;//未处理：0；交易成功：1；交易失败：2；交易处理中：3；超时：4
	private String updateOperator;
	private String updateReson;
	private Timestamp upTime;
	private String launchplatform;//前台：1；后台：2
	private String resetOperator;
	private Timestamp resetOperatorTime;
	private String resetOpertatorReson;
	private String resetOperatorStatus;//单笔支付：01；失败重发：02；撤销：03
	private String auditResetOperatorStatus;
	private String auditResetOperatorReson;
	private Timestamp auditResetOperatorTime;
	private String auditResetOperator;

}
