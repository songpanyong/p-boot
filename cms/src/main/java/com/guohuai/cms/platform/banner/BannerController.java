package com.guohuai.cms.platform.banner;

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
@RequestMapping(value = "/cms/boot/banner", produces = "application/json")
public class BannerController extends BaseController{

	@Autowired
	private BannerService bannerService;
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<BannerQueryRep>> query(HttpServletRequest request,
		@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
				@Spec(params = "title", path = "title", spec = Like.class),
			    @Spec(params = "approveStatus", path = "approveStatus", spec = In.class),
				@Spec(params = "releaseStatus", path = "releaseStatus", spec = In.class)}) 
         		Specification<BannerEntity> spec,
 		@RequestParam int page, 
		@RequestParam int rows) {
		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<BannerQueryRep> rep = this.bannerService.bannerQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<BannerQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取排序好的Banner
	 * @param request
	 * @param spec
	 * @return
	 */
	@RequestMapping(value = "/sortQuery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<BannerQueryRep>> sortQuery(HttpServletRequest request,
		@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
				@Spec(params = "approveStatus", path = "approveStatus", spec = In.class)
		}) 
         		Specification<BannerEntity> spec) {
		PagesRep<BannerQueryRep> rep = this.bannerService.sortQuery(spec);
		
		return new ResponseEntity<PagesRep<BannerQueryRep>>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid BannerAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bannerService.addBanner(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> update(@Valid BannerAddReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bannerService.addBanner(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep = new BaseRep();
		this.bannerService.delBanner(oid);
		
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
			rep = this.bannerService.setActive(null, channelOid, operator);
		} else {
			rep = this.bannerService.setActive(oids.split(","), channelOid, operator);
		}
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 审核Banner 通过/驳回
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/dealapprove", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> dealApprove(@Valid BannerApproveReq req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.bannerService.dealApprove(req, operator);
				
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
