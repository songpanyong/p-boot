package com.guohuai.payadapter.redeem;

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
@Table(
    name = "t_bank_callback_log"
)
public class CallBackLog extends UUID {
	
	private static final long serialVersionUID = 8919239421828196388L;
	/**
	 * 主表id
	 */
	private String callBackOid;
	/**
	 * 支付流水号
	 */
	private String payNo;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 银行返回代码
	 */
	private String returnCode;
	/**
	 * 银行返回错误信息
	 */
	private String returnMsg;
	private Date createTime;
	private Date updateTime;
	/**
	 * 银行返回流水号
	 */
	private String bankReturnSerialId;
}
