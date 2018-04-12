package com.guohuai.operate.api.objs.calendar;

import java.sql.Timestamp;

import com.guohuai.operate.api.objs.BaseObj;

public class IsTradeDateObj extends BaseObj {

	private static final long serialVersionUID = 7294259013178201615L;

	private Timestamp ts;
	private boolean td;

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public boolean isTd() {
		return td;
	}

	public void setTd(boolean td) {
		this.td = td;
	}

}
