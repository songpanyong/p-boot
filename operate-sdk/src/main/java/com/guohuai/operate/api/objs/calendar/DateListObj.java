package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;
import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class DateListObj extends BaseObj {

	private static final long serialVersionUID = -8096634245086720927L;

	private long begin;
	private long end;
	private List<Date> dates;

	public long getBegin() {
		return begin;
	}

	public void setBegin(long begin) {
		this.begin = begin;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

}
