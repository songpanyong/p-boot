
package com.guohuai.qams.question;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.Response;
import com.guohuai.qams.que.QamsQueResp;

/**
 * 问题
 * @author wzx
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/question", produces = "application/json;charset=utf-8")
public class QamsQuestionController extends BaseController {
	@Autowired
	private QamsQuestionService qamQuestionService;
	/**
	 * 新增问题
	 * 
	 * @param investment
	 * @return
	 */
	@RequestMapping(name = "新增问题", value = "add", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Response> add(@Valid QamsQuestionForm form) {
		qamQuestionService.add(form);
		Response r = new Response();
		r.with("result", "SUCCESSED!");
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	@RequestMapping(name = "排序问题", value = "sort", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Response> sort(@Valid SortQuestionForm form) {
		qamQuestionService.sort(form);
		Response r = new Response();
		r.with("result", "SUCCESSED!");
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	/**
	 * 根据问题ID查询问题 
	 * @param sid
	 * @return
	 * @author wzx
	 */
	@RequestMapping(name = "修改问题", value = "getQuesById", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<QamsQuestionResp> getQuesById(@RequestParam String qid) {
		QamsQuestionResp resp = qamQuestionService.getQuesById(qid);
		return new ResponseEntity<QamsQuestionResp>(resp, HttpStatus.OK);
	}
	/**
	 * 删除问题
	 * @param form
	 * @return
	 */
	@RequestMapping(name = "删除问题", value = "del", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Response> del(@RequestParam String qid) {
		qamQuestionService.del(qid);
		Response r = new Response();
		r.with("result", "SUCCESSED!");
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
}


