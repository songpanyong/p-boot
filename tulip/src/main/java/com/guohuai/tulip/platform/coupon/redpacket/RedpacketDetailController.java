package com.guohuai.tulip.platform.coupon.redpacket;

import javax.servlet.http.HttpServletRequest;

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
import com.guohuai.basic.component.ext.web.PageResp;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping(value = "/tulip/boot/redpacket", produces = "application/json")
public class RedpacketDetailController extends BaseController {
	
	@Autowired
	private RedpacketDetailService redpacketDetailService;
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<RedpacketDetailRep>> query(HttpServletRequest request,
			@And({ 	@Spec(params = "couponBatchId", path = "couponBatchId", spec = Equal.class)}) Specification<RedpacketDetailEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows);
		PageResp<RedpacketDetailRep> rep = this.redpacketDetailService.query(spec, pageable);
		return new ResponseEntity<PageResp<RedpacketDetailRep>>(rep, HttpStatus.OK);
	}
	
}
