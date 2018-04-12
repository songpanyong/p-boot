package com.guohuai.cms.component.web.view;

import java.util.ArrayList;
import java.util.List;

public abstract class Packer<T> {

	public abstract Response doPack(T t);

	public List<Response> doPack(List<T> ts) {
		List<Response> rs = new ArrayList<Response>();
		if (null != ts && ts.size() > 0) {
			for (T t : ts) {
				rs.add(this.doPack(t));
			}
		}
		return rs;
	}

}
