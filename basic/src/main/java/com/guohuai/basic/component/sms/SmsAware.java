package com.guohuai.basic.component.sms;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.guohuai.basic.component.ext.web.BaseResp;

@Component
public class SmsAware{
	
	@Autowired
	private ApplicationContext context;
	
	//选中的短信通道
	private static SmsChannel smsChannel;
	
	@Value("${sms.channel:yimei}")
	public void setSmsChannel(String v){
		smsChannel=SmsChannel.valueOf(v);
	}
	
	public static SmsChannel getSmsChannel(){
		return SmsAware.smsChannel;
	}
	
	public enum SmsChannel{
		
		yimei("yimei"),ronglian("ronglian"),alidayu("alidayu"),tongzhouhulian("tongzhouhulian"),dahansantong("dahansantong");;
		private String code;
		
		@Override
		public String toString() {
			return this.code;
		}
		private SmsChannel(String code){
			this.code=code;
		}
		
	}
	
	private static Map<SmsChannel, SmsSender<? extends Sms>> smsMap=new HashMap<SmsChannel, SmsSender<? extends Sms>>();
	
	@PostConstruct
	private void init(){
		smsMap.put(SmsChannel.yimei, this.context.getBean(YimeiSmsSender.class));
		smsMap.put(SmsChannel.ronglian, this.context.getBean(RonglianSmsSender.class));
//		smsMap.put(SmsChannel.alidayu, this.context.getBean(AlidayuSmsSender.class));
		smsMap.put(SmsChannel.tongzhouhulian, this.context.getBean(TongzhouhulianSmsSender.class));
	}

	@SuppressWarnings("unchecked")
	public static <T extends Sms>  BaseResp send(T t) {
		if (smsChannel != null) {
			SmsSender<T> sms=(SmsSender<T>)smsMap.get(smsChannel);
			return sms.send(t);
		}
		BaseResp rep=new BaseResp();
		rep.setErrorCode(-1);
		rep.setErrorMessage("无默认的短信渠道");
		return rep;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Sms>  BaseResp send(SmsChannel smsChannel,T t) {
		if (smsChannel != null) {
			SmsSender<T> sms=(SmsSender<T>)smsMap.get(smsChannel);
			return sms.send(t);
		}
		BaseResp rep=new BaseResp();
		rep.setErrorCode(-1);
		rep.setErrorMessage("无指定的短信渠道");
		return rep;
	}
}
