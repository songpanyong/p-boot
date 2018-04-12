package com.guohuai.qams.que;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answer.QamsAnswerService;
import com.guohuai.qams.answerIssue.QamsAnswerIssue;
import com.guohuai.qams.answerIssue.QamsAnswerIssueService;
import com.guohuai.qams.answerQue.QamsAnswerQue;
import com.guohuai.qams.answerQue.QamsAnswerQueService;
import com.guohuai.qams.answerRecord.QamsAnswerRecordService;
import com.guohuai.qams.que.QamsQueResultResp.QueAndAnswer;
import com.guohuai.qams.que.QamsQueResultResp.QueAndAnswer.Answer;
import com.guohuai.qams.question.QamsQuestion;
import com.guohuai.qams.question.QamsQuestionService;

@Service
@Transactional
public class QamsQueService {
	@Autowired
	private QamsQueDao qamsQuedao;
	@Autowired
	private QamsQuestionService qamsQuestionService;
	@Autowired
	private QamsAnswerQueService qamsAnswerQueService;
	@Autowired
	private QamsAnswerService qamsAnswerService;
	@Autowired
	private QamsAnswerRecordService qamsAnswerRecordService;
	@Autowired
	private QamsAnswerIssueService qamsAnswerIssueService;
	
	public Page<QamsQue> QamsQueList(Specification<QamsQue> spec, Pageable pageable) {
		return this.qamsQuedao.findAll(spec, pageable);
	}
	/**
	 * 新增问卷
	 * 
	 * @param oid
	 * @return
	 */
	public void addQamsQue(QamsQueForm form) {
		
		QamsQue entity = new QamsQue();
		List<QamsQue> qams = qamsQuedao.findByType(form.getType());
		if (qams.size()>0 ){ 
			throw GHException.getException("该类型已有问卷");
		}else{
			entity.setName(form.getName());
			entity.setType(form.getType());
			entity.setStatus(form.getType());
			entity.setMaxScore("0");
			entity.setMinScore("0");
			entity.setCreate_time(new Timestamp(System.currentTimeMillis()));
		}
		
		entity = this.qamsQuedao.save(entity);
		
	}
	/**
	 * 作废
	 * @param oid
	 * @return
	 */
	public QamsQue invalid(String oid) {
		QamsQue it = this.findByOid(oid);
		it.setStatus(QamsQue.ILLIQUIDASSET_STATE_INVALID);
		it = this.updateEntity(it);
		return it;
	}
	/**
	 * 作废
	 * @param oid
	 * @return
	 */
	@Transactional
	private QamsQue updateEntity(QamsQue it) {
		return this.qamsQuedao.save(it);
	}
	/**
	 * 根据OID查询
	 * 
	 * @param oid
	 * @return
	 */
	public QamsQue findByOid(String oid) {
		QamsQue entity = this.qamsQuedao.findOne(oid);
		if (null == entity) {
			throw GHException.getException(70000);
		}
		return entity;
	}
	/**
	 * 根据id查询问卷
	 * @param queId
	 * @return
	 */
	@Transactional
	public QamsQue getQueById(String queId) {
		return this.qamsQuedao.findOne(queId);
	}
	public List<QamsQueOptGroupResp> optgroup() {

		List<QamsQue> qamsQues = this.qamsQuedao.findAllOrderBySidAsc();
		List<QamsQueOptGroupResp> result = new ArrayList<QamsQueOptGroupResp>();

		if (qamsQues.size() > 0) {
			for(QamsQue qamsQue:qamsQues){
				QamsQueOptGroupResp qamsQueOPt=new QamsQueOptGroupResp();
				qamsQueOPt.setText(qamsQue.getName());
				qamsQueOPt.setValue(qamsQue.getSid());
				result.add(qamsQueOPt);
			}
			
		}
		return result;
	}
	
	/**
	 * 获取所有未删除的问卷的名称列表，包含id
	 * 
	 * @return
	 */
	@Transactional
	public List<JSONObject> getAllNameList() {
		List<JSONObject> jsonObjList = new ArrayList<>();
		List<Object> objList = this.qamsQuedao.findAllNameList();
		if (!objList.isEmpty()) {
			Object[] obs = null;
			JSONObject jsonObj = null;
			for (Object obj : objList) {
				obs = (Object[]) obj;
				jsonObj = new JSONObject();
				jsonObj.put("sid", obs[0]);
				jsonObj.put("name", obs[1]);

				jsonObjList.add(jsonObj);
			}
		}

		return jsonObjList;
	}
	
	/**
	 * 根据问卷ID查询问卷调查结果
	 * @param sid
	 * @return
	 */
	@Transactional
	public QamsQueResultResp getResultByQueId(String sid) {
		QamsQue qamsQue = this.qamsQuedao.findOne(sid);
		if (null == qamsQue) {
			throw new GHException("未知的问卷ID");
		}
		QamsQueResultResp resultResp = new QamsQueResultResp(qamsQue);
		List<QueAndAnswer> list = new ArrayList<>();
		List<QamsQuestion> questions = this.qamsQuestionService.getListByQueId(sid);
		if (null != questions && questions.size() > 0) {
			for (QamsQuestion qamsQuestion : questions) {
				QueAndAnswer queAndAnswer = new QueAndAnswer();
				List<QamsAnswer> answers = this.qamsAnswerService.getAnswersByQid(qamsQuestion.getSid());
				List<Answer> ans = new ArrayList<>();
				for (QamsAnswer qamsAnswer : answers) {
					Answer answer = new Answer();
					answer.setAid(qamsAnswer.getSid());
					answer.setSn(qamsAnswer.getSn());
					answer.setText(qamsAnswer.getContent());
					BigDecimal total = this.qamsAnswerRecordService.getTotal(qamsQuestion.getSid());
					QamsAnswerIssue qamsAnswerIssue = this.qamsAnswerIssueService.getByIssueId(qamsQuestion.getSid());
					BigDecimal num = this.qamsAnswerRecordService.getNumber(qamsAnswerIssue.getSid(),qamsAnswer.getSid());
					BigDecimal percent = num.divide(total).multiply(new BigDecimal(100).setScale(2, RoundingMode.HALF_UP));
					answer.setNumber(num);
					answer.setPercent(percent);
					ans.add(answer);
				}
				queAndAnswer.setId(qamsQuestion.getSid());
				queAndAnswer.setContent(qamsQuestion.getContent());
				queAndAnswer.setAnswers(ans);
				list.add(queAndAnswer);
			}
		}
		resultResp.setList(list);;
		return resultResp;
	}
	/**
	 * 根据问卷ID查询问卷调查结果
	 * @param sid
	 * @return
	 */
	@Transactional
	public QamsQueResultResp getQueResultById(String sid) {
		QamsQue qamsQue = this.qamsQuedao.findOne(sid);
		if (null == qamsQue) {
			throw new GHException("未知的问卷ID");
		}
		QamsQueResultResp resultResp = new QamsQueResultResp(qamsQue);
		List<QueAndAnswer> list = new ArrayList<>();
		List<QamsQuestion> questions = this.qamsQuestionService.getListByQueId(sid);
		if (null != questions && questions.size() > 0) {
			for (QamsQuestion qamsQuestion : questions) {
				QueAndAnswer queAndAnswer = new QueAndAnswer();
				List<QamsAnswer> answers = this.qamsAnswerService.getAnswersByQid(qamsQuestion.getSid());
				List<Answer> ans = new ArrayList<>();
				for (QamsAnswer qamsAnswer : answers) {
					Answer answer = new Answer();
					answer.setAid(qamsAnswer.getSid());
					answer.setSn(qamsAnswer.getSn());
					answer.setText(qamsAnswer.getContent());
					answer.setScore(qamsAnswer.getScore());
					ans.add(answer);
				}
				queAndAnswer.setId(qamsQuestion.getSid());
				queAndAnswer.setContent(qamsQuestion.getContent());
				queAndAnswer.setChType(qamsQuestion.getCh_type());
				queAndAnswer.setAnswers(ans);
				list.add(queAndAnswer);
			}
		}
		resultResp.setList(list);;
		return resultResp;
	}
	
	/**
	 * 根据问卷ID查询问卷
	 * @param sid
	 * @return
	 */
	@Transactional
	public QamsQueResp getQueRespById(String sid) {
		QamsQue qamsQue = this.qamsQuedao.findOne(sid);
		if (null == qamsQue) {
			throw new GHException("未知的问卷ID");
		}
		QamsQueResp resp = new QamsQueResp(qamsQue);
		return resp;
	}
	/**
	 * 修改问卷
	 * @param sid
	 * @return
	 */
	public QamsQue updateQamsQue(QamsQueForm form) {
		QamsQue entity = this.qamsQuedao.findOne(form.getSid());
		entity.setName(form.getName());
		qamsQuedao.save(entity);
		return entity;
	}
	/**
	 * 获取所有问卷的名称列表，包含id
	 * @return
	 */
	@Transactional
	public List<JSONObject> getAllName() {
		List<JSONObject> jsonObjList = new ArrayList<>();
		List<Object> objList = this.qamsQuedao.findAllName();
		if (!objList.isEmpty()) {
			Object[] obs = null;
			JSONObject jsonObj = null;
			for (Object obj : objList) {
				obs = (Object[]) obj;
				jsonObj = new JSONObject();
				jsonObj.put("sid", obs[0]);
				jsonObj.put("name", obs[1]);

				jsonObjList.add(jsonObj);
			}
		}

		return jsonObjList;
	}
}
