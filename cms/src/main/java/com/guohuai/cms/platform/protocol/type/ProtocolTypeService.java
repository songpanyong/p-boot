package com.guohuai.cms.platform.protocol.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.platform.protocol.type.ProtocolTypeSelectRep.ProtocolTypeSelectRepBuilder;

@Service
@Transactional
public class ProtocolTypeService {

	@Autowired
	private ProtocolTypeDao protocolTypeDao;
	
	public ProtocolTypeEntity findById(String id) {
		ProtocolTypeEntity protocolType = this.protocolTypeDao.findOne(id);
		if (null == protocolType) {
			// 此协议类型不存在(CODE:90002)
			throw MoneyException.getException(90002);
		}
		return protocolType;
	}
	
	/**
	 * 获取协议类型下拉列表
	 * @return
	 */
	public Map<String, List<ProtocolTypeSelectRep>> allSelectTypes() {
		Map<String, List<ProtocolTypeSelectRep>> map = new HashMap<String, List<ProtocolTypeSelectRep>>();
		List<ProtocolTypeSelectRep> selects = new ArrayList<ProtocolTypeSelectRep>();
		
		List<ProtocolTypeEntity> types = this.protocolTypeDao.findAll();
		
		for (ProtocolTypeEntity en : types) {
			ProtocolTypeSelectRep rep = new ProtocolTypeSelectRepBuilder().id(en.getId())
					.text(en.getName())
					.build();
			selects.add(rep);
		}
		map.put("protocolTypes", selects);
		return map;
	}
}
