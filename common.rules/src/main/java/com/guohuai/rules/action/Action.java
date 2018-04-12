package com.guohuai.rules.action;


public interface Action {
	void execute(RuleFiredEvent ruleFired);
}
