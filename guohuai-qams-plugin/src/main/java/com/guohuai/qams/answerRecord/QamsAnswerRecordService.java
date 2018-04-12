package com.guohuai.qams.answerRecord;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answerIssue.QamsAnswerIssue;
import com.guohuai.qams.answerIssue.QamsAnswerIssueService;
import com.guohuai.qams.answerQue.QamsAnswerQue;

@Service
@Transactional
public class QamsAnswerRecordService {

	@Autowired 
	private QamsAnswerRecordDao qamsAnswerRecordDao;
	@Autowired
	private QamsAnswerIssueService qamsAnswerIssueService;

	/**
	 * 保存答案记录
	 * @param qamsAnswerRecord
	 */
	@Transactional
	public void save(QamsAnswerRecord qamsAnswerRecord) {
		this.qamsAnswerRecordDao.save(qamsAnswerRecord);
	}

	/**
	 * 查询问题的答案总数
	 * @param sid 答题ID
	 * @return
	 */
	@Transactional
	public BigDecimal getTotal(String sid) {
		return this.qamsAnswerRecordDao.getTotal(sid);
	}

	/**
	 * 查询答案的个数
	 * @param qid 答题ID
	 * @param sid 答案ID
	 * @return
	 */
	@Transactional
	public BigDecimal getNumber(String qid, String sid) {
		return this.qamsAnswerRecordDao.getNumber(qid,sid);
	}

	/**
	 * 根据答题ID查询答案记录
	 * @param sid 答题ID
	 * @return
	 */
	@Transactional
	public QamsAnswerRecord getByIssue(String sid) {
		return this.qamsAnswerRecordDao.getByIssue(sid);
	}

	/**
	 * 保存答案记录
	 * @param qamsAnswerQue
	 * @param loginUser
	 * @param answers
	 */
	public int saveAnswerRecord(QamsAnswerQue qamsAnswerQue, String loginUser, List<QamsAnswer> answers) {
		int score = 0;
		if (null != answers && answers.size() > 0) {
			for (QamsAnswer qamsAnswer : answers) {
				QamsAnswerIssue answerIssue = this.qamsAnswerIssueService.getByIssue(qamsAnswer.getIssue().getSid(),qamsAnswerQue.getSid(),loginUser);
				QamsAnswerRecord record = this.qamsAnswerRecordDao.getByIssue(answerIssue.getSid());
				if (null != record) {
					this.qamsAnswerRecordDao.delete(record);
				}
				QamsAnswerRecord qamsAnswerRecord = new QamsAnswerRecord();
				qamsAnswerRecord.setSid(StringUtil.uuid());
				qamsAnswerRecord.setAuthor(loginUser);
				qamsAnswerRecord.setContent(qamsAnswer.getContent());
				qamsAnswerRecord.setQamsAnswer(qamsAnswer);
				qamsAnswerRecord.setQamsAnswerIssue(answerIssue);
				score += Integer.parseInt(qamsAnswer.getScore());
				this.qamsAnswerRecordDao.save(qamsAnswerRecord);
			}
		}
		return score;
	}

}
