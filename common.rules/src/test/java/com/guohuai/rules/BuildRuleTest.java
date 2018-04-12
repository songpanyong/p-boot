//package com.guohuai.rules;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.alibaba.fastjson.JSON;
//import com.guohuai.rules.config.BizRule;
//import com.guohuai.rules.config.DroolsContainerHolder;
//import com.guohuai.rules.config.RuleDefinition;
//import com.guohuai.rules.config.RuleExpression;
//import com.guohuai.rules.config.RuleGenerator;
//import com.guohuai.rules.dao.RuleTestDao;
//import com.guohuai.rules.entity.RuleEntity;
//import com.guohuai.rules.event.BaseEvent;
//import com.guohuai.rules.event.RegEvent;
//
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class BuildRuleTest {
//
//	RuleGenerator ruleGenerator = new RuleGenerator();
//
//	@Autowired
//	DroolsContainerHolder containerHolder;
//
//	@Autowired
//	ApplicationEventPublisher eventPublisher;
//	
//	@Autowired
//	ApplicationContext applicationContext;
//	
//	@Autowired
//	RuleTestDao ruleDao;
//
////	@Test
////	public void buildRule() {
////		List<RuleEntity> ruleEntities = ruleDao.findAll();
////		List<BizRule> list =new ArrayList<>();
////		for (RuleEntity ruleEntity : ruleEntities) {
////			InputStream input = this.getClass().getResourceAsStream("/rules.drt");
////			List<RuleExpression> ruleSet = JSON.parseArray(ruleEntity.getExprs(), RuleExpression.class);
////			System.out.println(ruleSet);
////			
////			RuleDefinition ruleDef = RuleDefinition.builder().oid(UUID.randomUUID().toString()).ruleSet(ruleSet)
////					.ruleOpt(ruleEntity.getJoinMethod()).actionParams(ruleEntity.getActionParam().replaceAll("\"", "\'"))
////					.action(ruleEntity.getActionClassName()).build();
////			
////			BaseEvent event=(BaseEvent) applicationContext.getBean(ruleEntity.getEventName());
////			
////			String ruleContent = ruleGenerator.applyRuleTemplate(event, ruleDef, input);
////			System.out.println(ruleContent);
////			
////			BizRule bizRule = BizRule.builder().ruleId(ruleEntity.getOid()).ruleType("demoCoupon").content(ruleContent).build();
////			list.add(bizRule);
////		}
////		this.containerHolder.reload(list);
////		
////		
////		RegEvent event = RegEvent.builder().userOid("u0001").channelNo("APP").regTime(new Date()).build();
////		event.setOid(UUID.randomUUID().toString());
////
////		this.eventPublisher.publishEvent(event);
////
////	}
//
//}
