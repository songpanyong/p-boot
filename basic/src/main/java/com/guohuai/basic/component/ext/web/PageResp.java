package com.guohuai.basic.component.ext.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageResp<T> extends BaseResp {

	protected long total;
	protected List<T> rows = new ArrayList<T>();

	public PageResp(Page<T> page) {
		this(page.getTotalElements(), page.getContent());
	}
}
