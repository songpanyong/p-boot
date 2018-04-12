package com.guohuai.qams.score;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.ext.web.BaseController;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.basic.component.ext.web.Response;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.que.QamsQueDao;

/**
 * 评分准则
 * @author wzx
 *
 */
@Controller
@RequestMapping(value = "${request.prefix}/qams/score", produces = "application/json;charset=utf-8")
public class QamsScoreController extends BaseController {

	@Autowired
	private QamsScoreService qamsScoreService;
	@Autowired
	private QamsQueDao qamsQueDao;
	@Autowired
	private AdminSdk adminSdk;
	@Autowired
	private QamsScoreDao qamsScoreDao;
	@RequestMapping(value = "/findQueList", name = " 获取问卷", method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody ResponseEntity<Response> getQamsQues() {
		List<QamsQue> qamsQue=qamsQueDao.findAll();
		Response r = new Response();
		r.with("rows", qamsQue);
		return new ResponseEntity<Response>(r, HttpStatus.OK);
	}
	@RequestMapping(value = "/save", name = "新加评分准则", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> savePeriodic(@Valid SaveQamsScoreForm form) throws ParseException, Exception {
		String user=super.getLoginUser();
		BaseResp repponse = this.qamsScoreService.save(user,form);
		return new ResponseEntity<BaseResp>(repponse, HttpStatus.OK);
	}
	@RequestMapping(value = "/search", name = "评分列表查询", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<PageResp<QamsScoreResp>> applyList(HttpServletRequest request, @RequestParam final String que, @RequestParam int page, @RequestParam int rows) {

		if (page < 1) {
			page = 1;
		}
		if (rows < 1) {
			rows = 1;
		}

		Specification<QamsScore> codeSpec = null;
			if (!StringUtil.isEmpty(que)) {
			codeSpec = new Specification<QamsScore>() {
				@Override
				public Predicate toPredicate(Root<QamsScore> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
					return cb.equal(root.get("qamsQue").get("sid").as(String.class), que);
				}
			};
			codeSpec = Specifications.where(codeSpec);
		}

		Pageable pageable = new PageRequest(page - 1, rows, new Sort(new Order(Direction.ASC, "sid")));
		PageResp<QamsScoreResp> rep = this.qamsScoreService.list(codeSpec, pageable);
		return new ResponseEntity<PageResp<QamsScoreResp>>(rep, HttpStatus.OK);
	}
	@RequestMapping(value = "/delete", name="删除评分准则", method = { RequestMethod.POST, RequestMethod.DELETE })
	@ResponseBody
	public ResponseEntity<BaseResp> delete(@RequestParam(required = true) String sid) {
		BaseResp repponse = this.qamsScoreService.delete(sid);
		return new ResponseEntity<BaseResp>(repponse, HttpStatus.OK);
	}
	/**
	 * 评分准则明细
	 * 
	 * @param sid
	 *           评分准则的sid
	 * @return {@link ResponseEntity<QamsScoreResp>} 
	 */
	@RequestMapping(value = "/detail", name="评分准则明细", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public ResponseEntity<QamsScoreResp> detail(@RequestParam(required = true) String sid) {
		QamsScoreResp pr = this.qamsScoreService.read(sid);
		return new ResponseEntity<QamsScoreResp>(pr, HttpStatus.OK);
	}
	/**
	 * 更新评分准则
	 * @param form
	 * @return
	 */
	@RequestMapping(value = "/update", name="编辑评分准则", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<BaseResp> update(@Valid UpdateQamsScoreForm form) throws ParseException {
		String user=super.getLoginUser();
		BaseResp repponse = this.qamsScoreService.update(user,form);
		return new ResponseEntity<BaseResp>(repponse, HttpStatus.OK);
	}
	@RequestMapping(value = "/getScores", name="根据问卷查看所有评分准则", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<List<QamsScoreResp>> getScoreList(@RequestParam(required = true) String qid){
		List<QamsScore> qamsScores=this.qamsScoreDao.findAllByQueSid(qid);
		List<QamsScoreResp> queryReps=new ArrayList<QamsScoreResp>();
		for(QamsScore qamsScore:qamsScores){
			queryReps.add(new QamsScoreResp(qamsScore));
		}
		return new ResponseEntity<List<QamsScoreResp>>(queryReps, HttpStatus.OK);
	}
}

