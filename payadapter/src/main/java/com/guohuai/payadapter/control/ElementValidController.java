package com.guohuai.payadapter.control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guohuai.payadapter.listener.event.OrderEvent;

@RestController
@RequestMapping(value = "/pay")
public class ElementValidController {
	@Autowired
	ApplicationEventPublisher publisher;

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void save() {
		OrderEvent event = new OrderEvent();
		event.setOrderNo("111111");
		publisher.publishEvent(event);
	}

}
