package com.guohuai.operate.api.objs.corporate;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class CorporateListObj extends BaseObj {

	private static final long serialVersionUID = 8335088879941675248L;

	private int total;
	private List<CorporateObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<CorporateObj> getRows() {
		return rows;
	}

	public void setRows(List<CorporateObj> rows) {
		this.rows = rows;
	}

}
