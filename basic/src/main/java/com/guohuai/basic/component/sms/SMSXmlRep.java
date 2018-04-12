package com.guohuai.basic.component.sms;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@lombok.Data
@XStreamAlias("response")
public class SMSXmlRep {

	String error;
	
	String message;
}
