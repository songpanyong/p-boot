package com.guohuai.tulip.platform.intervalsetting;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 基数等级resp
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class IntervalSettingRep extends BaseResp {
	/**
	 * oid
	 */
	private String oid;
	/**
	 * 投资起始金额
	 */
	private BigDecimal startMoney;
	/**
	 * 投资结束金额
	 */
	private BigDecimal endMoney;
	/**
	 * 基数
	 */
	private BigDecimal intervalLevel;
	/** 创建用户 */
	private String createUser;
	/** 修改用户 */
	private String updateUser;
	private Timestamp createTime;
	private Timestamp updateTime;
	/** 是否删除 */
	private String isdel;
}
