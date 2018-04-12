package com.guohuai.tulip.platform.coupon.couponRule;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.guohuai.basic.component.ext.hibernate.UUID;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 卡券规则关系实体
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_COUPON_RULE")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CouponRuleEntity extends UUID {
	
	private static final long serialVersionUID = -73089949195904221L;
	/**
	 * 规则编号
	 */
	String ruleId;
	/**
	 * 卡券编号
	 */
	String couponId;
	/**
	 * 创建时间
	 */
	Timestamp createTime=new Timestamp(System.currentTimeMillis());
	/**
	 * 获取 ruleId
	 */
	public String getRuleId() {
		return ruleId;
	}
	/**
	 * 设置 ruleId
	 */
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	/**
	 * 获取 couponId
	 */
	public String getCouponId() {
		return couponId;
	}
	/**
	 * 设置 couponId
	 */
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	/**
	 * 获取 createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * 设置 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CouponRuleEntity [ruleId=" + ruleId + ", couponId=" + couponId + ", createTime=" + createTime + "]";
	};

	
}
