package com.guohuai.tulip.platform.sceneprop;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@RequestMapping(value = "/tulip/boot/sceneprop", produces = "application/json")
public class ScenePropController extends BaseController {
	Logger logger = LoggerFactory.getLogger(ScenePropController.class);

	@Autowired
	private ScenePropService scenePropService;
	
	/**
	 * 说明：查询所有场景属性
	 * @param request
	 * @param spec
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:28:59
	 */
	@RequestMapping(value = "findAllSceneProp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<ScenePropRep>> findAllSceneProp(HttpServletRequest request,
			@And({@Spec(params = "type", path = "type", spec = Equal.class),
				@Spec(params = "isdel", path = "isdel", spec = Equal.class) }) Specification<ScenePropEntity> spec) {
		PageResp<ScenePropRep> rep = this.scenePropService.findAllSceneProp(spec);
		return new ResponseEntity<PageResp<ScenePropRep>>(rep, HttpStatus.OK);
	}
	
	
	

	
	/**
	 * 说明：条件查询
	 * @param request
	 * @param spec
	 * @param page
	 * @param rows
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:29:24
	 */
	@RequestMapping(value = "scenePropList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<ScenePropRep>> scenePropList(HttpServletRequest request,
			@And({@Spec(params = "name", path = "name", spec = Like.class),
				@Spec(params = "type", path = "type", spec = Equal.class),
				@Spec(params = "isdel", path = "isdel", spec = Equal.class)}) Specification<ScenePropEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows);
		PageResp<ScenePropRep> rep = this.scenePropService.scenePropList(spec,pageable);
		
		return new ResponseEntity<PageResp<ScenePropRep>>(rep, HttpStatus.OK);
	}
	
	/**
	 * 说明：保存场景属性
	 * @param qRep
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:29:38
	 */
	@RequestMapping(value = "/saveSceneProp", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> saveSceneProp(@Valid ScenePropReq qReq) {
		BaseResp rep = this.scenePropService.saveSceneProp(qReq);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	/**
	 * 说明：删除场景属性
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月23日 下午1:30:18
	 */
	@RequestMapping(value = "/activeSceneProp", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public ResponseEntity<BaseResp> activeSceneProp(String oid,String isdel) {
		BaseResp rep = this.scenePropService.activeSceneProp(oid,isdel);
		return new ResponseEntity<BaseResp>(rep, HttpStatus.OK);
	}
	
	
	/**
	 * 说明：获取场景属性详情
	 * @param oid
	 * @return
	 * @author ddyin
	 * @time：2017年2月28日 下午1:47:41
	 */
	@RequestMapping(value = "getScenePropDetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ScenePropRep> getScenePropDetail(@RequestParam String oid){
		ScenePropRep rep = this.scenePropService.getScenePropDetail(oid);
		return new ResponseEntity<ScenePropRep>(rep, HttpStatus.OK);
	}
	
	
}
