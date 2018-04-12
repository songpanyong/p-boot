package com.guohuai.qams.answer;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class QamsAnswerService {

	@Autowired
	private QamsAnswerDao qamsAnswerDao;

	/**
	 * 根据问题ID查询答案
	 * @param qid
	 * @return
	 */
	@Transactional
	public List<QamsAnswer> getAnswersByQid(String qid) {
		return this.qamsAnswerDao.getAnswersByQid(qid);
	}

	/**
	 * 根据答案ID查询答案
	 * @param sid
	 * @return
	 */
	@Transactional
	public QamsAnswer getById(String sid) {
		return this.qamsAnswerDao.findOne(sid);
	}
	
	

}
