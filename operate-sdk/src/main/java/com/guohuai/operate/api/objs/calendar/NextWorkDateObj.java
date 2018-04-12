package com.guohuai.operate.api.objs.calendar;

import java.sql.Date;

import com.guohuai.operate.api.objs.BaseObj;

public class NextWorkDateObj extends BaseObj {

	private static final long serialVersionUID = 2421428735304875527L;

	private Date ts;
	private Date nextwd;

	public Date getTs() {
		return ts;
	}

	public void setTs(Date ts) {
		this.ts = ts;
	}

	public Date getNextwd() {
		return nextwd;
	}

	public void setNextwd(Date nextwd) {
		this.nextwd = nextwd;
	}

}
