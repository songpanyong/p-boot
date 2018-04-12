package com.guohuai.payadapter.bankutil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.guohuai.basic.component.ext.hibernate.UUID;

/**
 * 对账Entity
 * @author chendonghui
 *
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "t_bank_card_bin_basic")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BankUtilEntity extends UUID implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 发卡行名称
	 */
	private String bankName;
	/**
	 * 机构代码
	 */
	private String bankCode;
	/**
	 * 卡名
	 */
	private String cardName;
	/**
	 * 卡号长度
	 */
	private int cardLength;
	/**
	 * BIN长度
	 */
	private int BINLength;
	/**
	 * 卡BIN
	 */
	private String bankBin; 
	/**
	 * 卡种
	 */
	private String kindOfCard;
	
}
