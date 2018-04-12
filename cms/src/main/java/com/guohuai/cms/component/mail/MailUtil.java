package com.guohuai.cms.component.mail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class MailUtil {
	/**站内信 ID-内容 */
	public static Map<String, MailContEntity> mailContentsMap = new HashMap<String, MailContEntity>();
	
	// 站内信模板
	@Value("${mail.contents}")
	String mailContents;
	
	@PostConstruct
	void init(){
		List<MailContentEntity> mailContentsList = JSON.parseArray(this.mailContents, MailContentEntity.class);
		
		if(mailContentsMap.size() == 0){
			for (MailContentEntity en : mailContentsList) {
				mailContentsMap.put(en.getSmsId(), en.getSmsCont());
			}
		}
	}
	
	/**
	 * 替换掉{1}格式
	 * @param target 目标
	 * @param strArr 替换的值
	 * @return
	 */
	public static String replaceComStrArr(String target, String[] repArr){
		for (int i = 1; i <= repArr.length; i++) {
			target = target.replace("{" + i +"}" , repArr[i - 1]);
		}
		return target;
	}
	
}
