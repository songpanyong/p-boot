package com.guohuai.tulip.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.guohuai.rules.config.DroolsContainerHolder;
import com.guohuai.tulip.platform.event.EventService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class InitEvent implements CommandLineRunner {

	@Autowired
	private EventService eventService;
	
	@Autowired
	DroolsContainerHolder containerHolder;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		log.debug(">>>>>>>>>>>>>>>服务启动执行，执行加载系统中已存在的规则活动开始 <<<<<<<<<<<<<");
		this.containerHolder.reload(eventService.initEventList());
		log.debug(">>>>>>>>>>>>>>>服务启动执行，执行加载系统中已存在的规则活动结束 <<<<<<<<<<<<<");
	}
	
	
}
