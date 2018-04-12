package com.guohuai.cms.platform.version;

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
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
@RestController
@RequestMapping(value = "/cms/boot/version", produces = "application/json")
public class VersionController extends BaseController{
	
	@Autowired
	private VersionService versionService;
	
	/**
	 * 版本页面数据查询
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/versionQuery", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<VersionQueryRep>> versionQuery(HttpServletRequest request,
			@And({  @Spec(params = "versionNo", path = "versionNo", spec = Like.class),
					@Spec(params = "status", path = "status", spec =In.class),
			     	@Spec(params = "publishTimeBegin", path = "publishTime", spec = DateAfterInclusive.class),
					@Spec(params = "publishTimeEnd", path = "publishTime", spec = DateBeforeInclusive.class),
					@Spec(params = "system", path = "system", spec =In.class)
				}) 
         		Specification<VersionEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));				
		PagesRep<VersionQueryRep> rep = this.versionService.versionFindAll(spec, pageable);
		return new ResponseEntity<PagesRep<VersionQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 新增--编辑 版本信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/addVersion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addVersion(@Valid VersionAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		rep = this.versionService.addVersion(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 获取版本详情
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "getVersion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<VersionQueryRep> getVersion(@RequestParam String oid) {		
		VersionQueryRep rep = this.versionService.getVersion(oid);
		return new ResponseEntity<VersionQueryRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 修改版本
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "editVersion", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> editVersion(@Valid VersionAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		this.versionService.addVersion(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 版本审核
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "versionReview", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> versionReview(@Valid VersionReviewRep req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.versionService.versionReview(req, operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 版本上/下架
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/versionPubilsh", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> versionPubilsh(@RequestParam String oid) {
		String operator = super.getLoginUser();
		BaseRep rep = this.versionService.versionPubilsh(oid,operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 版本删除
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/versionDelete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> versionDelete(@RequestParam String oid) {
		BaseRep rep = this.versionService.versionDelete(oid);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取增量版本的现有版本号
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getVersionNoByIncrement", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> getVersionNoByIncrement() {
		BaseRep rep = new BaseRep();
		String versionNo = this.versionService.getVersionNoByIncrement();
		rep.setErrorMessage(versionNo);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取版本升级的现有版本号
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "getVersionNoByVersion", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> getVersionNoByVersion(@RequestParam String system) {
		Map<String, Object> map=this.versionService.getVersionNoByVersion(system);
		return map;
	}
	
	/**
	 * 是否有重复版本号
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "isHasSameVersion", method = RequestMethod.POST)
	@ResponseBody
	public int isHasSameVersion(@RequestParam String system, @RequestParam String versionNo,@RequestParam String oid) {
		return this.versionService.isHasSameVersion(system,versionNo,oid);
	}
	
}
