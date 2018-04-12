package com.guohuai.operate.api.objs.admin.amp;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class AdminAmpListObj extends BaseObj {

	private static final long serialVersionUID = 7882077935059187025L;

	private int total;

	private List<AdminAmpObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AdminAmpObj> getRows() {
		return rows;
	}

	public void setRows(List<AdminAmpObj> rows) {
		this.rows = rows;
	}

}
