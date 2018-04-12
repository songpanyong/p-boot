package com.guohuai.cms.platform.information;

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
import com.guohuai.cms.platform.information.type.InformationTypeAddReq;
import com.guohuai.cms.platform.information.type.InformationTypeEntity;
import com.guohuai.cms.platform.information.type.InformationTypeSelectRep;
import com.guohuai.cms.platform.information.type.InformationTypeService;

import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
@RestController
@RequestMapping(value = "/cms/boot/information", produces = "application/json")
public class InformationController extends BaseController{
	
	@Autowired
	private InformationService informationService;
	@Autowired
	private InformationTypeService InformationTypeService;
	
	/**
	 * 理财资讯页面数据查询
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "/informations", method = {RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<InformationQueryRep>> informationQuery(HttpServletRequest request,
			@And({  @Spec(params = "channelOid", path = "channel.oid", spec = Like.class),
					@Spec(params = "title", path = "title", spec = Like.class),
					@Spec(params = "status", path = "status", spec =Equal.class),
			     	@Spec(params = "type", path = "type", spec = Equal.class),
				}) 
         		Specification<InformationEntity> spec,
		@RequestParam int page, 
		@RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "publishTime")));				
		PagesRep<InformationQueryRep> rep = this.informationService.informationQuery(spec, pageable);
		return new ResponseEntity<PagesRep<InformationQueryRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 新增资讯信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addInformation(@Valid InformationAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		this.informationService.addInformation(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 获取资讯详情
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "informationInfo", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<InformationQueryRep> getInfo(@RequestParam String oid) {		
		InformationQueryRep rep = this.informationService.getInformation(oid);
		return new ResponseEntity<InformationQueryRep>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 修改资讯
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> editChanInfo(@Valid InformationAddReq req) {		
		BaseRep rep = new BaseRep();
		String operator = super.getLoginUser();
		this.informationService.addInformation(req, operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 删除资讯
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delChanInfo(@RequestParam String oid) {		
		BaseRep rep = this.informationService.delInformation(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 资讯审核
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "review", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> review(@Valid InformatinReviewRep req) {
		String operator = super.getLoginUser();
		BaseRep rep = this.informationService.dealReview(req, operator);		
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);
	}
	/**
	 * 发布资讯
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "publish", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> publish(@RequestParam String oid) {
		String operator = super.getLoginUser();
		BaseRep rep = this.informationService.publishInformation(oid,operator);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 下架资讯
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "informationOff", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> informationOff(@RequestParam String oid) {
		BaseRep rep = this.informationService.informationOff(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 是否推荐首页
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "isHome", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> isHome(@RequestParam String oid) {
		BaseRep rep = this.informationService.isHome(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 资讯类型查询
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "informationTypeList", method ={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<PagesRep<InformationTypeEntity>> informationTypeList() {	
		List<InformationTypeEntity> list=this.InformationTypeService.getInformationTypeList();
		PagesRep<InformationTypeEntity> rep = new PagesRep<InformationTypeEntity>();
		rep.setRows(list);
		return new ResponseEntity<PagesRep<InformationTypeEntity>>(rep, HttpStatus.OK);	
	}
	/**
	 * 新增资讯类型信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value = "addInformationType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> addInformationType(HttpServletRequest request,@Valid InformationTypeAddReq req) {		
		BaseRep rep = new BaseRep();		
		this.InformationTypeService.addInformationType(req);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 删除资讯类型
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "delInformationType", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> delInformationType(@RequestParam String oid) {		
		BaseRep rep = this.InformationTypeService.delInformationType(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 对资讯类型排序   --上移动
	 * @param oid
	 * @return   
	 */
	@RequestMapping(value = "sortInfoTypeUp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> sortInfoTypeUp(@RequestParam String oid) {	
		
		BaseRep rep = this.InformationTypeService.sorInformationType(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 对资讯类型排序   --上移动
	 * @param oid
	 * @return   
	 */
	@RequestMapping(value = "sortInfoTypeDown", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> sortInfoTypeDown(@RequestParam String oid) {	
		
		BaseRep rep = this.InformationTypeService.sorInformationTypeDown(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	
	/**
	 * 资讯类型下拉列表
	 * @param oid
	 * @return
	 */
	@RequestMapping(value = "/infoTypeSelect", method ={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public ResponseEntity<Map<String, List<InformationTypeSelectRep>>> informationTypeListAll() {	
		Map<String, List<InformationTypeSelectRep>> rep = this.InformationTypeService.allSelectInfoTypes();
		return new ResponseEntity<Map<String, List<InformationTypeSelectRep>>>(rep, HttpStatus.OK);	
	}
	
	/**
	 * 对资讯类型排--启用/关闭
	 * @param oid
	 * @return   
	 */
	@RequestMapping(value = "dealInfoTypeStatus", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseRep> informationTypeIsStart(@RequestParam String oid) {	
		
		BaseRep rep = this.InformationTypeService.dealInformationTypeStatus(oid);
		return new ResponseEntity<BaseRep>(rep, HttpStatus.OK);		
	}
	/**
	 * 资讯类型下是否包含有资讯信息
	 * @param oid
	 * @return   
	 */
	@RequestMapping(value = "isHasInfo", method = RequestMethod.POST)
	@ResponseBody
	public int isHasInfo(@RequestParam String name) {	
		return this.informationService.isHasInfo(name);		
	}
	/**
	 * 资讯类型是否有相同
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "infoTypeNameIsSame", method = RequestMethod.POST)
	@ResponseBody
	public int infoTypeNameIsSame(@RequestParam String name) {	
		return this.informationService.infoTypeNameIsSame(name);		
	}

}
