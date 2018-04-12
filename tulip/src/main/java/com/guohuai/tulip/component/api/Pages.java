package com.guohuai.tulip.component.api;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Pages<T> {
	List<T> rows = new ArrayList<T>();

	public void add(T e) {
		rows.add(e);
	}

	long total;
}