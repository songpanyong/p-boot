package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;

import com.guohuai.operate.api.objs.BaseObj;

public class TradeDateObj extends BaseObj {

	private static final long serialVersionUID = 7960959525720780748L;

	private String oid;
	private String exchangeCD;
	private Date calendarDate;
	private int isOpen;
	private Date prevTradeDate;
	private int isWeekEnd;
	private int isMonthEnd;
	private int isQuarterEnd;
	private int isYearEnd;

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getExchangeCD() {
		return exchangeCD;
	}

	public void setExchangeCD(String exchangeCD) {
		this.exchangeCD = exchangeCD;
	}

	public Date getCalendarDate() {
		return calendarDate;
	}

	public void setCalendarDate(Date calendarDate) {
		this.calendarDate = calendarDate;
	}

	public int getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(int isOpen) {
		this.isOpen = isOpen;
	}

	public Date getPrevTradeDate() {
		return prevTradeDate;
	}

	public void setPrevTradeDate(Date prevTradeDate) {
		this.prevTradeDate = prevTradeDate;
	}

	public int getIsWeekEnd() {
		return isWeekEnd;
	}

	public void setIsWeekEnd(int isWeekEnd) {
		this.isWeekEnd = isWeekEnd;
	}

	public int getIsMonthEnd() {
		return isMonthEnd;
	}

	public void setIsMonthEnd(int isMonthEnd) {
		this.isMonthEnd = isMonthEnd;
	}

	public int getIsQuarterEnd() {
		return isQuarterEnd;
	}

	public void setIsQuarterEnd(int isQuarterEnd) {
		this.isQuarterEnd = isQuarterEnd;
	}

	public int getIsYearEnd() {
		return isYearEnd;
	}

	public void setIsYearEnd(int isYearEnd) {
		this.isYearEnd = isYearEnd;
	}

}
