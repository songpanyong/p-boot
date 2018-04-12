package com.guohuai.basic.component.repayment;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;

import com.guohuai.basic.common.DateUtil;
import com.guohuai.basic.component.repayment.mode.MonthDaysMode;
import com.guohuai.basic.component.repayment.rate.YieldRate;

import lombok.Data;
import lombok.NoArgsConstructor;

public class RepaymentPlan {

	// 到期一次还本付息
	public static final String PAYMENT_METHOD_A_DEBT_SERVICE_DUE = "A_DEBT_SERVICE_DUE";

	// 按月付息到期还本
	public static final String PAYMENT_METHOD_EACH_INTEREST_RINCIPAL_DUE = "EACH_INTEREST_RINCIPAL_DUE";

	// 等额本息
	public static final String PAYMENT_METHOD_FIXED_PAYMENT_MORTGAGE = "FIXED-PAYMENT_MORTGAGE";

	// 等额本金
	public static final String PAYMENT_METHOD_FIXED_BASIS_MORTGAGE = "FIXED-BASIS_MORTGAGE";

	// 首期付息按月还本
	public static final String PAYMENT_METHOD_FIRST_INTEREST_FIXED_BASIS = "FIRST_INTEREST_FIXED-BASIS";

	/**
	 * 到期一次还本付息
	 * 
	 * @param startDate
	 *            收益起始日
	 * @param endDate
	 *            收益截止日
	 * @param principal
	 *            借款本金
	 * @param rate
	 *            收益率
	 * @return 还款计划列表
	 */
	public static List<RepaymentPlan.Plan> aDebtServiceDue(Date startDate, Date endDate, BigDecimal principal, YieldRate rate) {
		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();

		int intervalDays = DateUtil.intervalDays(startDate, endDate);
		// 收益 = 本金 * 日利率 * 计息天数
		BigDecimal interest = rate.dailyRate().multiply(new BigDecimal(intervalDays)).multiply(principal.setScale(2, RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);

		RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
		plan.setIssue(1);
		plan.setDueDate(endDate);
		plan.setIntervalDays(intervalDays);
		plan.setStartDate(startDate);
		plan.setEndDate(DateUtil.addDays(endDate, -1));
		plan.setPrincipal(principal.setScale(2, RoundingMode.HALF_UP));
		plan.setInterest(interest);
		plan.setRepayment(principal.add(interest).setScale(2, RoundingMode.HALF_UP));
		plan.appendTo(plans);

		return plans;
	}

	/**
	 * 按月付息到期还本
	 * 
	 * @param startDate
	 *            收益起始日
	 * @param endDate
	 *            收益截止日
	 * @param principal
	 *            借款本金
	 * @param rate
	 *            收益率
	 * @param repaymentDate
	 *            还款日
	 * @param mdmode
	 *            每月天数模型
	 * @return 还款计划列表
	 */
	public static List<RepaymentPlan.Plan> eachInterestRincipalDue(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {

		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();

		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);

		for (int i = 0; i < amors.size(); i++) {
			Amortization amor = amors.get(i);
			RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
			plan.setIssue(i + 1);
			plan.setDueDate(amor.getDueDate());
			plan.setIntervalDays(amor.getIntervalDays());
			plan.setStartDate(amor.getStartDate());
			plan.setEndDate(amor.getEndDate());
			plan.setPrincipal(amor.isLastRepayment() ? principal.setScale(2, RoundingMode.HALF_UP) : BigDecimal.ZERO);
			plan.setInterest(principal.setScale(2, RoundingMode.HALF_UP).multiply(new BigDecimal(amor.getIntervalDays())).multiply(rate.dailyRate()).setScale(2, RoundingMode.HALF_UP));
			plan.setRepayment(plan.getPrincipal().add(plan.getInterest()));
			plan.appendTo(plans);
		}

		return plans;

	}

	/**
	 * 等额本息 设贷款金额为a,月利率为i,分期次数为n,月还款额为b,则 b =〔a × i ×（1 + i）＾n〕÷〔（1 + i）＾n - 1〕
	 * 
	 * @param startDate
	 *            收益起始日
	 * @param endDate
	 *            收益截止日
	 * @param principal
	 *            借款本金
	 * @param rate
	 *            收益率
	 * @param repaymentDate
	 *            还款日
	 * @return 还款计划列表
	 */
	public static List<RepaymentPlan.Plan> fixedPaymentMortgage(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {

		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();

		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);

		// 计算总共几个月
		int totalMonths = amors.size();
		BigDecimal modYield = (rate.monthRate().add(BigDecimal.ONE)).pow(totalMonths).setScale(16, RoundingMode.HALF_UP);
		//BigDecimal modYield = new BigDecimal(Math.pow(rate.monthRate().add(BigDecimal.ONE).doubleValue(), totalMonths)).setScale(16, RoundingMode.HALF_UP);
		System.out.println(modYield);

		// 剩余本金
		BigDecimal fixedPrincipal = principal.setScale(2, RoundingMode.HALF_UP);
		// 每月应还
		BigDecimal monthRepayment = fixedPrincipal.multiply(rate.monthRate()).multiply(modYield).divide(modYield.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);

		System.out.println(monthRepayment);

		for (int i = 0; i < amors.size(); i++) {

			Amortization amor = amors.get(i);

			// 本月利息
			BigDecimal interest0 = fixedPrincipal.multiply(rate.monthRate()).setScale(2, RoundingMode.HALF_UP);
			// 本月本金
			BigDecimal principal0 = monthRepayment.subtract(interest0).setScale(2, RoundingMode.HALF_UP);
			// 实际利息
			interest0 = interest0.multiply(amor.partMonth).setScale(2, RoundingMode.HALF_UP);
			// 本月应还
			BigDecimal repayment0 = principal0.add(interest0);

			fixedPrincipal = fixedPrincipal.subtract(principal0);

			RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
			plan.setIssue(i + 1);
			plan.setDueDate(amor.getDueDate());
			plan.setIntervalDays(amor.getIntervalDays());
			plan.setStartDate(amor.getStartDate());
			plan.setEndDate(amor.getEndDate());
			plan.setPrincipal(principal0);
			plan.setInterest(interest0);
			plan.setRepayment(repayment0);
			plan.appendTo(plans);
		}

		return plans;
	}

	/*
	public static List<RepaymentPlan.Plan> fixedPaymentMortgage(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {
	
		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();
	
		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);
	
		// 计算总共几个月
		BigDecimal totalMonths = BigDecimal.ZERO;
		for (int i = 0; i < amors.size(); i++) {
			totalMonths = totalMonths.add(amors.get(i).getPartMonth());
			// Amortization amor = amors.get(i);
			// Object[] x = {
			// DateUtil.formatDate(amor.getStartDate().getTime()),
			// DateUtil.formatDate(amor.getEndDate().getTime()),
			// DateUtil.formatDate(amor.getStartDateE().getTime()),
			// DateUtil.formatDate(amor.getEndDateE().getTime()),
			// DateUtil.formatDate(amor.getDueDate().getTime()),
			// amor.getIntervalDays(), amor.isLastRepayment(),
			// amor.isOverRepayment() };
			// System.out.println(String.format("%s\t%s\t%s\t%s\t%s\t%s\t%s\t%s",
			// x));
		}
	
		BigDecimal modYield = new BigDecimal(Math.pow(rate.monthRate().add(BigDecimal.ONE).doubleValue(), totalMonths.doubleValue())).setScale(16, RoundingMode.HALF_UP);
	
		// 剩余本金
		BigDecimal fixedPrincipal = principal.setScale(2, RoundingMode.HALF_UP);
		// 每月应还
		BigDecimal monthRepayment = fixedPrincipal.multiply(rate.monthRate()).multiply(modYield).divide(modYield.subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
	
		for (int i = 0; i < amors.size(); i++) {
	
			Amortization amor = amors.get(i);
	
			// 本月应还
			BigDecimal repayment0 = monthRepayment.multiply(amor.partMonth).setScale(2, RoundingMode.HALF_UP);
			// 本月利率
			BigDecimal monthRate0 = rate.monthRate().multiply(amor.partMonth).setScale(16, RoundingMode.HALF_UP);
			// 本月利息
			BigDecimal interest0 = fixedPrincipal.multiply(monthRate0).setScale(2, RoundingMode.HALF_UP);
			// 本月本金
			BigDecimal principal0 = repayment0.subtract(interest0).setScale(2, RoundingMode.HALF_UP);
	
			fixedPrincipal = fixedPrincipal.subtract(principal0);
	
			// Object[] x = {
			// DateUtil.formatDate(amor.getStartDate().getTime()),
			// DateUtil.formatDate(amor.getEndDate().getTime()), repayment0,
			// principal0, interest0, amor.overRepayment };
			// System.out.println(String.format("%s\t%s\t%s\t%s\t%s\t%s", x));
	
			RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
			plan.setIssue(i + 1);
			plan.setDueDate(amor.getDueDate());
			plan.setIntervalDays(amor.getIntervalDays());
			plan.setStartDate(amor.getStartDate());
			plan.setEndDate(amor.getEndDate());
			plan.setPrincipal(principal0);
			plan.setInterest(interest0);
			plan.setRepayment(repayment0);
			plan.appendTo(plans);
		}
	
		return plans;
	}
	*/

	/**
	 * 等额本金
	 * 
	 * @param startDate
	 *            收益起始日
	 * @param endDate
	 *            收益截止日
	 * @param principal
	 *            借款本金
	 * @param rate
	 *            收益率
	 * @param repaymentDate
	 *            还款日
	 * @param mdmode
	 *            每月天数模型
	 * @return 还款计划列表
	 */
	public static List<RepaymentPlan.Plan> fixedBasisMortgage(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {

		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();

		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);

		// 计算总共几个月
		int totalMonths = amors.size();

		// 剩余本金
		BigDecimal fixedPrincipal = principal.setScale(2, RoundingMode.HALF_UP);
		// 每月本金
		BigDecimal partPrincipal = fixedPrincipal.divide(new BigDecimal(totalMonths), 2, RoundingMode.HALF_UP);

		for (int i = 0; i < amors.size(); i++) {

			Amortization amor = amors.get(i);

			// 本月本金
			BigDecimal principal0 = partPrincipal;
			// 本月利息
			BigDecimal interest0 = fixedPrincipal.multiply(new BigDecimal(amor.getIntervalDays())).multiply(rate.dailyRate()).setScale(2, RoundingMode.HALF_UP);
			// 本月还款额
			BigDecimal repayment0 = principal0.add(interest0);

			fixedPrincipal = fixedPrincipal.subtract(principal0);

			RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
			plan.setIssue(i + 1);
			plan.setDueDate(amor.getDueDate());
			plan.setIntervalDays(amor.getIntervalDays());
			plan.setStartDate(amor.getStartDate());
			plan.setEndDate(amor.getEndDate());
			plan.setPrincipal(principal0);
			plan.setInterest(interest0);
			plan.setRepayment(repayment0);
			plan.appendTo(plans);
		}

		return plans;
	}

	/*
	public static List<RepaymentPlan.Plan> fixedBasisMortgage(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {
	
		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();
	
		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);
	
		// 计算总共几个月
		BigDecimal totalMonths = BigDecimal.ZERO;
		for (int i = 0; i < amors.size(); i++) {
			totalMonths = totalMonths.add(amors.get(i).getPartMonth());
		}
	
		// 剩余本金
		BigDecimal fixedPrincipal = principal.setScale(2, RoundingMode.HALF_UP);
		// 每月本金
		BigDecimal partPrincipal = fixedPrincipal.divide(totalMonths, 2, RoundingMode.HALF_UP);
	
		for (int i = 0; i < amors.size(); i++) {
	
			Amortization amor = amors.get(i);
	
			// 本月本金
			BigDecimal principal0 = amor.isLastRepayment() ? fixedPrincipal : partPrincipal.multiply(amor.getPartMonth()).setScale(2, RoundingMode.HALF_UP);
			// 本月利息
			BigDecimal interest0 = fixedPrincipal.multiply(new BigDecimal(amor.getIntervalDays())).multiply(rate.dailyRate()).setScale(2, RoundingMode.HALF_UP);
			// 本月还款额
			BigDecimal repayment0 = principal0.add(interest0);
	
			fixedPrincipal = fixedPrincipal.subtract(principal0);
	
			RepaymentPlan.Plan plan = new RepaymentPlan.Plan();
			plan.setIssue(i + 1);
			plan.setDueDate(amor.getDueDate());
			plan.setIntervalDays(amor.getIntervalDays());
			plan.setStartDate(amor.getStartDate());
			plan.setEndDate(amor.getEndDate());
			plan.setPrincipal(principal0);
			plan.setInterest(interest0);
			plan.setRepayment(repayment0);
			plan.appendTo(plans);
		}
	
		return plans;
	}
	*/

	/**
	 * 首期付息按月还本/先息后本
	 * 
	 * @param startDate
	 *            开始日
	 * @param endDate
	 *            结束日
	 * @param principal
	 *            借款本金
	 * @param rate
	 *            收益率
	 * @param repaymentDate
	 *            还款日
	 * @param mdmode
	 *            每月天数模型
	 * @return
	 */
	public static List<RepaymentPlan.Plan> firstInterestFixedBasis(Date startDate, Date endDate, BigDecimal principal, YieldRate rate, int repaymentDate, MonthDaysMode mdmode) {
		List<RepaymentPlan.Plan> plans = new ArrayList<RepaymentPlan.Plan>();
		System.out.println(plans);
		List<Amortization> amors = amortize(startDate, endDate, repaymentDate, mdmode);
		System.out.println(amors);
		throw new NotImplementedException();
	}

	/**
	 * 还款周期
	 * 
	 * @param startDate
	 *            开始日
	 * @param endDate
	 *            截止日(此日不计息)
	 * @param repaymentDate
	 *            还款日
	 * @param mdmode
	 *            每月天数模型
	 * @return 还款周期列表
	 */
	private static List<Amortization> amortize(Date startDate, Date endDate, int repaymentDate, MonthDaysMode mdmode) {

		Calendar c = Calendar.getInstance();
		c.clear();
		c.setTime(startDate);

		Calendar endingDate = Calendar.getInstance();
		endingDate.clear();
		endingDate.setTime(endDate);
		endingDate.add(Calendar.DATE, -1);

		// 每月截止日
		int endIntervalDate = repaymentDate == 1 ? 31 : repaymentDate - 1;

		List<Amortization> amors = new ArrayList<Amortization>();

		// 切割时间
		while (DateUtil.lt(c.getTime(), endDate)) {

			/* 起息日 */
			Date startDate0 = c.getTime();

			int startDate0MonthDays = DateUtil.monthDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH));

			if (c.get(Calendar.DAY_OF_MONTH) > Math.min(endIntervalDate, startDate0MonthDays)) {
				c.add(Calendar.MONTH, 1);
			}
			int endDate0MonthDays = DateUtil.monthDays(c.get(Calendar.YEAR), c.get(Calendar.MONTH));
			c.set(Calendar.DAY_OF_MONTH, Math.min(endIntervalDate, endDate0MonthDays));
			/* 期望止息日 */
			Date endDateE = c.getTime();

			Calendar l = Calendar.getInstance();
			l.clear();
			l.setTime(endDateE);
			l.add(Calendar.MONTH, -1);
			l.set(Calendar.DAY_OF_MONTH, Math.min(endIntervalDate, DateUtil.monthDays(l.get(Calendar.YEAR), l.get(Calendar.MONTH))));
			l.add(Calendar.DATE, 1);
			/* 期望起息日 */
			Date startDateE = l.getTime();

			if (DateUtil.gt(endDateE, endingDate.getTime())) {
				c.setTime(endingDate.getTime());
			}
			/* 止息日 */
			Date endDate0 = c.getTime();

			c.add(Calendar.DATE, 1);
			Date dueDate0 = c.getTime();

			// 是否整月还款
			boolean overRepayment = DateUtil.eq(startDate0, startDateE) && DateUtil.eq(endDate0, endDateE);

			int intervalDays0 = DateUtil.intervalDays(startDate0, endDate0) + 1;
			if (overRepayment) {
				if (mdmode.equals(MonthDaysMode.FIXED_30_DAYS)) {
					intervalDays0 = 30;
				}
			} else {
				if (mdmode.equals(MonthDaysMode.FIXED_30_DAYS) && intervalDays0 > 30) {
					intervalDays0 = 30;
				}
			}

			// 是否是最后一期
			boolean lastRepayment = !DateUtil.lt(c.getTime(), endDate);

			BigDecimal partMonth = BigDecimal.ONE;
			if (!overRepayment) {
				int overDays = 30;
				if (mdmode.equals(MonthDaysMode.NATURAL_DAYS)) {
					overDays = DateUtil.intervalDays(startDateE, endDateE) + 1;
				}
				if (intervalDays0 < overDays) {
					partMonth = new BigDecimal(intervalDays0).divide(new BigDecimal(overDays), 4, RoundingMode.HALF_UP);
				}
			}

			Amortization amor = new Amortization();
			amor.setDueDate(dueDate0);
			amor.setStartDate(startDate0);
			amor.setEndDate(endDate0);
			amor.setIntervalDays(intervalDays0);
			amor.setStartDateE(startDateE);
			amor.setEndDateE(endDateE);
			amor.setOverRepayment(overRepayment);
			amor.setLastRepayment(lastRepayment);
			amor.setPartMonth(partMonth);
			amors.add(amor);
		}

		return amors;
	}

	@Data
	private static class Amortization {
		// 还款日
		private Date dueDate;
		// 起息日
		private Date startDate;
		// 止息日
		private Date endDate;
		// 借款天数
		private int intervalDays;
		// 期望开始日
		private Date startDateE;
		// 期望截止日
		private Date endDateE;
		// 是否覆盖当前整个周期
		private boolean overRepayment;
		// 是否最后一期
		private boolean lastRepayment;
		// 本期占满月比例
		private BigDecimal partMonth;
	}

	@lombok.Data
	@NoArgsConstructor
	public static class Plan {
		// 期数
		private int issue;
		// 还款日
		private Date dueDate;
		// 计息天数
		private int intervalDays;
		// 计息起始日
		private Date startDate;
		// 计息截止日
		private Date endDate;
		// 还款本金
		private BigDecimal principal;
		// 还款利息
		private BigDecimal interest;
		// 总还款额
		private BigDecimal repayment;

		public void appendTo(List<RepaymentPlan.Plan> list) {
			list.add(this);
		}
	}
}
