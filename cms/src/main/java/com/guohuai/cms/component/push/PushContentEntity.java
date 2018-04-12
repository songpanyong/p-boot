package com.guohuai.cms.component.push;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PushContentEntity {

	private String pushId;
	
	private PushContEntity pushCont;
}
