package com.guohuai.tulip.platform.commissionorder;

import java.util.List;

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
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.operate.api.objs.admin.AdminObj;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/tulip/boot/commissionOrder", produces = "application/json")
public class CommissionOrderController extends BaseController{

	@Autowired
	private CommissionOrderService commissionOrderService;
	
	@Autowired
	private AdminSdk adminSdk;
	
	@RequestMapping(value = "query", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<PageResp<CommissionOrderRep>> query(HttpServletRequest request,
			@And({ @Spec(params = "phone", path = "phone", spec = Equal.class),
					@Spec(params = "orderStatus", path = "orderStatus", spec = Equal.class)}) Specification<CommissionOrderEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<CommissionOrderRep> rep = this.commissionOrderService.query(spec, pageable);
		return new ResponseEntity<PageResp<CommissionOrderRep>>(rep, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "commissionListByOrderStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<CommissionOrderRep>> commissionListByOrderStatus(@RequestParam String orderStatus){
		PageResp<CommissionOrderRep> rep = this.commissionOrderService.commissionListByOrderStatus(orderStatus);
		return new ResponseEntity<PageResp<CommissionOrderRep>>(rep, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "commissionOrderDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CommissionOrderRep> commissionOrderDetail(@RequestParam String oid){
		CommissionOrderRep rep = this.commissionOrderService.commissionOrderDetail(oid);
		return new ResponseEntity<CommissionOrderRep>(rep, HttpStatus.OK);
	}
	
	
	
	/**
	 * 说明：审核通过
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年4月5日 下午4:19:28
	 */
	@RequestMapping(value = "/commissionOrderPass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> commissionOrderPass(@RequestParam String oid){
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? oid : admin.getName();
		BaseResp rep = this.commissionOrderService.commissionOrderPass(oid,operateName);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 一键全发（审核）:一键审核通过列表中所有佣金订单
	 * @param oids
	 * @return
	 */
	@RequestMapping(value = "/commissionOrderBatchPass", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> commissionOrderBatchPass(@RequestParam String oids){
		BaseResp rep = new BaseResp();
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		if(StringUtil.isEmpty(operateName)){
			rep.setErrorCode(-1);
			rep.setErrorMessage("登录用户异常");
			return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(oids)){
			rep.setErrorCode(-1);
			rep.setErrorMessage("没有需要审核的佣金订单");
			return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
		}
		
		this.commissionOrderService.commissionOrderBatchPass(oids.split(","), operateName);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 说明：审核驳回/拒绝
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年4月5日 下午4:19:28
	 */
	@RequestMapping(value = "/commissionOrderRefused", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> commissionOrderRefused(@RequestParam String rsrJSON){
		
		CommissionOrderEntity qReq = (CommissionOrderEntity) JSONObject.parseObject(rsrJSON, CommissionOrderEntity.class);
		String oid = qReq.getOid();
		String rejectAdvice = qReq.getRejectAdvice();
		
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? oid : admin.getName();
		this.commissionOrderService.commissionOrderRefused(oid,operateName,rejectAdvice);
		BaseResp rep = new BaseResp();
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 说明：获取返佣详情
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年4月10日 下午3:19:43
	 */
	@RequestMapping(value = "/getCommissionDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<CommissionOrderRep>> getcommissionDetail(@RequestParam String oid) {
		List<CommissionOrderRep> rep = this.commissionOrderService.getcommissionDetail(oid);
		return new ResponseEntity<List<CommissionOrderRep>>(rep, HttpStatus.OK);
	}
}
