package com.guohuai.rules.config;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.template.ObjectDataCompiler;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.guohuai.rules.enums.AttrTypeEnum;
import com.guohuai.rules.event.BaseEvent;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RuleGenerator {
	private static final String RULE_ID = "ruleId";
	private static final String EVENT_TYPE = "eventType";
	private static final String RULE_EXPR = "ruleExpr";
	private static final String RULE_ACTION = "action";
	private static final String ACTION_PARAMS = "actionParams";

	public String applyRuleTemplate(BaseEvent event, RuleDefinition ruleDef, String templateContent) throws Exception {
		InputStream input = new ByteArrayInputStream(templateContent.getBytes());
		return applyRuleTemplate(event, ruleDef, input);
	}

	public String applyRuleTemplate(BaseEvent event, RuleDefinition rule, InputStream input) {
		Map<String, Object> data = new HashMap<String, Object>();
		ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();

		data.put(RULE_ID, rule.getOid());

		List<RuleExpression> ruleSet = rule.getRuleSet();
		if (ruleSet != null) {
			List<String> exprList = new ArrayList<String>();
			StringBuffer exprBuffer = null;
			for (RuleExpression rex : ruleSet) {
				exprBuffer = new StringBuffer();
				exprBuffer.append(rex.getAttr());
				exprBuffer.append(rex.getOp());
				if (AttrTypeEnum.STR.getCode().equals(rex.getType())) {
					exprBuffer.append("\'");
					exprBuffer.append(rex.getValue());
					exprBuffer.append("\'");
				} else {
					exprBuffer.append(rex.getValue());
				}
				rex.setExpr(exprBuffer.toString());
				exprList.add(rex.getExpr());
			}

			String ruleOpt = rule.ruleOpt;
			if (null == ruleOpt) {
				ruleOpt = "&&";
			} else {
				if ("or".equalsIgnoreCase(ruleOpt) || "||".equals(ruleOpt)) {
					ruleOpt = "||";
				} else {
					ruleOpt = "&&";
				}
			}
			String ruleString = Joiner.on(ruleOpt).join(exprList);
			if (ruleString == null) {
				ruleString = "";
			}

			data.put(RULE_EXPR, "(" + ruleString + ")");
		} else {
			data.put(RULE_EXPR, "()");
		}
		data.put(EVENT_TYPE, event.getClass().getName());
		data.put(RULE_ACTION, rule.getAction());
		
		String params = rule.getActionParams();
		if (params == null || params.trim().equals("")) {
			params = "{}";
		}
		data.put(ACTION_PARAMS, params);

		log.debug("datamap : {}", data);

		return objectDataCompiler.compile(Arrays.asList(data), input);
	}

}
