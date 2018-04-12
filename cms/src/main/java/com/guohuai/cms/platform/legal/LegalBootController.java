package com.guohuai.cms.platform.legal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.cms.platform.element.ElementEntity;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/legal", produces = "application/json;charset=UTF-8")
public class LegalBootController  extends BaseController {

	@Autowired
	private LegalService legalService;
	
	@RequestMapping(name = "法律文件管理-类型列表", value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<PageResp<LegalResp>> list(HttpServletRequest request,
			@And({ @Spec(params = "code", path = "code", spec = Like.class),
					@Spec(params = "name", path = "name", spec = Like.class),
					@Spec(params = "status", path = "status", spec = Equal.class) })  Specification<LegalEntity> spec,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "status"), new Order(Direction.DESC, "createTime")));
		PageResp<LegalResp> enchs = legalService.queryPage(spec, pageable);
		return new ResponseEntity<PageResp<LegalResp>>(enchs, HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-类型详情", value = "detail", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<LegalResp> detail(@RequestParam(required = true) String oid) {
		super.getLoginUser();
		LegalResp resp = legalService.getDetail(oid);
		return new ResponseEntity<LegalResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-类型删除", value = "delete", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> delete(@RequestParam(required = true) String oid) {
		super.getLoginUser();
		legalService.delete(oid);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-类型添加", value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> add(@Valid LegalAddReq form) {
		String operator = super.getLoginUser();
		legalService.add(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-类型修改", value = "update", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> update(@Valid LegalAddReq form) {
		String operator = super.getLoginUser();
		legalService.update(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-类型启用", value = "on", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> on(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		legalService.isDisplay(oid, LegalEntity.LEGAL_STATUS_enabled, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(name = "法律文件管理-类型禁用", value = "off", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> off(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		legalService.isDisplay(oid, LegalEntity.LEGAL_STATUS_disabled, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-可用类型名称", value = "enabledTypes", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Map<String, List<LegalResp>>> enabledTypes() {
		super.getLoginUser();
		Map<String, List<LegalResp>> rep = this.legalService.queryEnabledTypes();
		
		return new ResponseEntity<Map<String, List<LegalResp>>>(rep, HttpStatus.OK);
	}
}
