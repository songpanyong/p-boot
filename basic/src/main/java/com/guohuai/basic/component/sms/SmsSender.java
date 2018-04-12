package com.guohuai.basic.component.sms;

import com.guohuai.basic.component.ext.web.BaseResp;

public interface SmsSender<T extends Sms> {
	public BaseResp send(T t);
}
