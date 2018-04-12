package com.guohuai.operate.system.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubMenuButton {

	public static final String ENABLE_YES = "YES";
	public static final String ENABLE_NO = "NO";

	public static final String RENDER_DEFAULT = "submenu";

	private String id;
	private String text;
	private String role;
	private String icon;
	private String render;
	private String enable;
	private SubMenuButtonAction action;

}
