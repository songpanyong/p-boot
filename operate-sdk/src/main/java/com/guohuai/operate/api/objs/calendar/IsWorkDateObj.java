package com.guohuai.operate.api.objs.calendar;

import java.sql.Timestamp;

import com.guohuai.operate.api.objs.BaseObj;

public class IsWorkDateObj extends BaseObj {

	private static final long serialVersionUID = 8215878519507613481L;

	private Timestamp ts;
	private boolean wd;

	public Timestamp getTs() {
		return ts;
	}

	public void setTs(Timestamp ts) {
		this.ts = ts;
	}

	public boolean isWd() {
		return wd;
	}

	public void setWd(boolean wd) {
		this.wd = wd;
	}

}
