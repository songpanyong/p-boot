package com.guohuai.tulip.platform.coupon.redpacket;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class RedpacketDetailRep extends BaseResp  {

	private String oid;
	//红包批次号
	private String couponBatchId;
	
	private String userId;
	
	//红包金额
	private BigDecimal randomAmount;
	
	//发放状态：默认未发放notUsed，已发放used
	private String status;
	
	//生成时间
	private Timestamp createTime;
	
	private Timestamp updateTime;

	public String getCouponBatchId() {
		return couponBatchId;
	}

	public void setCouponBatchId(String couponBatchId) {
		this.couponBatchId = couponBatchId;
	}

	public BigDecimal getRandomAmount() {
		return randomAmount;
	}

	public void setRandomAmount(BigDecimal randomAmount) {
		this.randomAmount = randomAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}
	
}
