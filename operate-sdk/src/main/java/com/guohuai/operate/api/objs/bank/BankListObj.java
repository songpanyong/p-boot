package com.guohuai.operate.api.objs.bank;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class BankListObj extends BaseObj {

	private static final long serialVersionUID = 8203257856793597479L;

	private int total;
	private List<BankObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<BankObj> getRows() {
		return rows;
	}

	public void setRows(List<BankObj> rows) {
		this.rows = rows;
	}

}
