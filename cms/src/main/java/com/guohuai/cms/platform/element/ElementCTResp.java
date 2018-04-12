package com.guohuai.cms.platform.element;

import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class ElementCTResp extends BaseResp {

	private List<ElementCTBaseResp> datas;
}
