package com.guohuai.tulip.platform.event;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.operate.api.objs.admin.AdminObj;
import com.guohuai.tulip.util.DateUtil;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@SuppressWarnings("deprecation")
@RestController
@RequestMapping(value = "/tulip/boot/event", produces = "application/json")
public class EventController extends BaseController {
	Logger logger = LoggerFactory.getLogger(EventController.class);

	@Autowired
	private EventService eventService;
	@Autowired
	private AdminSdk adminSdk;
	/**
	 * 分页查询活动列表
	 * @param request 请求
	 * @param spec 传参
	 * @param page 页码
	 * @param rows 行数
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<EventQueryRep>> query(HttpServletRequest request,
			@And({ @Spec(params = "title", path = "title", spec = Like.class),
					@Spec(params = "status", path = "status", spec = Equal.class),
					@Spec(params = "isdel",  path = "isdel",spec = Equal.class),
					@Spec(params = "active", path = "active", spec = Equal.class),
					@Spec(params = "type", path = "type", spec =Equal.class),
					@Spec(params = "begin", path = "createTime", spec = DateAfterInclusive.class, config = DateUtil.datePattern),
					@Spec(params = "end", path = "createTime", spec = DateBeforeInclusive.class, config = DateUtil.datePattern) }) Specification<EventEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<EventQueryRep> rep = this.eventService.query(spec, pageable);
		return new ResponseEntity<PageResp<EventQueryRep>>(rep, HttpStatus.OK);
	}

	/**
	 * 保存活动
	 * @param rsrJSON 请求数据
	 * @return
	 */
	@RequestMapping(value = "/saveEvent", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> saveEvent(@RequestParam String rsrJSON) {
		EventAddPojo rsr=(EventAddPojo) JSONObject.parseObject(rsrJSON, EventAddPojo.class);
		AdminObj admin = this.adminSdk.getAdmin(super.getLoginUser());
		String operateName = admin == null ? "" : admin.getName();
		BaseResp rep =this.eventService.saveEvent(rsr,operateName);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 批量上下架
	 * @param type
	 * @param oids
	 * @return
	 */
	@RequestMapping(value = "/batchActive", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> batchActive(String type,@RequestParam String oids) {
		BaseResp rep = new BaseResp();
		if (StringUtil.isEmpty(oids)) {
			rep = this.eventService.batchEventActive(type,null);
		} else {
			rep = this.eventService.batchEventActive(type,oids.split(","));
		}
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	/**
	 * 活动上下架
	 * @param type
	 * @param oids
	 * @return
	 */
	@RequestMapping(value = "/active", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> active(String active,@RequestParam String oid) {
		BaseResp rep = new BaseResp();
		if (!StringUtil.isEmpty(oid)) {
			rep = this.eventService.eventActive(active,oid);
		}
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	/**
	 * 查询上架的活动
	 * @return
	 */
	@RequestMapping(value = "/eventPassList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<EventQueryRep>> eventPassList() {
		PageResp<EventQueryRep> rep = this.eventService.getPassDataList();
		return new ResponseEntity<PageResp<EventQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 查询上架的自定义活动
	 * @return
	 */
	@RequestMapping(value = "/eventPassCustomList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<EventQueryRep>> eventPassCustomList() {
		super.getLoginUser();
		PageResp<EventQueryRep> rep = this.eventService.getPassCustomList();
		return new ResponseEntity<PageResp<EventQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 审核活动
	 * @param oid
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> checkEvent(@RequestParam String oid, @RequestParam String status) {
		BaseResp rep = this.eventService.checkEvent(oid, status);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 删除活动
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> deleteEvent(@RequestParam String oid) {
		BaseResp rep = this.eventService.deleteEvent(oid);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 查询活动视图详情数据
	 * @return
	 */
	@RequestMapping(value = "/eventViewDetail", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<EventDetailRep> eventViewDetail() {
		EventDetailRep rep=this.eventService.eventViewDetail();
		return new ResponseEntity<EventDetailRep>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取活动信息（修改）
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "getEventInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<EventAddPojo> getEventInfo(@RequestParam String oid){
		EventAddPojo rep = this.eventService.getEventInfo(oid);
		return new ResponseEntity<EventAddPojo>(rep, HttpStatus.OK);
	}
	
	/**
	 * 获取活动信息（详情）
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "getEventDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getEventDetail(@RequestParam String oid){
		Map<String,Object> rep = this.eventService.getEventDetail(oid);
		return new ResponseEntity<Map<String,Object>>(rep, HttpStatus.OK);
	}
	
	@RequestMapping(value = "findEventCouponSoon", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<EventResp> findEventCouponSoon(@RequestParam String userId, @RequestParam String type, @RequestParam String singleAmount){
		EventResp rep = new EventResp();
		this.eventService.checkMyCouponNow(userId, type);//.findEventSoon(userId, type, singleInvestAmount1);
		return new ResponseEntity<EventResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 判断活动标题是否重复
	 * 
	 * @param title
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "checkOnlyTitle", method = {RequestMethod.POST, RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<BaseResp> checkOnlyTitle(@RequestParam String title, @RequestParam(value = "oid", required = false) String oid){
		BaseResp resp = this.eventService.checkOnlyEventTitle(title, oid);
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
}
