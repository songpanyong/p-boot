package com.guohuai.tulip.platform.intervalsetting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.tulip.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 基数等级实体
 *
 * @author zhoujunliang
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@ToString
public class IntervalSettingReq implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 7829424417740108768L;
	
	private String oid;
	/** 投资起始金额 */
	private BigDecimal startMoney;
	/** 投资结束金额 */
	private BigDecimal endMoney;
	/** 基数 */
	private BigDecimal intervalLevel;
	/** 创建用户 */
	private String createUser;
	/** 创建时间 */
	private Timestamp createTime=DateUtil.getSqlCurrentDate();
	/** 修改时间 */
	private Timestamp updateTime=DateUtil.getSqlCurrentDate();
	/** 修改用户 */
	private String updateUser;
	/** 是否删除 */
	private String isdel="no";

}
