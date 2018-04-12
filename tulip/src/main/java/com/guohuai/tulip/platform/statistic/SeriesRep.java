package com.guohuai.tulip.platform.statistic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SeriesRep implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8034068189235464832L;
	String name;
	String type;
	String stack;
	List<Integer> data = new ArrayList<Integer>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStack() {
		return stack;
	}
	public void setStack(String stack) {
		this.stack = stack;
	}
	public List<Integer> getData() {
		return data;
	}
	public void setData(List<Integer> data) {
		this.data = data;
	}
	
	
}
