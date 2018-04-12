package com.guohuai.tulip.platform.statistic;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.basic.component.ext.hibernate.UUID;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
/**
 * 统计分析实体
 * @author suzhicheng
 *
 */
@Entity
@Table(name = "T_TULIP_STATISTICAL")
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class StatisticEntity extends UUID{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1657185028997409827L;
	/**
	 * 奖品编号
	 */
	String cid;
	/**
	 * 发放数量
	 */
	Integer provideCount;
	/**
	 * 领取数量
	 */
	Integer receiveCount;
	/**
	 * 使用数量
	 */
	Integer useCount;
	/**
	 * 已核销数量
	 */
	Integer closureCount;
	/**
	 * 奖品描述
	 */
	String description;
	/**
	 * 转发投资额
	 */
	BigDecimal amount;
	/**
	 * 卡券名称
	 */
	String couponName;
	/**
	 * 获取 cid
	 */
	public String getCid() {
		return cid;
	}
	/**
	 * 设置 cid
	 */
	public void setCid(String cid) {
		this.cid = cid;
	}
	/**
	 * 获取 provideCount
	 */
	public Integer getProvideCount() {
		return provideCount;
	}
	/**
	 * 设置 provideCount
	 */
	public void setProvideCount(Integer provideCount) {
		this.provideCount = provideCount;
	}
	/**
	 * 获取 receiveCount
	 */
	public Integer getReceiveCount() {
		return receiveCount;
	}
	/**
	 * 设置 receiveCount
	 */
	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}
	/**
	 * 获取 useCount
	 */
	public Integer getUseCount() {
		return useCount;
	}
	/**
	 * 设置 useCount
	 */
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	/**
	 * 获取 closureCount
	 */
	public Integer getClosureCount() {
		return closureCount;
	}
	/**
	 * 设置 closureCount
	 */
	public void setClosureCount(Integer closureCount) {
		this.closureCount = closureCount;
	}
	/**
	 * 获取 description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取 amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * 设置 amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * 获取 couponName
	 */
	public String getCouponName() {
		return couponName;
	}
	/**
	 * 设置 couponName
	 */
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	
}
