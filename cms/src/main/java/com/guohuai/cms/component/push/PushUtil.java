package com.guohuai.cms.component.push;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component
public class PushUtil {
	/**个推-内容 */
	public static Map<String, PushContEntity> pushContentsMap = new HashMap<String, PushContEntity>();
	
	// 个推模板
	@Value("${push.single.contents}")
	String pushContents;
	
	@PostConstruct
	void init(){
		List<PushContentEntity> pushContentsList = JSON.parseArray(this.pushContents, PushContentEntity.class);
		
		if(pushContentsMap.size() == 0){
			for (PushContentEntity en : pushContentsList) {
				pushContentsMap.put(en.getPushId(), en.getPushCont());
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
