package com.guohuai.tulip.platform.rule.ruleProp;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/tulip/boot/ruleprop", produces = "application/json")
public class RulePropController extends BaseController {
	Logger logger = LoggerFactory.getLogger(RulePropController.class);

	@Autowired
	private RulePropService rulePropService;

	
	/**
	 * 说明：条件查询
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:25:26
	 */
	@RequestMapping(value = "rulepropList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<RulePropRep>> rulepropList(HttpServletRequest request,
			@And({@Spec(params = "isdel", path = "isdel", spec = Equal.class),
				  @Spec(params = "type", path = "type", spec = Equal.class),
				  @Spec(params = "name", path = "name", spec = Like.class) }) Specification<RulePropEntity> spec,
			      @RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "oid")));
		PageResp<RulePropRep> rep = this.rulePropService.rulepropList(spec,pageable);
		return new ResponseEntity<PageResp<RulePropRep>>(rep, HttpStatus.OK);
	}


	
	/**
	 * 说明：保存规则属性
	 * @param qRep
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:26:01
	 */
	@RequestMapping(value = "/saveRuleProp", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> saveRuleProp(@Valid RulePropReq req) {
		BaseResp rep = rulePropService.saveRuleProp(req);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	
	/**根据oid查询规则属性是否存在
	 * 说明：
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月28日 下午5:50:09
	 */
	@RequestMapping(value = "/getRulePropByOid", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<RulePropEntity> getRulePropByOid(String oid) {
		RulePropEntity rulePropEntity=this.rulePropService.findRulePropByOid(oid);
		return new ResponseEntity<RulePropEntity>(rulePropEntity, HttpStatus.OK);
	}
	
	
	/**
	 * 说明：查询所有规则属性
	 * @param request
	 * @param spec
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:26:28
	 */
	@RequestMapping(value = "findAllRuleProp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<RulePropRep>> findAllRuleProp(HttpServletRequest request,
			@And({@Spec(params = "type", path = "type", spec = Equal.class) }) Specification<RulePropEntity> spec) {
		PageResp<RulePropRep> rep = this.rulePropService.findAllRuleProp(spec);
		return new ResponseEntity<PageResp<RulePropRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 说明：删除规则属性
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:27:07
	 */
	@RequestMapping(value = "/activeRuleprop", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> activeRuleprop(String oid,String isdel) {
		BaseResp rep =this.rulePropService.activeRuleprop(oid,isdel);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 说明：获取规则属性详情
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月28日 下午1:47:41
	 */
	@RequestMapping(value = "getRulePropDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<RulePropRep> getRulePropDetail(@RequestParam String oid){
		RulePropRep rep = this.rulePropService.getRulePropDetail(oid);
		return new ResponseEntity<RulePropRep>(rep, HttpStatus.OK);
	}
}
