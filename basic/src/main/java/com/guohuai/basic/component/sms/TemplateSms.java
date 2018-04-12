package com.guohuai.basic.component.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class TemplateSms extends Sms {
	public String tempId;
	public String [] values;
}
