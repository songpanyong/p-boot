package com.guohuai.cms.platform.actrule.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.cms.component.exception.MoneyException;
import com.guohuai.cms.platform.actrule.type.ActRuleTypeSelectRep.ActRuleTypeSelectRepBuilder;

@Service
@Transactional
public class ActRuleTypeService {

	@Autowired
	private ActRuleTypeDao actRuleTypeDao;
	
	public ActRuleTypeEntity findById(String id) {
		ActRuleTypeEntity protocolType = this.actRuleTypeDao.findOne(id);
		if (null == protocolType) {
			// 此活动规则类型不存在(CODE:11002)
			throw MoneyException.getException(11002);
		}
		return protocolType;
	}
	
	/**
	 * 获取活动规则类型下拉列表
	 * @return
	 */
	public Map<String, List<ActRuleTypeSelectRep>> allSelectTypes() {
		Map<String, List<ActRuleTypeSelectRep>> map = new HashMap<String, List<ActRuleTypeSelectRep>>();
		List<ActRuleTypeSelectRep> selects = new ArrayList<ActRuleTypeSelectRep>();
		
		List<ActRuleTypeEntity> types = this.actRuleTypeDao.findAll();
		
		for (ActRuleTypeEntity en : types) {
			ActRuleTypeSelectRep rep = new ActRuleTypeSelectRepBuilder().id(en.getId())
					.text(en.getName())
					.build();
			selects.add(rep);
		}
		map.put("actRuleTypes", selects);
		return map;
	}
}
