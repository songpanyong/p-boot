package com.guohuai.operate.component.web.view;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ParamResp extends BaseResp {

	private Map<String, Object> params = new HashMap<String, Object>();

}
