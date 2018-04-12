package com.guohuai.qams.answerQue;


import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answer.QamsAnswerService;
import com.guohuai.qams.answerIssue.QamsAnswerIssue;
import com.guohuai.qams.answerIssue.QamsAnswerIssueService;
import com.guohuai.qams.answerQue.QamsAnswerQueDeatilResp.QueAndAnswer;
import com.guohuai.qams.answerQue.QamsAnswerQueDeatilResp.QueAndAnswer.Ans;
import com.guohuai.qams.answerRecord.QamsAnswerRecord;
import com.guohuai.qams.answerRecord.QamsAnswerRecordService;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.que.QamsQueService;
import com.guohuai.qams.question.QamsQuestion;
import com.guohuai.qams.question.QamsQuestionService;
import com.guohuai.qams.score.QamsScoreService;

@Service
@Transactional
public class QamsAnswerQueService {

	@Autowired
	private QamsAnswerQueDao qamsAnswerQueDao;
	@Autowired
	private QamsAnswerRecordService qamsAnswerRecordService;
	@Autowired
	private QamsAnswerIssueService qamsAnswerIssueService;
	@Autowired
	private QamsQueService qamsQueService;
	@Autowired
	private QamsQuestionService qamsQuestionService;
	@Autowired
	private QamsAnswerService qamsAnswerService;
	@Autowired
	private QamsScoreService qamsScoreService;
	@Autowired
	private AdminSdk adminSdk;
	@Autowired
	private InvestorBaseAccountInfoDao investorBaseAccountDao;
	@Autowired
	private QamsAnswerQueRequireNewService qamsAnswerQueRequireNewService;
	@Autowired
	RedisTemplate<String, String> redis;
	
	
	
	@Transactional
	public QamsAnswerQueResp saveAnswerQue(String queId, List<QamsAnswer> answers, String loginUser) {
		Charset utf8 = Charset.forName("utf8");
		boolean startFlag = redis.execute(new RedisCallback<Boolean>() {
			@Override
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				boolean nxflag = connection.setNX(loginUser.getBytes(utf8), "que".getBytes(utf8));
				boolean exflag = connection.expire(loginUser.getBytes(utf8), 10);
				return nxflag && exflag;
			}
		});
		if (!startFlag) {
			throw new GHException("问卷提交中");
		}
		QamsAnswerQueResp qamsAnswerQueResp = new QamsAnswerQueResp();
		try {
			qamsAnswerQueResp = qamsAnswerQueRequireNewService.saveAnswerQue(queId, answers, loginUser);
		} catch (Exception e) {
			
		} 
		
		redis.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				Long delFlag = connection.del(loginUser.getBytes(utf8));
				return delFlag;
			}
		});
		return qamsAnswerQueResp;
	}

//	/**
//	 * 保存答卷
//	 * 
//	 * @param queId
//	 * @param answers
//	 * @param loginUser
//	 * @return 
//	 */
//	@Transactional
//	public QamsAnswerQueResp saveAnswerQue(String queId, List<QamsAnswer> answers, String loginUser) {
//		// 问卷
//		QamsQue qamsQue = this.qamsQueService.getQueById(queId);
//		if (null == qamsQue) {
//			throw new GHException("未知的问卷ID");
//		}
//		//获取当前登录人信息      Todo  问卷库要和mimosa放到一起 不然此处会报错，为解佳兆业问题，此问题后续优化
//		InvestorBaseAccountInfoEntity  investorInfo  =investorBaseAccountDao.findOne(loginUser);
//		if (null == investorInfo) {
//			throw new GHException("未知的投资人信息");
//		}
//		// 答卷
//		QamsAnswerQue qamsAnswerQue = this.getAnswerQueByUser(queId,loginUser);
//		if (qamsAnswerQue == null) {
//			qamsAnswerQue = new QamsAnswerQue();
//			qamsAnswerQue.setSid(StringUtil.uuid());
//			qamsAnswerQue.setAuthorId(loginUser);
//			qamsAnswerQue.setAuthorName(investorInfo.getRealName());
//			qamsAnswerQue.setAuthorPhone(investorInfo.getPhoneNum());
//			qamsAnswerQue.setQamsQue(qamsQue);
//			qamsAnswerQue.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			this.qamsAnswerQueDao.save(qamsAnswerQue);
//		} else {
//			qamsAnswerQue.setCreateTime(new Timestamp(System.currentTimeMillis()));
//			this.qamsAnswerQueDao.save(qamsAnswerQue);
//		}
//		// 答题
//		List<QamsQuestion> questions = this.qamsQuestionService.getListByQueId(queId);
//		if (null == questions || questions.size() == 0) {
//			throw new GHException("该问卷尚未创建问题");
//		}
//		this.qamsAnswerIssueService.saveAnswerIssue(qamsAnswerQue,loginUser,questions);
//		// 答案记录
//		String scorestr = null;
//		int score = this.qamsAnswerRecordService.saveAnswerRecord(qamsAnswerQue,loginUser,answers);
//		
//		QamsAnswerQue answerQue = this.qamsAnswerQueDao.findOne(qamsAnswerQue.getSid());
//		scorestr = score + "";
//		answerQue.setScore(scorestr);
//		String grade = this.qamsScoreService.getGradeByScore(scorestr);
//		answerQue.setGrade(grade);
//		this.qamsAnswerQueDao.save(answerQue);
//		
//		QamsAnswerQueResp qamsAnswerQueResp = this.getDetailById(qamsAnswerQue.getSid());
//		return qamsAnswerQueResp;
//	}

	/**
	 * 根据问卷ID和用户ID查询答卷
	 * @param queId
	 * @param loginUser
	 * @return
	 */
	private QamsAnswerQue getAnswerQueByUser(String queId,String loginUser) {
		return this.qamsAnswerQueDao.getAnswerQueByUser(queId,loginUser);
	}

	/**
	 * 调查问卷结果列表
	 * 
	 * @param spec
	 * @param pageable
	 * @return
	 */
	public QamsAnswerQueListResp list(Specification<QamsAnswerQue> spec, Pageable pageable) {
		Page<QamsAnswerQue> page = this.qamsAnswerQueDao.findAll(spec, pageable);
		QamsAnswerQueListResp listResp = new QamsAnswerQueListResp(page);
//		if (null != page) {
//			listResp.setTotal(page.getTotalElements());
//			if (null != page.getContent() && page.getContent().size() > 0) {
//				for (QamsAnswerQue entity : page.getContent()) {
//					QamsAnswerQueResp resp = new QamsAnswerQueResp(entity);
//					resp.setName(adminSdk.getAdmin(entity.getAuthorId()).getName());
//					resp.setTelephone(adminSdk.getAdmin(entity.getAuthorId()).getPhone());
//					listResp.getRows().add(resp);
//				}
//			}
//		}
		return listResp;
	}

	/**
	 * 根据答卷sid查询问卷调查详情
	 * 
	 * @param sid 答卷ID
	 * @param loginUser 用户ID
	 * @return
	 */
	@Transactional
	public QamsAnswerQueDeatilResp getDetailByQueId(String sid, String loginUser) {
		QamsAnswerQue qamsAnswerQue = this.qamsAnswerQueDao.findOne(sid);
		if (null == qamsAnswerQue) {
			throw new GHException("未知的答卷ID");
		}
		QamsAnswerQueDeatilResp deatilResp = new QamsAnswerQueDeatilResp(qamsAnswerQue);
		List<QueAndAnswer> list = new ArrayList<>();
		List<QamsQuestion> questions = this.qamsQuestionService.getListByQueId(qamsAnswerQue.getQamsQue().getSid());
		if (null != questions && questions.size() > 0) {
			for (QamsQuestion qamsQuestion : questions) {
				QueAndAnswer queAndAnswer = new QueAndAnswer();
				QamsAnswerIssue qamsAnswerIssue = this.qamsAnswerIssueService.getByIssue(qamsQuestion.getSid(), sid,loginUser);
				if (null != qamsAnswerIssue) {
					QamsAnswerRecord qamsAnswerRecord = this.qamsAnswerRecordService.getByIssue(qamsAnswerIssue.getSid());
					if (null != qamsAnswerRecord) {
						QamsAnswer an = this.qamsAnswerService.getById(qamsAnswerRecord.getQamsAnswer().getSid());
						if (null != qamsQuestion) {
							List<QamsAnswer> answers = this.qamsAnswerService.getAnswersByQid(qamsQuestion.getSid());
							List<Ans> ans = new ArrayList<>();
							for (QamsAnswer qamsAnswer : answers) {
								Ans answer = new Ans();
								answer.setAid(qamsAnswer.getSid());
								answer.setSn(qamsAnswer.getSn());
								answer.setText(qamsAnswer.getContent());
								answer.setScore(qamsAnswer.getScore());
								if (an.equals(qamsAnswer)) {
									answer.setChecked("checked");
								}
								ans.add(answer);
							}
							queAndAnswer.setId(qamsQuestion.getSid());
							queAndAnswer.setContent(qamsQuestion.getContent());
							queAndAnswer.setAnswers(ans);
							list.add(queAndAnswer);
						}
					}
				}
			}
		}

		deatilResp.setList(list);
		return deatilResp;
	}

	/**
	 * 根据sid查询答卷详情
	 * 
	 * @param sid 答卷ID
	 * @return
	 */
	@Transactional
	public QamsAnswerQueResp getDetailById(String sid) {
		QamsAnswerQue qamsAnswerQue = this.qamsAnswerQueDao.findOne(sid);
		if (null == qamsAnswerQue) {
			throw new GHException("未知的答卷ID");
		}
		QamsAnswerQueResp qamsAnswerQueResp = new QamsAnswerQueResp(qamsAnswerQue);
		return qamsAnswerQueResp;
	}

}
