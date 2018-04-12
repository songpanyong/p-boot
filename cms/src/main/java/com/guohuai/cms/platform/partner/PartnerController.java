package com.guohuai.cms.platform.partner;

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
import com.guohuai.cms.component.util.StringUtil;
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/partner", produces = "application/json")
public class PartnerController extends BaseController{

	@Autowired
	private PartnerService partnerService;
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<PartnerQueryRep>> query(HttpServletRequest request,
		@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
				@Spec(params = "title", path = "title", spec = Like.class),
			    @Spec(params = "approveStatus", path = "approveStatus", spec = In.class),
				@Spec(params = "releaseStatus", path = "releaseStatus", spec = In.class)}) 
         		Specification<PartnerEntity> spec,
 		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<PartnerQueryRep> rep = this.partnerService.query(spec, pageable);
		
		return new ResponseEntity<PagesRep<PartnerQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取排序好的
	 * @param request
	 * @param spec
	 * @return
	 */
	@RequestMapping(value = "/sortQuery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<PartnerQueryRep>> sortQuery(HttpServletRequest request,
		@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
				@Spec(params = "approveStatus", path = "approveStatus", spec = In.class)
		}) 
         		Specification<PartnerEntity> spec) {
		PagesRep<PartnerQueryRep> rep = this.partnerService.sortQuery(spec);
		
		return new ResponseEntity<PagesRep<PartnerQueryRep>>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid PartnerAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.partnerService.add(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> update(@Valid PartnerAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.partnerService.add(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep = new BaseRep();
		this.partnerService.del(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	/**
	 * 设置上下架
	 * @param oids
	 * @param channelOid
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> active(@RequestParam String oids,
			@RequestParam(required = true) String channelOid) {
		String operator = super.getLoginUser();
		BaseRep rep = null;
		if (StringUtil.isEmpty(oids)) {
			rep = this.partnerService.setActive(null, channelOid, operator);
		} else {
			rep = this.partnerService.setActive(oids.split(","), channelOid, operator);
		}
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 审核通过/驳回
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dealapprove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> dealApprove(@Valid PartnerApproveReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.partnerService.dealApprove(req, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
