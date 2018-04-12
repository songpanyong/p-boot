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
public class MenuConfig {

	private String id;
	private String text;
	private List<SubMenu> children;

}
