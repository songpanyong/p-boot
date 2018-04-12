package com.guohuai.basic.component.repayment.rate;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.alibaba.fastjson.JSONObject;

public class YearYieldRate implements YieldRate {

	// 年化利率
	private BigDecimal rateOfYear;
	// 合同年天数
	private int daysOfYear;
	// 日利率
	private BigDecimal rate;

	public YearYieldRate(/* 年化利率 */ BigDecimal rateOfYear, /* 合同年天数 */ int daysOfYear) {
		this.rateOfYear = rateOfYear;
		this.daysOfYear = daysOfYear;
		this.rate = rateOfYear.divide(new BigDecimal(daysOfYear), 16, RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal dailyRate() {
		return this.rate;
	}

	@Override
	public BigDecimal monthRate() {
		return this.rateOfYear.divide(new BigDecimal(12), 16, RoundingMode.HALF_UP);
	}

	@Override
	public String toString() {
		JSONObject json = new JSONObject();
		json.put("rateOfYear", this.rateOfYear);
		json.put("daysOfYear", this.daysOfYear);
		json.put("rate", this.rate);
		return json.toJSONString();
	}

}
