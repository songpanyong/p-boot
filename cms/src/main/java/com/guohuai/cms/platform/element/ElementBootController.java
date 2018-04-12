package com.guohuai.cms.platform.element;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/element", produces = "application/json;charset=UTF-8")
public class ElementBootController  extends BaseController {

	@Autowired
	private ElementService elementService;
	
	@RequestMapping(name = "元素管理-元素列表", value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<ElementListResp> list(HttpServletRequest request,
			@And({ @Spec(params = "code", path = "code", spec = Like.class),
					@Spec(params = "type", path = "type", spec = Equal.class),
					@Spec(params = "isDisplay", path = "isDisplay", spec = Equal.class),
					@Spec(params = "name", path = "name", spec = Like.class) }) Specification<ElementEntity> spec,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows,
			@RequestParam(required = false, defaultValue = "updateTime") String sortField,
			@RequestParam(required = false, defaultValue = "desc") String sort) {
		Direction sortDirection = Direction.DESC;
		if (!"desc".equals(sort)) {
			sortDirection = Direction.ASC;
		}
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(sortDirection, sortField)));
		Page<ElementEntity> enchs = elementService.queryPage(spec, pageable);
		ElementListResp pageResp = new ElementListResp(enchs);
		return new ResponseEntity<ElementListResp>(pageResp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素详情", value = "detail", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<ElementResp> detail(@RequestParam(required = true) String oid) {
		ElementEntity entity = elementService.findByOid(oid);
		ElementResp resp = new ElementResp(entity);
		return new ResponseEntity<ElementResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素删除", value = "delete", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> delete(@RequestParam(required = true) String oid) {
		elementService.delete(oid);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素添加", value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> add(@Valid ElementForm form) {
		String operator = super.getLoginUser();
		elementService.add(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素修改", value = "update", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> update(@Valid ElementForm form) {
		String operator = super.getLoginUser();
		elementService.update(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素显示", value = "on", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> on(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		elementService.isDisplay(oid, ElementEntity.ELEMENT_ISDISPLAY_YES, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(name = "元素管理-元素关闭", value = "off", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> off(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		elementService.isDisplay(oid, ElementEntity.ELEMENT_ISDISPLAY_NO, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素显示", value = "checkCode", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> checkCode(@RequestParam(required = true) String code) {
		BaseResp resp = new BaseResp();
		elementService.checkCode(code);
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "元素管理-元素详情", value = "find", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<ElementResp> find(@RequestParam(required = true) String code) {
		ElementEntity entity = elementService.findByCode(code);
		ElementResp resp = new ElementResp(entity);
		return new ResponseEntity<ElementResp>(resp, HttpStatus.OK);
	}
}
