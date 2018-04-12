package com.guohuai.cms.component.push;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class PushContEntity{
	private String pushTitle;
	private String pushContent;
}
