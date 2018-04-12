package com.guohuai.tulip.platform.intervalsetting;

import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 基数等级实体
 */
@Entity
@Data
@Table(name = "T_TULIP_INTERVAL_SETTING")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class IntervalSettingEntity extends UUID {
	
	public static final String ISDEL_YES="yes";
	public static final String ISDEL_NO="no";
	/**
	 *
	 */
	private static final long serialVersionUID = 7829424417740108768L;

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
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	private String createUser;
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;
	private String updateUser;
	/**
	 * 是否删除yes:no
	 */
	private String isdel;


}
