package com.guohuai.tulip.platform.coupon;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.operate.api.objs.admin.AdminObj;
import com.guohuai.tulip.platform.coupon.coupon1.RedPacketsRep;
import com.guohuai.tulip.platform.coupon.coupon1.RedPacketsReq;
import com.guohuai.tulip.platform.coupon.coupon2.CouponRep;
import com.guohuai.tulip.platform.coupon.coupon2.CouponReq;
import com.guohuai.tulip.platform.coupon.coupon3.RateCouponRep;
import com.guohuai.tulip.platform.coupon.coupon3.RateCouponReq;
import com.guohuai.tulip.platform.coupon.coupon4.TasteCouponRep;
import com.guohuai.tulip.platform.coupon.coupon4.TasteCouponReq;
import com.guohuai.tulip.platform.coupon.coupon5.CashCouponRep;
import com.guohuai.tulip.platform.coupon.coupon5.CashCouponReq;
import com.guohuai.tulip.platform.coupon.coupon6.PointsCouponRep;
import com.guohuai.tulip.platform.coupon.coupon6.PointsCouponReq;
import com.guohuai.tulip.util.DateUtil;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/tulip/boot/coupon", produces = "application/json")
public class CouponController extends BaseController {

	@Autowired
	private CouponService couponService;
	@Autowired
	private AdminSdk adminSdk;
	/**
	 * 分页查询卡券列表
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "query", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<PageResp<CouponQueryRep>> query(HttpServletRequest request,
			@And({ @Spec(params = "name", path = "name", spec = Like.class),
					@Spec(params = "couponAmount", path = "couponAmount", spec = Equal.class),
					@Spec(params = "writeOffTotalAmountStart", path = "writeOffTotalAmount", spec = GreaterThanOrEqual.class),
					@Spec(params = "writeOffTotalAmountEnd", path = "writeOffTotalAmount", spec = LessThanOrEqual.class),
					@Spec(params = "couponAmount", path = "couponAmount", spec = Equal.class),
					@Spec(params = "type", path = "type", spec = Equal.class),
					@Spec(params = "start", path = "createTime", spec = DateAfterInclusive.class, config = DateUtil.datePattern),
					@Spec(params = "finish", path = "createTime", spec = DateBeforeInclusive.class, config = DateUtil.datePattern) }) Specification<CouponEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<CouponQueryRep> rep = this.couponService.query(spec, pageable);
		return new ResponseEntity<PageResp<CouponQueryRep>>(rep, HttpStatus.OK);
	}
	/**
	 * 根据卡券类型获取卡券列表
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "counponListByType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<CouponRep>> counponListByType(@RequestParam String type) {
		PageResp<CouponRep> rep = this.couponService.counponListByType(type);
		return new ResponseEntity<PageResp<CouponRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 查询可发放的卡券
	 * @param type
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "findUsableCoupon", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<PageResp<CouponQueryRep>> findUsableCoupon(@RequestParam String type, @RequestParam int page,
			@RequestParam int rows) {
		Specification<CouponEntity> couponSpec = new Specification<CouponEntity>() {
			@Override
			public Predicate toPredicate(Root<CouponEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate p = null;
				if (type.equals(CouponEntity.COUPON_TYPE_redPackets)) {
					p = cb.and(cb.equal(root.get("isdel").as(String.class), "no"),
							cb.equal(root.get("type").as(String.class), type),
							cb.gt(root.get("remainCount").as(Integer.class), 0));
				} else {
					p = cb.and(cb.equal(root.get("isdel").as(String.class), "no"),
							cb.equal(root.get("type").as(String.class), type),
							cb.gt(root.get("remainCount").as(Integer.class), 0));
				}
				return p;
			}
		};
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<CouponQueryRep> rep = this.couponService.query(couponSpec, pageable);
		return new ResponseEntity<PageResp<CouponQueryRep>>(rep, HttpStatus.OK);
	}

	/**
	 * 保存红包
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/saveRedPackets", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<RedPacketsRep> saveRedPackets(@RequestParam String jsonStr) {
		RedPacketsReq req = (RedPacketsReq) JSONObject.parseObject(jsonStr, RedPacketsReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<RedPacketsRep>(this.couponService.saveRedPackets(req), HttpStatus.OK);
	}

	/**
	 * 获取红包详情
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findRedPackets", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<RedPacketsRep> findRedPackets(@RequestParam String oid) {
		RedPacketsRep rep = this.couponService.findRedPackets(oid);
		return new ResponseEntity<RedPacketsRep>(rep, HttpStatus.OK);
	}

	/**
	 * 保存代金券
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/saveCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<CouponRep> saveCoupon(@RequestParam String jsonStr) {
		CouponReq req = (CouponReq) JSONObject.parseObject(jsonStr, CouponReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<CouponRep>(this.couponService.saveCoupon(req), HttpStatus.OK);
	}

	/**
	 * 查询代金券
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<CouponRep> findCoupon(@RequestParam String oid) {
		CouponRep rep = this.couponService.findCoupon(oid);
		return new ResponseEntity<CouponRep>(rep, HttpStatus.OK);
	}

	/**
	 * 保存加息券
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/saveRateCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<RateCouponRep> saveRateCoupon(@RequestParam String jsonStr) {
		RateCouponReq req = (RateCouponReq) JSONObject.parseObject(jsonStr, RateCouponReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<RateCouponRep>(this.couponService.saveRateCoupon(req), HttpStatus.OK);
	}

	/**
	 * 查询加息券
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findRateCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<RateCouponRep> findRateCoupon(@RequestParam String oid) {
		RateCouponRep rep = this.couponService.findRateCoupon(oid);
		return new ResponseEntity<RateCouponRep>(rep, HttpStatus.OK);
	}

	/**
	 * 保存体验金
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/saveTasteCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<TasteCouponRep> saveTasteCoupon(@RequestParam String jsonStr) {
		TasteCouponReq req = (TasteCouponReq) JSONObject.parseObject(jsonStr, TasteCouponReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<TasteCouponRep>(this.couponService.saveTasteCoupon(req), HttpStatus.OK);
	}

	/**
	 * 查询体验金
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findTasteCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<TasteCouponRep> findTasteCoupon(@RequestParam String oid) {
		TasteCouponRep rep = this.couponService.findTasteCoupon(oid);
		return new ResponseEntity<TasteCouponRep>(rep, HttpStatus.OK);
	}

	/**
	 * 保存提现券
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/saveCashCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<CashCouponRep> saveCashCoupon(@RequestParam String jsonStr) {
		CashCouponReq req = (CashCouponReq) JSONObject.parseObject(jsonStr, CashCouponReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<CashCouponRep>(this.couponService.saveCashCoupon(req), HttpStatus.OK);
	}

	/**
	 * 查询提现券
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findCashCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<CashCouponRep> findCashCoupon(@RequestParam String oid) {
		CashCouponRep rep = this.couponService.findCashCoupon(oid);
		return new ResponseEntity<CashCouponRep>(rep, HttpStatus.OK);
	}

	/**
	 * 保存积分券
	 * 
	 * @param jsonStr
	 * @return
	 */
	@RequestMapping(value = "/savePointsCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<PointsCouponRep> savePointsCoupon(@RequestParam String jsonStr) {
		PointsCouponReq req = (PointsCouponReq) JSONObject.parseObject(jsonStr, PointsCouponReq.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		req.setUpdateUser(operateName);
		req.setCreateUser(operateName);
		return new ResponseEntity<PointsCouponRep>(this.couponService.savePointsCoupon(req), HttpStatus.OK);
	}

	/**
	 * 查询积分券
	 * 
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/findPointsCoupon", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<PointsCouponRep> findPointsCoupon(@RequestParam String oid) {
		PointsCouponRep rep = this.couponService.findPointsCoupon(oid);
		return new ResponseEntity<PointsCouponRep>(rep, HttpStatus.OK);
	}
	/**
	 * 删除卡券
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/deleteCoupon", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> deleteCoupon(@RequestParam String oid) {
		BaseResp rep = this.couponService.deleteCoupon(oid);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 删除卡券
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/checkOnlyName", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> checkOnlyName(@RequestParam(value = "oid", required = false) String oid, @RequestParam String name, @RequestParam String type) {
		BaseResp rep = this.couponService.checkOnlyCouponName(oid, name, type);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
}
