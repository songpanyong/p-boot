package com.guohuai.qams.answerQue;


import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.operate.api.AdminSdk;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answer.QamsAnswerService;
import com.guohuai.qams.answerIssue.QamsAnswerIssueService;
import com.guohuai.qams.answerRecord.QamsAnswerRecordService;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.que.QamsQueService;
import com.guohuai.qams.question.QamsQuestion;
import com.guohuai.qams.question.QamsQuestionService;
import com.guohuai.qams.score.QamsScoreService;

@Service
@Transactional
public class QamsAnswerQueRequireNewService {

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

	/**
	 * 保存答卷
	 * 
	 * @param queId
	 * @param answers
	 * @param loginUser
	 * @return 
	 */
	@Transactional(value = TxType.REQUIRES_NEW)
	public QamsAnswerQueResp saveAnswerQue(String queId, List<QamsAnswer> answers, String loginUser) {
		// 问卷
				QamsQue qamsQue = this.qamsQueService.getQueById(queId);
				if (null == qamsQue) {
					throw new GHException("未知的问卷ID");
				}
				//获取当前登录人信息      Todo  问卷库要和mimosa放到一起 不然此处会报错，为解佳兆业问题，此问题后续优化
				InvestorBaseAccountInfoEntity  investorInfo  =investorBaseAccountDao.findOne(loginUser);
				if (null == investorInfo) {
					throw new GHException("未知的投资人信息");
				}
				// 答卷
				QamsAnswerQue qamsAnswerQue = this.getAnswerQueByUser(queId,loginUser);
				if (qamsAnswerQue == null) {
					qamsAnswerQue = new QamsAnswerQue();
					qamsAnswerQue.setSid(StringUtil.uuid());
					qamsAnswerQue.setAuthorId(loginUser);
					qamsAnswerQue.setAuthorName(investorInfo.getRealName());
					qamsAnswerQue.setAuthorPhone(investorInfo.getPhoneNum());
					qamsAnswerQue.setQamsQue(qamsQue);
					qamsAnswerQue.setCreateTime(new Timestamp(System.currentTimeMillis()));
					this.qamsAnswerQueDao.save(qamsAnswerQue);
				} else {
					qamsAnswerQue.setCreateTime(new Timestamp(System.currentTimeMillis()));
					if(null != investorInfo.getPhoneNum()){
						qamsAnswerQue.setAuthorPhone(investorInfo.getPhoneNum());
					}
					this.qamsAnswerQueDao.save(qamsAnswerQue);
				}
				// 答题
				List<QamsQuestion> questions = this.qamsQuestionService.getListByQueId(queId);
				if (null == questions || questions.size() == 0) {
					throw new GHException("该问卷尚未创建问题");
				}
				this.qamsAnswerIssueService.saveAnswerIssue(qamsAnswerQue,loginUser,questions);
				// 答案记录
				String scorestr = null;
				int score = this.qamsAnswerRecordService.saveAnswerRecord(qamsAnswerQue,loginUser,answers);
				
				QamsAnswerQue answerQue = this.qamsAnswerQueDao.findOne(qamsAnswerQue.getSid());
				scorestr = score + "";
				answerQue.setScore(scorestr);
				String grade = this.qamsScoreService.getGradeByScore(scorestr);
				answerQue.setGrade(grade);
				this.qamsAnswerQueDao.save(answerQue);
				
				QamsAnswerQueResp qamsAnswerQueResp = this.getDetailById(qamsAnswerQue.getSid());
				return qamsAnswerQueResp;
	}
	
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
