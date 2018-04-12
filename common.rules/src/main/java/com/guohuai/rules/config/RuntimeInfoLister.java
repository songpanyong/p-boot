package com.guohuai.rules.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.guohuai.rules.action.ActionAnno;
import com.guohuai.rules.action.ActionInfo;
import com.guohuai.rules.action.ActionParam;
import com.guohuai.rules.event.EventAnno;
import com.guohuai.rules.event.EventAttr;
import com.guohuai.rules.event.EventAttr.EventAttrBuilder;
import com.guohuai.rules.event.EventInfo;
import com.guohuai.rules.event.EventInfo.EventInfoBuilder;

@Slf4j
@Service
public class RuntimeInfoLister {

	@Autowired
	ApplicationContext applicationContext;

	private Set<EventInfo> eventInfoSet = new HashSet<>();
	private Set<ActionInfo> actionInfoSet = new HashSet<>();

	@PostConstruct
	public void initEventList() {
		initEventInfoSet();
		this.initActionInfoSet();
	}

	void initActionInfoSet() {
		Class<ActionAnno> cls = ActionAnno.class;
		String[] beanNames = this.applicationContext
				.getBeanNamesForAnnotation(cls);

		if (beanNames == null) {
			log.info("no event bean!");
			return;
		}

		log.info("got action beans: {}", Joiner.on(",").join(beanNames));

		for (String beanName : beanNames) {
			Object obj = this.applicationContext.getBean(beanName);
			ActionAnno anno = obj.getClass().getAnnotation(cls);
			String actionName = anno.desc();
			if (actionName.length() == 0) {
				actionName = anno.value();
			}

			List<EventAttr> attrList = new ArrayList<EventAttr>();
			ActionParam[] params = anno.params();
			if (params != null) {
				for (ActionParam p : params) {
					EventAttr attr = EventAttr.builder().id(p.id())
							.name(p.desc()).type(p.type().getName())
							.enums(p.enums()).build();
					attrList.add(attr);
				}
			}

			ActionInfo ainfo = ActionInfo.builder().id(beanName)
					.name(actionName).paramList(attrList).build();

			this.getActionInfoSet().add(ainfo);
		}
		log.info("ACTION INFO SET: {}", JSON.toJSONString(this.getActionInfoSet()));
	}

	private void initEventInfoSet() {
		Class<EventAnno> cls = EventAnno.class;
		String[] beanNames = this.applicationContext
				.getBeanNamesForAnnotation(cls);

		if (beanNames == null) {
			log.info("no event bean!");
			return;
		}

		log.info("got event beans: {}", Joiner.on(",").join(beanNames));

		for (String beanName : beanNames) {
			Object obj = this.applicationContext.getBean(beanName);
			EventAnno anno = obj.getClass().getAnnotation(cls);
			String eventName = anno.desc();
			if (eventName.length() == 0) {
				eventName = anno.value();
			}

			List<EventAttr> attrList = new ArrayList<EventAttr>();
			EventInfoBuilder builder = EventInfo.builder().eventId(beanName)
					.eventClass(obj.getClass().getName()).eventName(eventName)
					.attrList(attrList);

			Field[] fields = obj.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (f.isAnnotationPresent(cls)) {
					EventAnno info = f.getAnnotation(cls);
					EventAttrBuilder attrBuilder = EventAttr.builder()
							.id(f.getName()).name(info.value())
							.type(f.getType().getName());

					if (info.enums().length() > 0) {
						attrBuilder.enums(info.enums());
					}

					attrList.add(attrBuilder.build());
				}
			}

			if (!attrList.isEmpty()) {
				this.getEventInfoSet().add(builder.build());
			}
		}

		log.info("EVENT INFO SET: {}", JSON.toJSONString(this.eventInfoSet));
	}

	public Set<EventInfo> getEventInfoSet() {
		return eventInfoSet;
	}

	public void setEventInfoSet(Set<EventInfo> eventInfoSet) {
		this.eventInfoSet = eventInfoSet;
	}

	public Set<ActionInfo> getActionInfoSet() {
		return actionInfoSet;
	}

	public void setActionInfoSet(Set<ActionInfo> actionInfoSet) {
		this.actionInfoSet = actionInfoSet;
	}
}
