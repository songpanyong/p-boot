package com.guohuai.basic.component.sms;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AlidayuTemplateSms extends Sms {
	/**
	 * 公共回传参数，在“消息返回”中会透传回该参数；
	 * 举例：用户可以传入自己下级的会员ID，在消息返回时，
	 * 该会员ID会包含在内，用户可以根据该会员ID识别是哪位会员使用了你的应用
	 */
	public String extend;
	/**
	 * 短信类型
	 */
	public String smsType;
	/**
	 * 短信签名
	 */
	public String signName;
	/**
	 * 短信模板ID
	 */
	public String templateCode;
	/**
	 * 短信模板变量，传参规则{"key":"value"}，key的名字须和申请模板中的变量名一致
	 * ，多个变量之间以逗号隔开。示例：针对模板“验证码${code}，
	 * 您正在进行${product}身份验证，打死不要告诉别人哦！”，
	 * 传参时需传入{"code":"1234","product":"alidayu"}
	 */
	public String smsParamStr;
	
}
