package com.guohuai.tulip.platform.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.exception.GHException;

@Service
public class RuleService {
	
	@Autowired
	private RuleDao ruleDao;
	
	/**
	 * 根据活动ID查询规则
	 * @param eventId
	 * @return
	 */
	public List<RuleEntity> findRuleByEventId(String eventId) {
		List<RuleEntity> ruleList= this.ruleDao.findRuleByEventId(eventId);
		return ruleList;
	}
	/**
	 * 根据卡券批次查询规则
	 * @param couponBatch
	 * @return
	 */
	public List<RuleEntity> listByCid(String couponBatch) {
		List<RuleEntity> ruleList= this.ruleDao.listByCid(couponBatch);
		return ruleList;
	}
	
	/**
	 * 根据卡券批次查询 领用规则
	 * 
	 * @param couponBatch
	 * @param eventType
	 * @return
	 */
	public List<RuleEntity> findRuleListByCid(String couponBatch, String eventType) {
		List<RuleEntity> ruleList= this.ruleDao.findRuleListByCid(couponBatch, RuleEntity.RULE_TYPE_GET, eventType);
		return ruleList;
	}
	
	/**
	 * 根据规则Oid查询规则
	 * @param ruleId
	 * @return
	 */
	public RuleEntity findRuleByOid(String ruleId) {
		return this.ruleDao.findOne(ruleId);
	}
	/**
	 * 创建规则
	 * @param ruleEntity
	 * @return
	 */
	public RuleEntity createRule(RuleEntity ruleEntity) {
		return this.ruleDao.save(ruleEntity);
	}
	/**
	 * 删除规则集合
	 * @param ruleIds
	 */
	public void deleteRule(String[] ruleIds) {
		int num = this.ruleDao.deleteRule(ruleIds);
		if(num < 1){
			throw new GHException("删除规则异常!");
		}
	}

	/**
	 * 根据活动类型查询规则oids
	 * @param eventType
	 * @return
	 */
	public List<RuleEntity> findRuleIdsByEventType(String eventType) {
		return this.ruleDao.findRuleListByEventType(eventType);
	}
	
	public List<RuleEntity> findRuleListByEventType(String eventType) {
		return this.ruleDao.findRuleListByEventType(eventType);
	}
}
