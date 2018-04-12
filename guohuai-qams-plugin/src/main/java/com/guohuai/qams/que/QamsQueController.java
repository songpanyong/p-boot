package com.guohuai.qams.que;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.Response;

/**
 * 调查问卷
 * @author zzq
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/que", produces = "application/json;charset=utf-8")
public class QamsQueController extends BaseController {

	@Autowired
	private QamsQueService qamQueService;
	
	/**
	 * 调查问卷新增
	 * @param form
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/addQamsQue", name = "新增调差问卷列表", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<Response> addQamsQue(QamsQueForm form) {
		qamQueService.addQamsQue(form);
		Response r = new Response();
		r.with("result", "SUCCESSED!");
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	/**
	 * 调查问卷--列表
	 * 
	 * @param portfolioOid
	 * @return
	 */
	@RequestMapping(value = "/QamsQueList", name = " 调查问卷列表", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsQueListResp> QamsQueList( HttpServletRequest request, 
			@RequestParam(required = false, defaultValue = "1") int page, 
			@RequestParam(required = false, defaultValue = "10") int rows,
			@RequestParam(required = false, defaultValue = "status") String sortField, 
			@RequestParam(required = false, defaultValue = "desc") String sort) {

		Direction sortDirection = Direction.DESC;
		if (!"desc".equals(sort)) {
			sortDirection = Direction.ASC;
		}
		Specification<QamsQue> spec = new Specification<QamsQue>() {
			@Override
			public Predicate toPredicate(Root<QamsQue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				return cb.notEqual(root.get("status"), QamsQue.ILLIQUIDASSET_STATE_INVALID);
			}
		};
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(sortDirection, sortField)));
		Page<QamsQue> entitys = qamQueService.QamsQueList(spec, pageable);
		QamsQueListResp resps = new QamsQueListResp(entitys);
		return new ResponseEntity<QamsQueListResp>(resps, HttpStatus.OK);
	}
	/**
	 * 修改问卷展示的详情
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(name = "修改问卷展示", value = "/detail", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<QamsQueResp> detail(@RequestParam(required = true) String oid) {
		QamsQue entity = qamQueService.findByOid(oid);
		QamsQueResp resp = new QamsQueResp(entity);
		return new ResponseEntity<QamsQueResp>(resp, HttpStatus.OK);
	}
	/**
	 * 修改问卷
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(name = "修改问卷", value = "/edit", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> edit(QamsQueForm form) {
		 qamQueService.updateQamsQue(form);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	/**
	 * 问卷作废
	 * 
	 * @param investment
	 * @return
	 */
	@RequestMapping(name = "问卷作废", value = "/invalid", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<BaseResp> invalid(@RequestParam (required = true) String sid) {
		qamQueService.invalid(sid);
		BaseResp r = new BaseResp();
		return new ResponseEntity<BaseResp>(r, HttpStatus.OK);
	}
	/**
	 * wangzhixin add 
	 * @return
	 */
	@RequestMapping(value = "/options/group", name = "分组选项", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<List<QamsQueOptGroupResp>> optgroup() {

		List<QamsQueOptGroupResp> result = this.qamQueService.optgroup();

		return new ResponseEntity<List<QamsQueOptGroupResp>>(result, HttpStatus.OK);

	}
	
	/**
	 * 获取所有未删除的问卷的名称列表，包含id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllNameList", name = "问卷 - 获取所有未删除的问卷json列表，包含id和name", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<Response> getAllNameList() {
		List<JSONObject> jsonList = this.qamQueService.getAllNameList();
		Response r = new Response();
		r.with("rows", jsonList);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	
	/**
	 * 获取所有问卷的名称列表，包含id
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllName", name = "问卷 - 获取所有问卷json列表，包含id和name", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<Response> getAllName() {
		List<JSONObject> jsonList = this.qamQueService.getAllName();
		Response r = new Response();
		r.with("rows", jsonList);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	
	/**
	 * 根据sid查询问卷调查结果
	 * @param sid 问卷id
	 * @return
	 */
	@RequestMapping(value = "/qamsQueResult", name = "问卷调查结果", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsQueResultResp> qamsQueResult(@RequestParam String sid) {
		QamsQueResultResp resp = this.qamQueService.getResultByQueId(sid);
		return new ResponseEntity<QamsQueResultResp>(resp, HttpStatus.OK);
	}

	/**
	 * wangzhixin add
	 * @param sid
	 * @return
	 */
	@RequestMapping(value = "/getQueById", name = "问卷调查结果", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody QamsQueResp getQueById(@RequestParam(required = true) String sid){
		QamsQue resp=this.qamQueService.getQueById(sid);
		QamsQueResp resultResp = new QamsQueResp(resp);
		return resultResp;
		
	}
	/**
	 * 根据sid查询问卷调查结果
	 * @param sid 问卷id
	 * @return
	 */
	@RequestMapping(value = "/qamsQueResultByid", name = "问卷调查结果", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsQueResultResp> qamsQueResultbyId(@RequestParam String sid) {
		QamsQueResultResp resp = this.qamQueService.getQueResultById(sid);
		return new ResponseEntity<QamsQueResultResp>(resp, HttpStatus.OK);
	}
	
	/**
	 * 根据sid查询问卷详情
	 * @param sid 问卷id
	 * @return
	 */
	@RequestMapping(value = "/qamsQueRespByid", name = "问卷调查结果", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsQueResp> qamsQueRespByid(@RequestParam String sid) {
		QamsQueResp resp = this.qamQueService.getQueRespById(sid);
		return new ResponseEntity<QamsQueResp>(resp, HttpStatus.OK);
	}
}
