package com.guohuai.operate.api.objs.admin;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class AdminListObj extends BaseObj {

	private static final long serialVersionUID = 7882077935059187025L;

	private int total;

	private List<AdminObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AdminObj> getRows() {
		return rows;
	}

	public void setRows(List<AdminObj> rows) {
		this.rows = rows;
	}

}
