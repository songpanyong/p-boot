package com.guohuai.basic.component.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.sms.smssdk.SMSApi;
import com.thoughtworks.xstream.XStream;

import lombok.extern.slf4j.Slf4j;
/**
 * 亿美短信通道
 * @author jeffrey
 *
 */
@Service("yimeiSms")
@Slf4j
public class YimeiSmsSender implements SmsSender<ContentSms> {

	@Autowired
	private SMSApi sMSApi;
	
	// 亿美
	@Value("${sms.yimei.cdkey:#{null}}")
	private String cdkey;
	@Value("${sms.yimei.password:#{null}}")
	private String password;
	@Value("${sms.yimei.addserial:#{null}}")
	private String addserial;
	
	@Override
	public BaseResp send(ContentSms t) {
		BaseResp resp = new BaseResp();
		if (StringUtil.isEmpty(this.cdkey)) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("亿美短信渠道没有初始化");
			return resp;
		}
		String smsXmlRep = sMSApi.sendSMS(this.cdkey, this.password, t.getPhone(), t.getContent(), this.addserial);
		
		if (StringUtil.isEmpty(smsXmlRep)) {
			log.error("用户：{}，短信发送失败，返回为空。", t.getPhone());			
			resp.setErrorCode(-1);
			resp.setErrorMessage("生成验证码失败！");
			return resp;
		}
		
		XStream xs = new XStream();
		xs.processAnnotations(SMSXmlRep.class);
		xs.autodetectAnnotations(true);
		xs.ignoreUnknownElements();
		SMSXmlRep xmlRep = (SMSXmlRep) xs.fromXML(smsXmlRep.trim());
		
		if (!"0".equals(xmlRep.getError())) {
			log.error("用户：{}，短信发送失败，错误原因：{}", t.getPhone(), xmlRep.getError() + "(" + xmlRep.getMessage() + ")");
			resp.setErrorCode(-1);
			resp.setErrorMessage(xmlRep.getError() + "(" + xmlRep.getMessage() + ")");
		} else {
			log.info("用户：{}，短信发送成功。", t.getPhone());
		}
		
		return resp;
	}

}
