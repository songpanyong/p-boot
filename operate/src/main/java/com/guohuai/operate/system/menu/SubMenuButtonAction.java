package com.guohuai.operate.system.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubMenuButtonAction {

	public static final String EVENTTYPE_OPEN = "open";
	public static final String EVENTTYPE_FUNC = "func";

	private String eventtype;
	private String event;
	private String oid;

}
