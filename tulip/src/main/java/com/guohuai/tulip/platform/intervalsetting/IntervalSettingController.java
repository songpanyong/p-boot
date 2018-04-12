package com.guohuai.tulip.platform.intervalsetting;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
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
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * 基数等级Controller
 */
@RestController
@RequestMapping(value = "/tulip/boot/intervalSetting/", produces = "application/json")
public class IntervalSettingController extends BaseController {

	@Autowired
	private IntervalSettingService intervalSettingService;
	/**
	 * 查询所有基数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "findAllIntervalSetting", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<IntervalSettingRep>> findAll(HttpServletRequest request) {
		return new ResponseEntity<List<IntervalSettingRep>>(intervalSettingService.findAll(), HttpStatus.OK);
	}
	
	/**
	 * 分页查询基数列表
	 * @param request
	 * @param page
	 * @param rows
	 * @param amount
	 * @param spec
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<IntervalSettingRep>> query(HttpServletRequest request,
			@RequestParam int page, @RequestParam int rows,
			@RequestParam BigDecimal amount,
			@And({@Spec(params = "isdel", path = "isdel", spec = Equal.class) })Specification<IntervalSettingEntity> spec,
			@RequestParam(required = false, defaultValue = "createTime") String sort) {
		Pageable pageable = new PageRequest(page - 1, rows);
		if ( null != amount ) {
			Specification<IntervalSettingEntity> stateSpec = new Specification<IntervalSettingEntity>() {
				@Override
				public Predicate toPredicate(Root<IntervalSettingEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					Expression<BigDecimal> exp = root.get("startMoney").as(BigDecimal.class);
					Expression<BigDecimal> exp2 = root.get("endMoney").as(BigDecimal.class);
					return cb.and(cb.le(exp, amount),cb.gt(exp2, amount));
				}
			};
			spec = Specifications.where(spec).and(stateSpec);
		}
		PageResp<IntervalSettingRep> rep = this.intervalSettingService.query(spec, pageable);
		return new ResponseEntity<PageResp<IntervalSettingRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 保存基数设置
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> save(@Valid IntervalSettingReq req) {
		String uid=getLoginUser();
		BaseResp rep=intervalSettingService.saveIntervalSetting(req,uid);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}

	/**
	 * 删除/启用基数设置
	 * @param oid
	 * @param fromStatus
	 * @return
	 */
	@RequestMapping(value = "active", method = RequestMethod.POST)
	@ResponseBody
	public BaseResp active(@RequestParam String oid,@RequestParam String fromStatus) {
		BaseResp rep=intervalSettingService.activeIntervalSetting(oid,fromStatus);
		return rep;
	}

} 
