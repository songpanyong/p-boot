package com.guohuai.payadapter.control;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(
    name = "t_bank_channel"
)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Channel extends UUID implements Serializable{
	   private static final long serialVersionUID = -6004709475834256278L;
	   private String userOid;
	   private String sourceType;//01，国槐
	   private String channelNo;
	   private String channelName;
	   private String tradeType;
	   private String tradeTypeDesc;
	   private BigDecimal minAmount;
	   private BigDecimal maxAmount;
	   private int paymentPrescription;
	   private String rateCalclationMethod;
	   private BigDecimal rate;//比如每笔1元，比如0.003（千分之3）
	   private int onlySpeed;//1，两小时,2一天
	   private String status;//启用，禁用
	   private String bankAccount;//平台的账号。对于快付通收款，定义收到哪个账号里，快付通如果需要T+0到账的话，这个字段是必需；对于平安银行付款接口，定义从平台哪个银行账号付出去。
	   private String peerCross;//同行跨行 根据收款行是否平安银行进行判断
	   private String merchantId;//配合第三方通道使用
	   private String productId;//配合第三方通道使用，扣款交易的产品编号
	   private String treatmentMethod;//1.实时支付 2.人工；如果结果是实时处理，则调用接口进行支付。
	   private Timestamp createTime;
	   private Timestamp updateTime;

}
