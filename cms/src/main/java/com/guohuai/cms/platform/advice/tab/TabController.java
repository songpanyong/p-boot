package com.guohuai.cms.platform.advice.tab;

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
import com.guohuai.cms.component.web.BaseRep;
import com.guohuai.cms.component.web.PagesRep;

import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/advice/tab", produces = "application/json")
public class TabController extends BaseController{

	@Autowired
	private TabService tabService;
	
	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PagesRep<TabQueryRep>> query(HttpServletRequest request,
		@And({  @Spec(params = "delStatus", path = "delStatus", spec = In.class)}) 
         		Specification<TabEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));		
		PagesRep<TabQueryRep> rep = this.tabService.tabQuery(spec, pageable);
		
		return new ResponseEntity<PagesRep<TabQueryRep>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> add(@Valid TabAddReq req) {		
		String operator = super.getLoginUser();
		BaseRep rep = this.tabService.addTab(req, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 删除标签
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delete(@RequestParam String oid) {		
		String operator = super.getLoginUser();
		BaseRep rep = this.tabService.delTab(oid, operator);
		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getSelect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, List<TabSelectRep>>> getAllTabs() {
		Map<String, List<TabSelectRep>> rep = this.tabService.allSelectTabs();
		
		return new ResponseEntity<Map<String, List<TabSelectRep>>>(rep, HttpStatus.OK);
	}
}
