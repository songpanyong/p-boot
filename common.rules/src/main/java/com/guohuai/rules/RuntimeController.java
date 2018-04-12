package com.guohuai.rules;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.rules.action.ActionInfo;
import com.guohuai.rules.config.BizRule;
import com.guohuai.rules.config.DroolsContainerHolder;
import com.guohuai.rules.config.RuntimeInfoLister;
import com.guohuai.rules.event.EventInfo;


/**
 * 运行时事件和动作定义信息.
 * 
 * @author xulizhong
 *
 */
@RestController
@RequestMapping(value="/tulip/rules/runtime", produces = "application/json")
public class RuntimeController {

	@Autowired
	RuntimeInfoLister runtimeInfoLister;
	
	@RequestMapping("/events")
	public Set<EventInfo> eventInfoSet() {
		return this.runtimeInfoLister.getEventInfoSet();
	}
	
	@RequestMapping("/actions")
	@ResponseBody
	public Set<ActionInfo> actionInfoSet() {
		return this.runtimeInfoLister.getActionInfoSet();
	}
	
	
	@Autowired
	DroolsContainerHolder containerHoler;
	
	/**
	 * 当前加载的规则集.
	 * @return
	 */
	@RequestMapping("/ruleset")
	public List<BizRule> rulesInfoSet() {
		return this.containerHoler.getCurrentRuleSet();
	}
}
