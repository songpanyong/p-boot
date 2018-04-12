package com.guohuai.basic.component.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.sms.smssdk.SMSTZHLApi;

import lombok.extern.slf4j.Slf4j;
/**
 * 同舟互联短信通道
 *
 */
@Service("tongzhouhulianSms")
@Slf4j
public class TongzhouhulianSmsSender implements SmsSender<ContentSms> {
	@Autowired
	private SMSTZHLApi sMSTZHLApi;
	
	// 同舟互联
	@Value("${sms.tzhl.uid:#{null}}")
	private String uid;
	@Value("${sms.tzhl.pwd:#{null}}")
	private String pwd;
	
	
	@Override
	public BaseResp send(ContentSms t) {
		BaseResp resp = new BaseResp();
		if (StringUtil.isEmpty(this.uid)) {
			resp.setErrorCode(-1);
			resp.setErrorMessage("同舟互联短信渠道没有初始化");
			return resp;
		}
		
		String rep;
		String content=t.getContent();
		
		try {
			content=t.getContent();//URLEncoder.encode(t.getContent(),"GBK");
			log.info("用户："+t.getPhone()+"，短信发送内容"+t.getContent()+"，转码后的内容："+content);
		} catch (Exception e) {
			log.error("用户：{}，短信发送失败，编码转换失败。", t.getPhone());			
			resp.setErrorCode(-1);
			resp.setErrorMessage("生成验证码失败！");
			return resp;
		}
		
		rep = sMSTZHLApi.sendTZHLSMS(this.uid, this.pwd, t.getPhone(),content);
		
		if (StringUtil.isEmpty(rep)) {
			log.error("用户：{}，短信发送失败，返回为空。", t.getPhone());			
			resp.setErrorCode(-1);
			resp.setErrorMessage("生成验证码失败！");
			return resp;
		}
		
		if (!"100".equals(rep)) {
			log.error("用户：{}，短信发送失败，错误原因：{}", t.getPhone(), rep + "(" + rep + ")");
			resp.setErrorCode(-1);
			resp.setErrorMessage(rep + "(" + rep + ")");
		} else {
			log.info("用户：{}，短信发送成功。", t.getPhone());
		}
		
		return resp;
	}
}
