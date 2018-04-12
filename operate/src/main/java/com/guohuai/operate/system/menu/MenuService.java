package com.guohuai.operate.system.menu;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.guohuai.operate.admin.AdminInfoResp;
import com.guohuai.operate.admin.AdminService;
import com.guohuai.operate.component.exception.OPException;
import com.guohuai.operate.component.util.StringUtil;

@Service
public class MenuService {

	@Autowired
	private MenuDao menuDao;
	@Autowired
	private AdminService adminService;

	@Transactional
	public void save(String system, String config, String operator) {
		JSONArray json = null;

		try {
			json = JSONArray.parseArray(config);
		} catch (Throwable t) {
			throw OPException.getException("无法解析的配置参数.");
		}

		Timestamp now = new Timestamp(System.currentTimeMillis());
		Menu menu = this.menuDao.findByOidAndSystem(Menu.OID_MENU + "_" + system, system);
		if (null == menu) {
			menu = Menu.builder().oid(Menu.OID_MENU + "_" + system).system(system).createTime(now).build();
		}
		menu.setConfig(json.toString());
		menu.setOperator(operator);
		menu.setUpdateTime(now);
		this.menuDao.save(menu);
	}

	@Transactional
	public JSONArray load(String system) {
		Menu menu = this.menuDao.findByOidAndSystem(Menu.OID_MENU + "_" + system, system);
		if (null == menu) {
			throw OPException.getException("未初始化的菜单设置.");
		}
		JSONArray menus = null;

		try {
			menus = JSONArray.parseArray(menu.getConfig());
		} catch (Throwable t) {
			throw OPException.getException("无法解析的配置参数.");
		}

		return menus;
	}

	@Transactional
	public List<MenuConfig> getConfig(String system) {
		JSONArray json = this.load(system);
		List<MenuConfig> config = new ArrayList<MenuConfig>();
		if (null != json && json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				MenuConfig menu = JSONObject.toJavaObject(json.getJSONObject(i), MenuConfig.class);
				config.add(menu);
			}
		}
		return config;
	}

	public boolean hasRole(String[] role, String roles, boolean develop) {
		if (develop) {
			return true;
		}
		if (null == role || role.length == 0) {
			return false;
		}
		if (StringUtil.isEmpty(roles)) {
			return false;
		}
		String[] trole = roles.split("[,;]");
		for (String r : role) {
			for (String t : trole) {
				if (r.trim().equals(t.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasRole(String[] role, List<String> roles, boolean develop) {
		if (develop) {
			return true;
		}
		if (null == role || role.length == 0) {
			return false;
		}
		if (null == roles || roles.size() == 0) {
			return false;
		}
		String[] trole = roles.toArray(new String[0]);
		for (String r : role) {
			for (String t : trole) {
				if (r.trim().equals(t.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	public JSONArray view(String system, boolean develop, String operator) {
		List<MenuConfig> menus = this.getConfig(system);

		AdminInfoResp adminInfo = this.adminService.info(operator);

		String[] roles = null == adminInfo.getRoles() ? new String[0] : adminInfo.getRoles().toArray(new String[0]);

		JSONArray views = new JSONArray();

		if (null != menus && menus.size() > 0) {
			for (MenuConfig menu : menus) {
				if (null == menu.getChildren() || menu.getChildren().size() == 0) {
					// 没有子菜单配置
					continue;
				}

				JSONObject view = new JSONObject();
				view.put("id", menu.getId());
				view.put("text", menu.getText());
				JSONArray subViews = new JSONArray();
				for (SubMenu subMenu : menu.getChildren()) {
					JSONObject subView = new JSONObject();

					// 子菜单权限过滤
					if (!this.hasRole(roles, subMenu.getRole(), develop)) {
						continue;
					}

					subView.put("id", subMenu.getId());
					subView.put("text", subMenu.getText());
					if (null != subMenu.getAction()) {
						subView.put("action", subMenu.getAction());
					}

					// 过滤菜单按钮
					JSONArray btns = new JSONArray();
					if (null != subMenu.getButton() && subMenu.getButton().size() > 0) {

						for (SubMenuButton button : subMenu.getButton()) {
							// 按钮没有设置动作
							if (null == button.getAction()) {
								continue;
							}
							// 按钮被禁用了
							if (!StringUtil.isEmpty(button.getEnable()) && button.getEnable().equals(SubMenuButton.ENABLE_NO)) {
								continue;
							}
							// 按钮权限过滤
							if (!this.hasRole(roles, button.getRole(), develop)) {
								continue;
							}
							JSONObject btn = new JSONObject();
							btn.put("id", button.getId());
							btn.put("text", button.getText());
							btn.put("render", StringUtil.isEmpty(button.getRender()) ? SubMenuButton.RENDER_DEFAULT : button.getRender());
							btn.put("action", button.getAction());
							btns.add(btn);
						}
					}

					if (btns.size() > 0) {
						subView.put("button", btns);
					}
					// 过滤流程按钮
					subViews.add(subView);

				}
				if (subViews.size() == 0) {
					// 没有有权限的菜单
					continue;
				}
				view.put("children", subViews);
				views.add(view);
			}
		}

		return views;

	}

}
