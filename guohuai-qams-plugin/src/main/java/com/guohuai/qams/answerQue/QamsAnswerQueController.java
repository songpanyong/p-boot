package com.guohuai.qams.answerQue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answer.QamsAnswerService;
import com.guohuai.qams.answerQue.QamsAnswerQueForm.Questions;

import net.kaczmarzyk.spring.data.jpa.domain.DateAfterInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBeforeInclusive;
import net.kaczmarzyk.spring.data.jpa.domain.DateBetween;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.LessThanOrEqual;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;

/**
 * 答卷
 * @author William Zhang
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/answerQue", produces = "application/json;charset=utf-8")
public class QamsAnswerQueController extends BaseController {

	@Autowired
	private QamsAnswerQueService qamsAnswerQueService;
	@Autowired
	private QamsAnswerService qamsAnswerService;
	
	/**
	 * 保存答卷
	 * @param queId 问卷id
	 * @param answers 答案集合
	 * @return
	 */
	@RequestMapping(value = "/saveAnswerQue", name = "问卷调查-保存答卷", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsAnswerQueResp> saveAnswerQue(@RequestBody QamsAnswerQueForm form) {
		String queId = form.getSid();
		List<Questions> questions = form.getQuestions();
		List<QamsAnswer> answers = new ArrayList<>();
		for (Questions que : questions) {
			List<String> ans = que.getAnswers();
			for (String aid : ans) {
				QamsAnswer answer = this.qamsAnswerService.getById(aid);
				answers.add(answer);
			}
		}
		QamsAnswerQueResp resp = this.qamsAnswerQueService.saveAnswerQue(queId,answers,super.getLoginUser());
		return new ResponseEntity<QamsAnswerQueResp>(resp, HttpStatus.OK);
	}

	/**
	 * 根据问卷ID查看调查记录
	 * @param request
	 * @param name 参与者姓名
	 * @param telephone 参与者电话
	 * @param queName 问卷名称
	 * @param grade 风险等级
	 * @param startDate
	 * @param endDate
	 * @param minScore
	 * @param maxScore
	 * @param page
	 * @param rows
	 * @param sortField
	 * @param sort
	 * @return
	 */
	@RequestMapping(value = "/qamsAnswerQueList", name = " 调查问卷记录", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsAnswerQueListResp> qamsAnswerQueList(HttpServletRequest request,
			@And({ @Spec(params = "sid", path = "qamsQue.sid", spec = Equal.class),
				   @Spec(params = "name", path = "authorName", spec = Like.class),
				   @Spec(params = "telephone", path = "authorPhone", spec = Like.class),
				   @Spec(params = "queName", path = "qamsQue.name", spec = Like.class),
				   @Spec(params = "grade", path = "grade", spec = Equal.class),
				   @Spec(params = "startDate", path = "createTime", spec =DateAfterInclusive.class ),
				   @Spec(params = "endDate", path = "createTime", spec = DateBeforeInclusive.class),
				   @Spec(params = "minScore", path = "score", spec = GreaterThanOrEqual.class),
				   @Spec(params = "maxScore", path = "score", spec = LessThanOrEqual.class)})Specification<QamsAnswerQue> spec,
			@RequestParam(required = false, defaultValue = "1") int page, 
			@RequestParam(required = false, defaultValue = "10") int rows,
			@RequestParam(required = false, defaultValue = "createTime") String sortField, 
			@RequestParam(required = false, defaultValue = "desc") String sort) {

		Direction sortDirection = Direction.DESC;
		if (!"desc".equals(sort)) {
			sortDirection = Direction.ASC;
		}
//		Specification<QamsAnswerQue> stateSpec = new Specification<QamsAnswerQue>() {
//			@Override
//			public Predicate toPredicate(Root<QamsAnswerQue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				return null;
//			}
//		};
//		spec = Specifications.where(spec).and(stateSpec);
		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(sortDirection, sortField)));
		QamsAnswerQueListResp resps = qamsAnswerQueService.list(spec, pageable);
		return new ResponseEntity<QamsAnswerQueListResp>(resps, HttpStatus.OK);
	}
	
	/**
	 * 根据sid查询问卷调查详情
	 * @param sid 答卷ID
	 * @return
	 */
	@RequestMapping(value = "/qamsAnswerQueDetail", name = "问卷调查详情", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsAnswerQueDeatilResp> qamsAnswerQueDetail(@RequestParam String sid) {
		QamsAnswerQueDeatilResp resp = this.qamsAnswerQueService.getDetailByQueId(sid,super.getLoginUser());
		return new ResponseEntity<QamsAnswerQueDeatilResp>(resp, HttpStatus.OK);
	}
	
	
	/**
	 * 根据sid查询答卷详情
	 * @param sid 答卷ID
	 * @return
	 */
	@RequestMapping(value = "/answerQueDetail", name = "答卷详情", method = { RequestMethod.POST })
	public @ResponseBody ResponseEntity<QamsAnswerQueResp> answerQueDetail(@RequestParam String sid) {
		QamsAnswerQueResp resp = this.qamsAnswerQueService.getDetailById(sid);
		return new ResponseEntity<QamsAnswerQueResp>(resp, HttpStatus.OK);
	}
	
}
