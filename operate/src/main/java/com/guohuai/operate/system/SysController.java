package com.guohuai.operate.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.operate.component.web.Response;

@RestController
@RequestMapping(value = "/operate/admin/ctrl/system", produces = "application/json;charset=utf-8")
public class SysController extends BaseController {

	@Autowired
	private SysService sysService;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Response> list() {
		super.getLoginUser();
		List<Sys> list = this.sysService.list();
		Response r = new Response().with("total", list.size()).with("rows", list);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}

}
