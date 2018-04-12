package com.guohuai.operate.system.menu2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.operate.admin.AdminInfoResp;
import com.guohuai.operate.admin.AdminService;
import com.guohuai.operate.component.util.StringUtil;
import com.guohuai.operate.system.menu.MenuService;

@Service
public class Menu2Service {

	@Autowired
	private MenuService menuService;
	@Autowired
	private AdminService adminService;

	@Transactional
	public void save(String system, String config, String operator) {
		this.menuService.save(system, config, operator);
	}

	@Transactional
	public JSONArray load(String system) {
		return this.menuService.load(system);
	}

	@Transactional
	public List<Menu2Config> getConfig(String system) {
		JSONArray json = this.menuService.load(system);
		List<Menu2Config> config = new ArrayList<Menu2Config>();
		if (null != json && json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				Menu2Config menu = JSONObject.toJavaObject(json.getJSONObject(i), Menu2Config.class);
				config.add(menu);
			}
		}
		return config;
	}

	@Transactional
	public JSONArray view(String system, boolean develop, String operator) {

		JSONArray views = new JSONArray();

		List<Menu2Config> menus = this.getConfig(system);

		if (null == menus || menus.size() == 0) {
			return views;
		}
		AdminInfoResp adminInfo = this.adminService.info(operator);

		String[] roles = null == adminInfo.getRoles() ? new String[0] : adminInfo.getRoles().toArray(new String[0]);

		for (Menu2Config menu : menus) {
			JSONObject valid = this.validate(menu, roles, develop);
			if (null != valid) {
				views.add(valid);
			}
		}

		return views;
	}

	@Transactional
	private JSONObject validate(Menu2Config config, String[] roles, boolean develop) {

		boolean permit = this.menuService.hasRole(roles, config.getRoles(), develop);
		if (!permit) {
			return null;
		}

		JSONObject json = config.toJson();

		// 按钮处理
		if (null != config.getButtons() && config.getButtons().size() > 0) {
			JSONArray buttons = new JSONArray();
			for (Menu2ButtonConfig button : config.getButtons()) {
				if (develop) {
					buttons.add(button.toJson());
				} else {
					if (Menu2ButtonConfig.ENABLE_YES.equals(button.getEnable()) && this.menuService.hasRole(roles, button.getRoles(), develop)) {
						buttons.add(button.toJson());
					} else {
						button.setAuthorize(false);
						buttons.add(button.toJson());
					}
				}
			}
			if (buttons.size() > 0) {
				json.put("buttons", buttons);
			}
		}

		if (null != config.getChildren() && config.getChildren().size() > 0) {
			JSONArray children = new JSONArray();
			for (Menu2Config child : config.getChildren()) {
				JSONObject childConfig = this.validate(child, roles, develop);
				if (null != childConfig) {
					children.add(childConfig);
				}
			}
			if (children.size() > 0) {
				json.put("children", children);
			} else {
				// 如果没有子菜单, 并且当前菜单是目录, 就不返回该菜单了.
				if (StringUtil.isEmpty(config.getId())) {
					return null;
				}
			}
		}

		return json;
	}

}
