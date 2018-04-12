package com.guohuai.cms.platform.bankcard;

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

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/bankCard", produces = "application/json")
public class BankCardBootController extends BaseController{

	@Autowired
	private BankCardService bankCardService;
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<BankCardQueryRep>> query(HttpServletRequest request,
		@And({
				@Spec(params = "bankName", path = "bankName", spec = Like.class),
			    @Spec(params = "status", path = "status", spec = In.class)}) 
         		Specification<BankCardEntity> spec,
 		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<BankCardQueryRep> rep = this.bankCardService.query(spec, pageable);
		
		return new ResponseEntity<PagesRep<BankCardQueryRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid BankCardAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bankCardService.add(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> update(@Valid BankCardAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bankCardService.add(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep = new BaseRep();
		this.bankCardService.del(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	/**
	 * 审核 通过/驳回
	 */
	@RequestMapping(value = "/dealapprove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> dealApprove(@Valid BankCardApproveReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bankCardService.deal(req, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
