package com.guohuai.operate.system.menu;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.operate.component.web.view.BaseResp;

@RestController
@RequestMapping(value = "/operate/system/menu", produces = "application/json;charset=utf-8")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<BaseResp> save(@RequestParam String system, @RequestBody String config) {
		String operator = super.getLoginUser();
		this.menuService.save(system, config, operator);
		System.out.println(config);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(value = "/load", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<JSONArray> load(@RequestParam String system) {
		super.getLoginUser();
		JSONArray config = this.menuService.load(system);
		return new ResponseEntity<JSONArray>(config, HttpStatus.OK);
	}

	@RequestMapping(value = "/config", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<List<MenuConfig>> config(@RequestParam String system) {
		super.getLoginUser();
		List<MenuConfig> config = this.menuService.getConfig(system);
		return new ResponseEntity<List<MenuConfig>>(config, HttpStatus.OK);
	}

	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody ResponseEntity<JSONArray> view(@RequestParam String system, @RequestParam(defaultValue = "false") boolean develop) {
		JSONArray json = this.menuService.view(system, develop, super.getLoginUser());
		return new ResponseEntity<JSONArray>(json, HttpStatus.OK);
	}
}
