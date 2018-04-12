package com.guohuai.rules.config;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * 完整的规则定义, 包含一系列的规则表达式.
 * 
 * @author xulizhong
 *
 */
@Data
@Builder
public class RuleDefinition {
	String oid, name;
	
	List<RuleExpression> ruleSet;

	/**
	 * 规则集选项: &&,||
	 */
	String ruleOpt = "&&";
	
	String action;
	
	String actionParams;
}
