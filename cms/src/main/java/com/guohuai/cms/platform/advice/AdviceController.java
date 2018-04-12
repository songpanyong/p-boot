package com.guohuai.cms.platform.advice;

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
import com.guohuai.cms.component.util.DateUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/advice", produces = "application/json")
public class AdviceController extends BaseController{

	@Autowired
	private AdviceService adviceService;
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<AdviceQueryRep>> channelApproveQuery(HttpServletRequest request,
			@And({  @Spec(params = "tabOid", path = "tab.oid", spec = In.class),
					@Spec(params = "userID", path = "userID", spec = Like.class),
					@Spec(params = "content", path = "content", spec = Like.class),
					@Spec(params = "dealStatus", path = "dealStatus", spec = In.class),
					@Spec(params = "reqTimeBegin", path = "createTime", spec = DateAfterInclusive.class, config = DateUtil.fullDatePattern),
					@Spec(params = "reqTimeEnd", path = "createTime", spec = DateBeforeInclusive.class, config = DateUtil.fullDatePattern)}) 
	         		Specification<AdviceEntity> spec,
			@RequestParam int page, 
			@RequestParam int rows) {		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<AdviceQueryRep> rep = this.adviceService.adviceQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<AdviceQueryRep>>(rep, HttpStatus.OK);
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid AdviceAddReq req) {		
		
		return new ResponseEntity<BaseRep>(this.adviceService.addAdvice(req), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/addToTab", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addAdviceOfTab(@RequestParam(required=true) String oid, @RequestParam(required=true) String tabOid) {		
		String operator = super.getLoginUser();
		BaseRep rep = this.adviceService.addAdviceOfTab(oid, tabOid, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/remark", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> remark(@Valid AdviceRemarkReq req) {	
		String operator = super.getLoginUser();
		BaseRep rep = this.adviceService.addAdviceOfRemark(req.getOid(), req.getRemark(), operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
