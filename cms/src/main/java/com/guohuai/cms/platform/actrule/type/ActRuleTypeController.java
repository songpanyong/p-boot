package com.guohuai.cms.platform.actrule.type;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.basic.component.ext.web.BaseController;

@RestController
@RequestMapping(value = "/cms/boot/actrule/type", produces = "application/json")
public class ActRuleTypeController extends BaseController {

	@Autowired
	private ActRuleTypeService actRuleTypeService;
	
	/**
	 * 获取协议类型，为<select>使用
	 * @return
	 */
	@RequestMapping(value = "/getSelect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, List<ActRuleTypeSelectRep>>> getSelect() {
		Map<String, List<ActRuleTypeSelectRep>> rep = this.actRuleTypeService.allSelectTypes();
		
		return new ResponseEntity<Map<String, List<ActRuleTypeSelectRep>>>(rep, HttpStatus.OK);
	}
	
}
