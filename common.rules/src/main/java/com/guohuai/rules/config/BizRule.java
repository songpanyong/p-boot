package com.guohuai.rules.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BizRule {
	String ruleId;
	String ruleType, content;
}
