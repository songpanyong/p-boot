package com.guohuai.tulip.component.api;

import java.util.ArrayList;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ListPages<T> {
	List<T> list = new ArrayList<T>();

	public void add(T e) {
		list.add(e);
	}

	long total;
}