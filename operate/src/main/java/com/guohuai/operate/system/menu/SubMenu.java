package com.guohuai.operate.system.menu;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubMenu {

	private String id;
	private String text;
	private String role;
	private SubMenuAction action;
	private List<SubMenuButton> button;

}
