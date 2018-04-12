package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;

import com.guohuai.operate.api.objs.BaseObj;

public class NextTradeDateObj extends BaseObj {

	private static final long serialVersionUID = 2959048966591887403L;

	private Date ts;
	private Date nexttd;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Date getNexttd() {
		return nexttd;
	}

	public void setNexttd(Date nexttd) {
		this.nexttd = nexttd;
	}

}
