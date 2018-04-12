package com.guohuai.cms.platform.protocol.type;

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
@RequestMapping(value = "/cms/boot/protocol/type", produces = "application/json")
public class ProtocolTypeController extends BaseController {

	@Autowired
	private ProtocolTypeService protocolTypeService;
	
	/**
	 * 获取协议类型，为<select>使用
	 * @return
	 */
	@RequestMapping(value = "/getSelect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Map<String, List<ProtocolTypeSelectRep>>> getSelect() {
		Map<String, List<ProtocolTypeSelectRep>> rep = this.protocolTypeService.allSelectTypes();
		
		return new ResponseEntity<Map<String, List<ProtocolTypeSelectRep>>>(rep, HttpStatus.OK);
	}
	
}
