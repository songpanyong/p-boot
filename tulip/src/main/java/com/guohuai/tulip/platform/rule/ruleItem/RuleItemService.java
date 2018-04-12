package com.guohuai.tulip.platform.rule.ruleItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.tulip.util.Collections3;

@Service
public class RuleItemService {
	
	@Autowired 
	private RuleItemDao ruleItemDao;

	/**
	 * 根据规则Id删除规则项
	 * @param ruleId
	 */
	public void deleteRuleItem(String[] ruleId) {
		this.ruleItemDao.deleteRuleItem(ruleId);
	}
	/**
	 * 创建规则项
	 * @param ruleItem
	 */
	public RuleItemEntity createRuleItem(RuleItemEntity ruleItem) {
		return this.ruleItemDao.save(ruleItem);
	}
	/**
	 * 根据规则ID查询规则项
	 * @param ruleId
	 * @return
	 */
	public List<RuleItemEntity> listRuleItemEntityByRID(String ruleId) {
		List<RuleItemEntity> ruleItemList=this.ruleItemDao.listRuleItemEntityByRID(ruleId);
		return ruleItemList;
	}
	/**
	 * 根据规则ID查询规则项
	 * @param oid
	 * @return
	 */
	public List<RuleItemEntity> listByRuleId(String oid) {
		List<RuleItemEntity> ruleItemList=this.ruleItemDao.listByRuleId(oid);
		return ruleItemList;
	}
	
	/**
	 * 根据规则id获取规则属性
	 * 
	 * @param ruleId
	 * @return
	 */
	public Map<String, Object> findRuleItem(String ruleId){
		Map<String, Object> ruleItem = new HashMap<String, Object>();
		List<RuleItemEntity> list = ruleItemDao.findRuleListByRuleId(ruleId);
		if(!Collections3.isEmpty(list)){
			RuleItemEntity entity = list.get(0);
			String value = entity.getPropId() + entity.getExpression() + entity.getValue();
			ruleItem.put(entity.getPropId(), value);
		}
		
		return ruleItem;
	}
}
