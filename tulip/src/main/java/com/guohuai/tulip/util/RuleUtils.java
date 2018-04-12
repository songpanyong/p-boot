package com.guohuai.tulip.util;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.mvel2.MVEL;

public class RuleUtils {
	
	/**
	 * 格式化 ruleItemEntity 中的规则信息
	 * @param value  ruleItemEntity.getValue()
	 * @param expression ruleItemEntity.getExpression()
	 * @param propId ruleItemEntity.getPropId()
	 * @return
	 */
	public final static String toExpression(String value, String expression, String propId) {
		String a[] = {};
		String result;
		if (value.indexOf("[") != -1) {
			a = value.replaceAll("\\[", "").replaceAll("\\]", "").split("\\,");
			result = propId + ">=" + a[0] + "&&" + propId + "<=" + a[1];
		} else if (value.indexOf("[") == -1 && "=".equals(expression)) {
			result = propId + expression + expression + value;
		} else {
			result = propId + expression + value;
		}
		return result;
	}
	
	/**
	 * 表达式校验
	 */
	public final static boolean compareExpression(String expression, Map<String, Object> map) {
		if (StringUtils.isBlank(expression)) {
			return true;
		}
		Boolean result;
		try {
			result = (Boolean) MVEL.eval(expression, map);
		} catch (Exception e) {
			result = new Boolean(false);
		}
		return result.booleanValue();
	}
	
}
