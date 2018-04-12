package com.guohuai.qams.answerIssue;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.qams.answerQue.QamsAnswerQue;
import com.guohuai.qams.question.QamsQuestion;

@Service
@Transactional
public class QamsAnswerIssueService {

	@Autowired
	private QamsAnswerIssueDao qamsAnswerIssueDao;

	/**
	 * 保存答题
	 * @param qamsAnswerIssue
	 */
	@Transactional
	public void save(QamsAnswerIssue qamsAnswerIssue) {
		this.qamsAnswerIssueDao.save(qamsAnswerIssue);
	}

	
	/**
	 * 根据问题ID,答卷ID,用户ID获取答题
	 * @param issueId 问题ID
	 * @param answerQueId 答卷ID
	 * @param loginUser 用户ID
	 * @return
	 */
	@Transactional
	public QamsAnswerIssue getByIssue(String issueId,String answerQueId, String loginUser) {
		return this.qamsAnswerIssueDao.getByIssue(issueId,answerQueId,loginUser);
	}

	/**
	 * 根据问题ID获取答题
	 * @param sid
	 * @return
	 */
	public QamsAnswerIssue getByIssueId(String issueId) {
		return this.qamsAnswerIssueDao.getByIssueId(issueId);
	}

	/**
	 * 保存答题
	 * @param qamsAnswerQue 答卷
	 * @param loginUser 用户ID
	 * @param questions 问题集合
	 */
	public void saveAnswerIssue(QamsAnswerQue qamsAnswerQue, String loginUser, List<QamsQuestion> questions) {
		List<QamsAnswerIssue> answerIssues = this.qamsAnswerIssueDao.getAnswerIssueByQid(qamsAnswerQue.getSid(),loginUser);
		if (null != answerIssues && answerIssues.size() > 0) {
			for (QamsAnswerIssue answerIssue : answerIssues) {
				this.qamsAnswerIssueDao.delete(answerIssue);
			}
			for (QamsQuestion qamsQuestion : questions) {
				QamsAnswerIssue qamsAnswerIssue = new QamsAnswerIssue();
				qamsAnswerIssue.setSid(StringUtil.uuid());
				qamsAnswerIssue.setQamsQuestion(qamsQuestion);
				qamsAnswerIssue.setAnswerQue(qamsAnswerQue);
				qamsAnswerIssue.setAuthorId(loginUser);
				this.qamsAnswerIssueDao.save(qamsAnswerIssue);
			}
//			if (questions.size() >= answerIssues.size()) {
//				for (QamsQuestion question : questions) {
//					QamsAnswerIssue issue = this.getByIssue(question.getSid(), qamsAnswerQue.getSid(), loginUser);
//					if (null == issue) {
//						issue = new QamsAnswerIssue();
//						issue.setSid(StringUtil.uuid());
//						issue.setQamsQuestion(question);
//						issue.setAnswerQue(qamsAnswerQue);
//						issue.setAuthorId(loginUser);
//						this.qamsAnswerIssueDao.save(issue);
//					}
//				}
//			} 
		} else {
			for (QamsQuestion qamsQuestion : questions) {
				QamsAnswerIssue qamsAnswerIssue = new QamsAnswerIssue();
				qamsAnswerIssue.setSid(StringUtil.uuid());
				qamsAnswerIssue.setQamsQuestion(qamsQuestion);
				qamsAnswerIssue.setAnswerQue(qamsAnswerQue);
				qamsAnswerIssue.setAuthorId(loginUser);
				this.qamsAnswerIssueDao.save(qamsAnswerIssue);
			}
		}
			
	}
}
