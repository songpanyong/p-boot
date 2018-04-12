//package com.guohuai.basic.component.sms;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import com.guohuai.basic.common.StringUtil;
//import com.guohuai.basic.component.ext.web.BaseResp;
//import com.taobao.api.ApiException;
//import com.taobao.api.DefaultTaobaoClient;
//import com.taobao.api.TaobaoClient;
//import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
//import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * 阿里大于短信通道
// *
// */
//@Slf4j
//@Service("alidayuSms")
//public class AlidayuSmsSender implements SmsSender<AlidayuTemplateSms>{
//	
//	private static TaobaoClient taobaoClient;
//	
//	//阿里大于
//	@Value("${sms.serverUrl:#{null}}")
//	private String serverUrl;
//	@Value("${sms.appKey:#{null}}")
//	private String appKey;
//	@Value("${sms.appSecret:#{null}}")
//	private String appSecret;
//	
//	
//	@PostConstruct
//	public void initAlidayuSMS() {
//		if(!StringUtil.isEmpty(this.serverUrl)&&
//				!StringUtil.isEmpty(this.appKey)&&
//				!StringUtil.isEmpty(this.appSecret)){
//			taobaoClient=new DefaultTaobaoClient(this.serverUrl, this.appKey, this.appSecret);
//			
//		}else{
//			log.error("阿里大于短信渠道初始化数据失败！");
//		}
//	}
//	
//	@Override
//	public BaseResp send(AlidayuTemplateSms t) {
//		BaseResp resp = new BaseResp();
//		AlibabaAliqinFcSmsNumSendResponse rsp = null;
//		if (t.getPhone() == null || t.getSignName()==null||
//				t.getExtend()==null|| t.getTemplateCode()==null ||
//				t.getSmsType()==null) {
//			resp.setErrorCode(-1);
//			resp.setErrorMessage("无效的短信内容！");
//			return resp;
//		}
//		AlibabaAliqinFcSmsNumSendRequest req=new AlibabaAliqinFcSmsNumSendRequest();
//		req.setExtend(t.getExtend());
//		req.setSmsType(t.getSmsType());
//		req.setSmsFreeSignName(t.getSignName());
//		req.setRecNum(t.getPhone());
//		req.setSmsTemplateCode(t.getTemplateCode());
//		req.setSmsParamString(t.getSmsParamStr());
//		
//		try {
//			rsp=taobaoClient.execute(req);
//			if(!rsp.isSuccess()){
//				log.error("用户：{}，短信发送失败，错误原因：{}", t.getPhone(), rsp.getMsg() + "(" + rsp.getErrorCode() + ")");
//				resp.setErrorCode(-1);
//				resp.setErrorMessage(rsp.getMsg()+"("+rsp.getErrorCode()+")");
//				
//				return resp;
//			}
//		} catch (ApiException e) {
//			log.error("用户：{}，短信发送失败。原因：{}", t.getPhone(), e.getMessage());
//			
//			resp.setErrorCode(-1);
//			resp.setErrorMessage("用户："+t.getPhone()+"，短信发送失败，错误原因： "+e.getMessage());
//			
//			return resp;
//		}
//		
//		log.debug("send sms " + t.getTemplateCode() + " to " + t.getPhone());
//		
//		return resp;
//	}
//
//}
