package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;

import com.guohuai.operate.api.objs.BaseObj;

public class LastWorkDateObj extends BaseObj {

	private static final long serialVersionUID = 3071910733771062481L;

	private Date ts;
	private Date lastwd;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Date getLastwd() {
		return lastwd;
	}

	public void setLastwd(Date lastwd) {
		this.lastwd = lastwd;
	}

}
