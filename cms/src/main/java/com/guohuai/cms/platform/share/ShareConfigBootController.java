package com.guohuai.cms.platform.share;

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

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/shareconfig", produces = "application/json;charset=UTF-8")
public class ShareConfigBootController  extends BaseController {

	@Autowired
	private ShareConfigService shareConfigService;
	
	@RequestMapping(value = "mng", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<PageResp<ShareConfigQueryRep>> mng(HttpServletRequest request,
			@And({ @Spec(params = "pageCode", path = "pageCode", spec = Equal.class)}) Specification<ShareConfigEntity> spec,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows,
			@RequestParam(required = false, defaultValue = "createTime") String sortField,
			@RequestParam(required = false, defaultValue = "desc") String sort) {
		Direction sortDirection = Direction.DESC;
		if (!"desc".equals(sort)) {
			sortDirection = Direction.ASC;
		}
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(sortDirection, sortField)));
		PageResp<ShareConfigQueryRep> rep = shareConfigService.query(spec, pageable);
		
		return new ResponseEntity<PageResp<ShareConfigQueryRep>>(rep, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> add(@Valid ShareConfigAddReq req) {
		this.getLoginUser();
		shareConfigService.add(req);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "modify", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> modify(@Valid ShareConfigModifyReq req) {
		this.getLoginUser();
		shareConfigService.modify(req);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "delete", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> delete(@RequestParam(required = true) String shareOid) {
		this.getLoginUser();
		shareConfigService.delete(shareOid);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	

}
