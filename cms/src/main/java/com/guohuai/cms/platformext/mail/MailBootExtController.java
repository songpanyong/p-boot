package com.guohuai.cms.platformext.mail;


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
import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/cms/boot/mailExt", produces = "application/json")
public class MailBootExtController extends BaseController {

	@Autowired
	private MailExtService mailExtService;
	
	/**后台分页查询站内信
	 * @param request
	 * @param mailspec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/queryPage", name="后台分页查询站内信",  method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PageResp<MailBTExtResp>> informationQuery(HttpServletRequest request,
         		@And({
         			@Spec(params = "phone", path = "phone", spec = Like.class),
         			@Spec(params = "mailType", path = "mailType", spec = Equal.class),
         			@Spec(params = "status", path = "status", spec = In.class),
         			@Spec(params = "createTimeBegin", path = "createTime", spec = DateAfterInclusive.class, config = "yyyy-MM-dd"),
    				@Spec(params = "createTimeEnd", path = "createTime", spec = DateBeforeInclusive.class, config = "yyyy-MM-dd") 
				}) 
         		Specification<MailExtEntity> mailspec,
				@RequestParam int page, 
				@RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));	
		PageResp<MailBTExtResp> rep = this.mailExtService.queryBTExtPage4List(mailspec, pageable);
		
		return new ResponseEntity<PageResp<MailBTExtResp>>(rep, HttpStatus.OK);
	}

	/**后台添加/修改站内信
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "/add", name="后台添加/修改站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> add(HttpServletRequest request, @Valid MailBTAddExtReq req){
		
		this.mailExtService.addExtBT(req, this.getLoginUser());
		
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	/**
	 * 审核操作
	 * @param approveResult 申请结果（pass：通过，refused：拒绝）
	 * @param aoid
	 * @return
	 */
	@RequestMapping(value = "/approve", name="后台审核站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> dealApprove(HttpServletRequest request, @RequestParam String approveResult, @RequestParam String aoid, 
			@RequestParam String approveNote){
		this.mailExtService.approveExt(approveResult, aoid, approveNote, this.getLoginUser());
		
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}

	/**后台获取站内信详情
	 * @param mailOid
	 * @return
	 */
	@RequestMapping(value = "/detail", name="后台获取站内信详情",  method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> login(@RequestParam String mailOid) {
		MailBTExtResp resp = this.mailExtService.getBTExtDetail(mailOid);
		return new ResponseEntity<BaseResp>(resp, HttpStatus.OK);
	}
	
	/**后台删除站内信
	 * @param request
	 * @param aoid
	 * @param approveNote
	 * @return
	 */
	@RequestMapping(value = "/delete", name="后台删除站内信",  method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> deleteApprove(HttpServletRequest request, @RequestParam String aoid, @RequestParam String approveNote){
		this.mailExtService.deleteExt(aoid, this.getLoginUser(), approveNote);
		return new ResponseEntity<BaseResp>(new BaseResp(), HttpStatus.OK);
	}
	
	
}
