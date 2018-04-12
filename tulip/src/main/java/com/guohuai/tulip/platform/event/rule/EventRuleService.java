package com.guohuai.tulip.platform.event.rule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.exception.GHException;

@Service
public class EventRuleService {

	@Autowired
	private EventRuleDao eventRuleDao;

	/**
	 * 新增事件规则
	 * @param eventAddReq
	 * @return
	 */
	public EventRuleEntity createEventRule(EventRuleEntity eventAddReq) {
		return this.eventRuleDao.save(eventAddReq);
	}
	/**
	 * 删除活动规则
	 * @param oid
	 */
	public void deleteEventRule(String oid) {
		int num = this.eventRuleDao.deleteEventRule(oid);
		if(num < 1 ){
			throw new GHException("删除活动规则异常!");
		}
	}
	/**
	 * 根据活动ID查询活动规则
	 * @param eid
	 * @return
	 */
	public List<EventRuleEntity> listEventRuleEntityByEID(String eid) {
		List<EventRuleEntity> eventRuleList=this.eventRuleDao.listEventRuleEntityByEID(eid);
		return eventRuleList;
	}

}
