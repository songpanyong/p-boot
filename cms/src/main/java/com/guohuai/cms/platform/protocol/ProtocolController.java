package com.guohuai.cms.platform.protocol;

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

import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/protocol", produces = "application/json")
public class ProtocolController extends BaseController {

	@Autowired
	private ProtocolService protocolService;
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<ProtocolQueryRep>> imagesQuery(HttpServletRequest request,
			@And({  @Spec(params = "typeId", path = "protocolType.id", spec = Like.class)
			})
         		Specification<ProtocolEntity> spec,
         	@RequestParam int page, 
			@RequestParam int rows) {		
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<ProtocolQueryRep> rep = this.protocolService.protocolQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<ProtocolQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 新增/修改协议
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid ProtocolAddReq req) {		
		BaseRep rep = this.protocolService.addProtocol(req);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {
		BaseRep rep =  this.protocolService.delProtocol(oid);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
}
