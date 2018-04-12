package com.guohuai.qams.answerRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.guohuai.basic.component.ext.web.BaseController;

/**
 * 答案记录
 * @author William Zhang
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/answerRecord", produces = "application/json;charset=utf-8")
public class QamsAnswerRecordController extends BaseController {

	@Autowired
	private QamsAnswerRecordService answerRecordService;

}
