package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;

import com.guohuai.operate.api.objs.BaseObj;

public class LastTradeDateObj extends BaseObj {

	private static final long serialVersionUID = 2959048966591887403L;

	private Date ts;
	private Date lasttd;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Date getLasttd() {
		return lasttd;
	}

	public void setLasttd(Date lasttd) {
		this.lasttd = lasttd;
	}

}
