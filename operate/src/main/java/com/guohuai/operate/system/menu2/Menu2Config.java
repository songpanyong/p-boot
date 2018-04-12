package com.guohuai.operate.system.menu2;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Menu2Config {

	private String id;
	private String text;
	private String parent;
	private String pageId;
	private String icon;
	private List<String> roles;
	private List<Menu2Config> children;
	private List<Menu2ButtonConfig> buttons;

	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("text", this.text);
		json.put("parent", this.parent);
		json.put("pageId", this.pageId);
		json.put("icon", this.icon);
		return json;
	}

}
