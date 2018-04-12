package com.guohuai.operate.api.objs.admin.log;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;


public class AdminLogListObj extends BaseObj {

	/**
	 * 
	 */
	private static final long serialVersionUID = 415025798111265965L;

	private int total;

	private List<AdminLogObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AdminLogObj> getRows() {
		return rows;
	}

	public void setRows(List<AdminLogObj> rows) {
		this.rows = rows;
	}

}
