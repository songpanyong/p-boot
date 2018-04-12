package com.guohuai.tulip.platform.event.rule;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.tulip.platform.event.EventEntity;
import com.guohuai.tulip.platform.event.EventQueryRep;
import com.guohuai.tulip.platform.event.EventService;
import com.guohuai.tulip.util.DateUtil;
import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

@RestController
@RequestMapping(value = "/tulip/boot/eventrule", produces = "application/json")
public class EventRuleController extends BaseController {
	Logger logger = LoggerFactory.getLogger(EventRuleController.class);

	@Autowired
	private EventService eventService;
	/**
	 * @param request
	 *            请求
	 * @param spec
	 *            传参
	 * @param page
	 *            页码
	 * @param rows
	 *            行数
	 * @return
	 */
	@RequestMapping(value = "query", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PageResp<EventQueryRep>> query(HttpServletRequest request,
			@And({ @Spec(params = "title", path = "title", spec = Like.class),
					@Spec(params = "status", path = "status", spec = Equal.class),
					@Spec(params = "active", path = "active", spec = Equal.class),
					@Spec(params = "start", path = "start", spec = DateAfterInclusive.class, config = DateUtil.datePattern),
					@Spec(params = "finish", path = "finish", spec = DateBeforeInclusive.class, config = DateUtil.datePattern) }) Specification<EventEntity> spec,
			@RequestParam int page, @RequestParam int rows) {
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.DESC, "createTime")));
		PageResp<EventQueryRep> rep = this.eventService.query(spec, pageable);
		return new ResponseEntity<PageResp<EventQueryRep>>(rep, HttpStatus.OK);
	}

}
