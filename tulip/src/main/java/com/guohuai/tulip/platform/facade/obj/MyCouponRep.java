package com.guohuai.tulip.platform.facade.obj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.guohuai.basic.component.ext.web.BaseResp;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MyCouponRep extends BaseResp implements Serializable{

	private static final long serialVersionUID = 1L;
	/** 卡券ID */
	private String couponId;
	/** 用户ID */
	private String userId;
	/** 卡券批次号 */
	private String couponBatch;
	/** 状态 */
	private String status; 
	/** 名称 */
	private String name; 
	/** 描述 */
	private String description;
	/** 金额类型 */
	private String amountType;
	/** 卡券类型 */
	private String type;
	/** 业务系统订单号 */
	private String orderCode;
	/** 体验天数 */
	private Integer validPeriod;
	/** 开始时间 */
	private Timestamp start;
	/** 结束时间 */
	private Timestamp finish;
	/** 金额 */
	private BigDecimal amount;
	/** 最大收益金额 */
	private BigDecimal maxRateAmount;
	/** 投资金额 */
	private BigDecimal investAmount;
	/** 投资金额 */
	private Integer investTime;
	/** 领用时间 */
	private Timestamp leadTime;
	/** 核销时间 */
	private Timestamp settlement;
	/** 使用时间 */
	private Timestamp useTime;
	/** 产品集 */
	private String products;
	/** 规则集 */
	private String rules;
	/** 活动类型 */
	private String eventType;
	/** 手机号 */
	private String phone;
	
	/** 用户-可使用卡券数量 */
	private Integer notUsedNum = 0;
	/** 用户-已使用卡券数量 */
	private Integer usedNum = 0;
	/** 用户- 已过期卡券数量 */
	private Integer expiredNum = 0;
	/**
	 * 获取 orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * 设置 orderCode
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * 获取 maxRateAmount
	 */
	public BigDecimal getMaxRateAmount() {
		return maxRateAmount;
	}

	/**
	 * 设置 maxRateAmount
	 */
	public void setMaxRateAmount(BigDecimal maxRateAmount) {
		this.maxRateAmount = maxRateAmount;
	}

	/**
	 * 获取 investAmount
	 */
	public BigDecimal getInvestAmount() {
		return investAmount;
	}

	/**
	 * 设置 investAmount
	 */
	public void setInvestAmount(BigDecimal investAmount) {
		this.investAmount = investAmount;
	}

	/**
	 * 获取 validPeriod
	 */
	public Integer getValidPeriod() {
		return validPeriod;
	}

	/**
	 * 设置 validPeriod
	 */
	public void setValidPeriod(Integer validPeriod) {
		this.validPeriod = validPeriod;
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
	 * 获取 userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置 userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	

	/**
	 * 获取 status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置 status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 获取 name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置 name
	 */
	public void setName(String name) {
		this.name = name;
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
	 * 获取 amountType
	 */
	public String getAmountType() {
		return amountType;
	}

	/**
	 * 设置 amountType
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	/**
	 * 获取 type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置 type
	 */
	public void setType(String type) {
		this.type = type;
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
	 * 获取 leadTime
	 */
	public Timestamp getLeadTime() {
		return leadTime;
	}

	/**
	 * 设置 leadTime
	 */
	public void setLeadTime(Timestamp leadTime) {
		this.leadTime = leadTime;
	}

	/**
	 * 获取 settlement
	 */
	public Timestamp getSettlement() {
		return settlement;
	}

	/**
	 * 设置 settlement
	 */
	public void setSettlement(Timestamp settlement) {
		this.settlement = settlement;
	}

	/**
	 * 获取 useTime
	 */
	public Timestamp getUseTime() {
		return useTime;
	}

	/**
	 * 设置 useTime
	 */
	public void setUseTime(Timestamp useTime) {
		this.useTime = useTime;
	}

	/**
	 * 获取 products
	 */
	public String getProducts() {
		return products;
	}

	/**
	 * 设置 products
	 */
	public void setProducts(String products) {
		this.products = products;
	}

	/**
	 * 获取 rules
	 */
	public String getRules() {
		return rules;
	}

	/**
	 * 设置 rules
	 */
	public void setRules(String rules) {
		this.rules = rules;
	}

	/**
	 * 获取 eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * 设置 eventType
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * 获取 phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 设置 phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 获取 start
	 */
	public Timestamp getStart() {
		return start;
	}

	/**
	 * 设置 start
	 */
	public void setStart(Timestamp start) {
		this.start = start;
	}

	/**
	 * 获取 finish
	 */
	public Timestamp getFinish() {
		return finish;
	}

	/**
	 * 设置 finish
	 */
	public void setFinish(Timestamp finish) {
		this.finish = finish;
	}

	/**
	 * 获取 couponBatch
	 */
	public String getCouponBatch() {
		return couponBatch;
	}

	/**
	 * 设置 couponBatch
	 */
	public void setCouponBatch(String couponBatch) {
		this.couponBatch = couponBatch;
	}

	/**
	 * 获取 investTime
	 */
	public Integer getInvestTime() {
		return investTime;
	}

	/**
	 * 设置 investTime
	 */
	public void setInvestTime(Integer investTime) {
		this.investTime = investTime;
	}

	/**
	 * 获取 notUsedNum
	 */
	public Integer getNotUsedNum() {
		return notUsedNum;
	}

	/**
	 * 设置 notUsedNum
	 */
	public void setNotUsedNum(Integer notUsedNum) {
		this.notUsedNum = notUsedNum;
	}

	/**
	 * 获取 usedNum
	 */
	public Integer getUsedNum() {
		return usedNum;
	}

	/**
	 * 设置 usedNum
	 */
	public void setUsedNum(Integer usedNum) {
		this.usedNum = usedNum;
	}

	/**
	 * 获取 expiredNum
	 */
	public Integer getExpiredNum() {
		return expiredNum;
	}

	/**
	 * 设置 expiredNum
	 */
	public void setExpiredNum(Integer expiredNum) {
		this.expiredNum = expiredNum;
	}
	
	

}
