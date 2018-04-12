package com.guohuai.cms.platform.element;

import java.util.List;

import org.springframework.data.domain.Page;

import com.guohuai.basic.component.ext.web.PageResp;

public class ElementListResp extends PageResp<ElementEntity> {
	public ElementListResp() {
		super();
	}

	public ElementListResp(Page<ElementEntity> liquidAsset) {
		this(liquidAsset.getContent(), liquidAsset.getTotalElements());
	}

	public ElementListResp(List<ElementEntity> liquidAsset) {
		this(liquidAsset, liquidAsset.size());
	}

	public ElementListResp(List<ElementEntity> liquidAsset, long total) {
		this();
		super.setTotal(total);
		super.setRows(liquidAsset);
	}
}