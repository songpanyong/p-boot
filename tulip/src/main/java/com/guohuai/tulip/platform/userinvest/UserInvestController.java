package com.guohuai.tulip.platform.userinvest;

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
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.operate.api.objs.admin.AdminObj;
import com.guohuai.tulip.platform.signin.SignInEntity;
import com.guohuai.tulip.platform.signin.SignInRep;
import com.guohuai.tulip.platform.signin.SignInService;
import com.guohuai.tulip.util.DateUtil;

import lombok.extern.slf4j.Slf4j;
import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * 用户投资controller
 */
@RestController
@RequestMapping(value = "/tulip/boot/userinvest/", produces = "application/json")
@Slf4j
public class UserInvestController extends BaseController{

	@Autowired
	private UserInvestService userInvestService;
	@Autowired
	private SignInService signService;
	@Autowired
	private AdminSdk adminSdk;
	
	@RequestMapping(value = "userList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<UserInvestRep>> userList(HttpServletRequest request,
				@RequestParam int page, @RequestParam int rows,
				@And({@Spec(params = "phone", path = "phone", spec = Like.class),
						@Spec(params = "type", path = "type", spec = Equal.class),
						@Spec(params = "begin", path = "registerTime", spec = DateAfterInclusive.class, config = DateUtil.datetimePattern),
						@Spec(params = "end", path = "registerTime", spec = DateBeforeInclusive.class, config = DateUtil.datetimePattern) }) Specification<UserInvestEntity> spec,
				@RequestParam(required = false, defaultValue = "registerTime") String sort,
				@RequestParam(required = false, defaultValue = "desc") String order) {
		Direction sortDirection = Direction.DESC;
		if (!"desc".equals(order)) {
			sortDirection = Direction.ASC;
		}
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(sortDirection, sort)));
		PageResp<UserInvestRep> rep = this.userInvestService.userList(spec, pageable);
		return new ResponseEntity<PageResp<UserInvestRep>>(rep, HttpStatus.OK);
	}


	@RequestMapping(value = "findUserDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserInvestRep> findUserDetail(@RequestParam String oid) {
		UserInvestRep rep = this.userInvestService.findUserDetail(oid);
		return new ResponseEntity<UserInvestRep>(rep, HttpStatus.OK);
	}

	/**
	 * 说明：推广人升级为渠道
	 */
	@RequestMapping(value = "refereeToChannel", method = {RequestMethod.GET, RequestMethod.POST})
	@ResponseBody
	public ResponseEntity<BaseResp> refereeToChannel(@Valid UserInvestReq req) {
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setOperateName(operateName);
		return new ResponseEntity<BaseResp>(userInvestService.refereeToChannel(req), HttpStatus.OK);
	}


	/**
	 * 说明：渠道降为推广人
	 *
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年3月28日 下午7:29:45
	 */
	@RequestMapping(value = "channelToReferee", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> channelToReferee(@RequestParam String oid) {
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		BaseResp rep = new BaseResp();
		userInvestService.channelToReferee(oid,operateName);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}


	/**
	 * 说明：获取返佣详情
	 *
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年3月29日 下午3:19:43
	 */
	@RequestMapping(value = "commissionDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<UserInvestRep>> getCommissionDetail(@RequestParam String oid) {
		PageResp<UserInvestRep> rep = this.userInvestService.getCommissionDetail(oid);
		return new ResponseEntity<PageResp<UserInvestRep>>(rep, HttpStatus.OK);
	}


	/**
	 * 说明：获取返佣详情
	 *
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年3月29日 下午3:19:43
	 */
	@RequestMapping(value = "commissionDetailPage", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<UserInvestRep>> getCommissionDetailPage(@RequestParam String oid,
			@RequestParam int page, @RequestParam int rows 
			) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<UserInvestRep> rep = this.userInvestService.getCommissionDetailPage(oid,pageable);
		return new ResponseEntity<PageResp<UserInvestRep>>(rep, HttpStatus.OK);
	}
	@RequestMapping(value = "signList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<SignInRep>> signList(HttpServletRequest request,
			@RequestParam int page, @RequestParam int rows,
			@And({@Spec(params = "userId", path = "userId", spec = Equal.class),
					@Spec(params = "start", path = "signDate", spec = DateAfterInclusive.class, config = DateUtil.datePattern),
					@Spec(params = "finish", path = "signDate", spec = DateBeforeInclusive.class, config = DateUtil.datePattern)}) Specification<SignInEntity> spec){
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "signInTime")));
		PageResp<SignInRep> rep = this.signService.signList(spec,pageable);
		return new ResponseEntity<PageResp<SignInRep>>(rep, HttpStatus.OK);
	}
}
