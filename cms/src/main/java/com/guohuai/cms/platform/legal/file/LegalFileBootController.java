package com.guohuai.cms.platform.legal.file;

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
import com.guohuai.cms.platform.legal.LegalAddReq;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/legalfile", produces = "application/json;charset=UTF-8")
public class LegalFileBootController  extends BaseController {

	@Autowired
	private LegalFileService legalFileService;
	
	@RequestMapping(name = "法律文件管理-文件列表", value = "list", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<PageResp<LegalFileResp>> list(HttpServletRequest request,
			@And({ @Spec(params = "name", path = "name", spec = Like.class),
					@Spec(params = "type", path = "type.oid", spec = Equal.class),
					@Spec(params = "status", path = "status", spec = Equal.class) })  Specification<LegalFileEntity> spec,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "status"), new Order(Direction.DESC, "createTime")));
		PageResp<LegalFileResp> enchs = legalFileService.queryPage(spec, pageable);
		return new ResponseEntity<PageResp<LegalFileResp>>(enchs, HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-文件详情", value = "detail", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<LegalFileResp> detail(@RequestParam(required = true) String oid) {
		super.getLoginUser();
		LegalFileEntity entity = legalFileService.findByOid(oid);
		LegalFileResp resp = new LegalFileResp(entity);
		return new ResponseEntity<LegalFileResp>(resp, HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-文件删除", value = "delete", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> delete(@RequestParam(required = true) String oid) {
		super.getLoginUser();
		legalFileService.delete(oid);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-文件添加", value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> add(@Valid LegalFileAddReq form) {
		String operator = super.getLoginUser();
		legalFileService.add(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-文件修改", value = "update", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> update(@Valid LegalFileUpdateReq form) {
		String operator = super.getLoginUser();
		legalFileService.update(form, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-文件启用", value = "on", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> on(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		legalFileService.isDisplay(oid, LegalFileEntity.LEGAL_STATUS_enabled, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	@RequestMapping(name = "法律文件管理-文件禁用", value = "off", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> off(@RequestParam(required = true) String oid) {
		String operator = super.getLoginUser();
		legalFileService.isDisplay(oid, LegalFileEntity.LEGAL_STATUS_disabled, operator);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	@RequestMapping(name = "法律文件管理-可用类型文件", value = "enabledFiles", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Map<String, List<LegalFileResp>>> enabledFiles(@RequestParam(required = true) String typeOid) {
		super.getLoginUser();
		Map<String, List<LegalFileResp>> rep = this.legalFileService.queryEnabledFiles(typeOid);
		
		return new ResponseEntity<Map<String, List<LegalFileResp>>>(rep, HttpStatus.OK);
	}
}
