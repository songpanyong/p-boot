package com.guohuai.tulip.platform.coupon.userCoupon;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.userinvest.UserInvestService;

@RestController
@RequestMapping(value = "/tulip/boot/usercoupon", produces = "application/json")
public class UserCouponController extends BaseController {
	@Autowired
	private UserCouponService userCouponService;
	@Autowired
	UserInvestService userInvestService;
	
	
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<UserCouponRep>> query(HttpServletRequest request,
			UserCouponDetailResp entity,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "useTime")));
		PageResp<UserCouponRep> rep = this.userCouponService.query(entity, pageable);
		return new ResponseEntity<PageResp<UserCouponRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 说明：我的卡券（红包/提现券）
	 * @param userId
	 * @param type
	 * @return
	 * @author ddyin
	 * @time：2017年3月27日 下午3:42:38
	 */
	@RequestMapping(value = "counponListByUidAndType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<UserCouponRep>> counponListByUidAndType(@RequestParam String userId,@RequestParam String type){
		List<UserCouponRep> rep = this.userCouponService.counponListByUidAndType(userId,type);
		return new ResponseEntity<List<UserCouponRep>>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 说明：红包过期
	 * @return
	 * @author ddyin
	 * @time：2017年3月27日 下午3:34:22
	 */
	@RequestMapping(value = "redPacketsExpire", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<UserCouponRep>> counponListExpire(@RequestParam String userId){
		List<UserCouponRep> rep = this.userCouponService.counponListExpire(userId);
		return new ResponseEntity<List<UserCouponRep>>(rep, HttpStatus.OK);
	}
	
	
	
}
