package com.guohuai.qams.answerIssue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guohuai.basic.component.ext.web.BaseController;

/**
 * 答题
 * @author William Zhang
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/answerIssue", produces = "application/json;charset=utf-8")
public class QamsAnswerIssueController extends BaseController {

	@Autowired
	private QamsAnswerIssueService answerIssueService;

}
