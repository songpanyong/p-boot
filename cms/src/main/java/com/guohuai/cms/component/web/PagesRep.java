package com.guohuai.cms.component.web;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PagesRep<T> extends BaseRep {

	List<T> rows = new ArrayList<T>();

	public void add(T e) {
		rows.add(e);
	}

	long total;
	
	long pages;
}
