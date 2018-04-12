package com.guohuai.rules.config;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Results;
import org.kie.api.io.KieResources;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guohuai.rules.action.SpringEventAction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DroolsContainerHolder {
	String kiePath = "src/main/resources/";

	private KieContainer kieContainer;

	@Autowired
	SpringEventAction ruleAction;

	private List<BizRule> currentRuleSet;

	/**
	 * 初始化加载.
	 * 
	 * @return
	 */
	public KieContainer current() {
		if (this.kieContainer == null) {
			return this.reload();
		}
		return this.kieContainer;
	}
	
	/**
	 * 开启新的KieSession, 注意都要使用这个方法开启, 会自动注入模板定义的全局变量.
	 * @return 新的KieSession
	 */
	public KieSession newSession() {
		KieSession session = this.current().newKieSession();
		session.setGlobal("ruleAction", this.ruleAction);
		return session;
	}
	
	public int fire(Object event) {
		KieSession session = this.newSession();
		session.insert(event);
		int matches = session.fireAllRules();
		session.dispose();
		return matches;
	}
	
	/**
	 * 重新加载规则列表.
	 * 
	 * @param ruleList 规则列表
	 * @return 重新加载的KieContainer
	 */
	public KieContainer reload(List<BizRule> ruleList) {
		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();

		// 加载demo用来验证
//		KieResources resources = kieServices.getResources();
//		String demoRule = "rules/marketing/demo.drl";
//		Resource demoResource = resources.newClassPathResource(demoRule);
//		kfs.write(kiePath + demoRule, demoResource);

		if (ruleList != null) {
			this.currentRuleSet = ruleList;
			for (BizRule rule : ruleList) {
				String path = this.kiePath +"rules/marketing/"+ rule.getRuleId() + ".drl";
				log.info("loading rule: path = {}, content = {}", path, rule.getContent());
				kfs.write(path, rule.getContent());
			}
		}

		kieServices.newKieBuilder(kfs).buildAll();

		this.setKieContainer(kieServices.newKieContainer(kieServices
				.getRepository().getDefaultReleaseId()));

		return this.kieContainer;
		
	}

	public KieContainer reload() {
		// load rules from database
		List<BizRule> ruleList = null;
		return this.reload(ruleList);
	}

	public Results verify() {
		if (this.kieContainer != null) {
			return this.kieContainer.verify();
		}
		
		return null;
	}

	public KieContainer getKieContainer() {
		return kieContainer;
	}

	public void setKieContainer(KieContainer kieContainer) {
		this.kieContainer = kieContainer;
	}

	public List<BizRule> getCurrentRuleSet() {
		return currentRuleSet;
	}

	public void setCurrentRuleSet(List<BizRule> currentRuleSet) {
		this.currentRuleSet = currentRuleSet;
	}
}
