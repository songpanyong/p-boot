package com.guohuai.payadapter.redeem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate(true)
@Table(name = "t_bank_history")
public class HistoryTransactions extends UUID implements Serializable {
	private static final long serialVersionUID = -3286518029485346965L;
	private String bankType;
	private String accountNo;
	private String currency;
	private Date tradTime;
	private String hostSerial;
	private String transactionFlow;
	private String paymentPartyNetwork;
	private String paymentallianceCode;
	private String paymentName;
	private String paymentPartyAccount;
	private String paymentAccount;
	private String settlementCurrency;
	private BigDecimal tradAmount;
	private String receivParty;
	private String payeeContact;
	private String beneficiaryBankName;
	private String beneficiaryAccount;
	private String payeeName;
	private String lendMark;
	private String stract;
	private String voucher;
	private BigDecimal fee;
	private BigDecimal postFee;
	private BigDecimal accountBalance;
	private String postscript;
	private String chineseAbstract;
	private String customerCustom;
	private String reconciliationMark;
	private String userOid;
	private Date createTime;
	private Date updateTime;
	private int reconStatus=0;//对账结果，默认为未对账:0，对账结果有对账成功:1;和对账失败:2,对账忽略:3
	private String tradStatus="1";//默认交易成功1,交易失败0
	private String hostErrorCode;//错误码
	private String backRem;//错误信息
	
}