package com.guohuai.operate.api.objs.calendar;

import java.util.List;

import com.guohuai.operate.api.objs.BaseObj;

public class TradeDateListObj extends BaseObj {

	private static final long serialVersionUID = -8759143685380510912L;

	private int total;
	private List<TradeDateObj> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<TradeDateObj> getRows() {
		return rows;
	}

	public void setRows(List<TradeDateObj> rows) {
		this.rows = rows;
	}

}
