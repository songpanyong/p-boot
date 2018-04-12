package com.guohuai.cms.platform.element;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class ElementResp extends BaseResp{

	public ElementResp(ElementEntity entity){
		super();
		this.data = entity;
	}
	
	private ElementEntity data;
}
