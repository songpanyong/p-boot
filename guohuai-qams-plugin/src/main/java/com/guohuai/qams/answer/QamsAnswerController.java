
package com.guohuai.qams.answer;

import java.util.ArrayList;
import java.util.List;

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
import com.guohuai.qams.score.QamsScore;
import com.guohuai.qams.score.QamsScoreResp;

/**
 * 问题答案
 * @author wzx
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/answer", produces = "application/json;charset=utf-8")
public class QamsAnswerController extends BaseController {
	@Autowired
	private QamsAnswerService qamsAnswerService;
	@RequestMapping(value = "/getAnswer", name="根据问题查看答案", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<QamsAnswerResp>> getAnswerList(@RequestParam(required = true) String iid){
		List<QamsAnswer> qamsAnswers=this.qamsAnswerService.getAnswersByQid(iid);
		List<QamsAnswerResp> queryReps=new ArrayList<QamsAnswerResp>();
		for(QamsAnswer qamsAnswer:qamsAnswers){
			queryReps.add(new QamsAnswerResp(qamsAnswer));
		}
		return new ResponseEntity<List<QamsAnswerResp>>(queryReps, HttpStatus.OK);
	}
	
}


