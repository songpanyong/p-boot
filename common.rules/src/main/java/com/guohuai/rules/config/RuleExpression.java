package com.guohuai.rules.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 规则表达式.
 * 
 * @author xulizhong
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RuleExpression {
	String oid, name;
	//规则表达式
	String expr;
	
	//表达式 days >= 100 && channelId == 'APP'
	String attr, op, value,type;
}
