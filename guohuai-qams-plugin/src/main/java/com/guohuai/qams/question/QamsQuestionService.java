
package com.guohuai.qams.question;

import java.sql.Timestamp;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guohuai.basic.common.StringUtil;
import com.guohuai.basic.component.exception.GHException;
import com.guohuai.qams.answer.QamsAnswer;
//import com.guohuai.qams.answer.QamsAnswerForm;
import com.guohuai.qams.answer.QamsAnswerDao;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.que.QamsQueDao;
import com.guohuai.qams.que.QamsQueResp;
import com.guohuai.qams.que.QamsQueService;

@Service
@Transactional
public class QamsQuestionService {

	@Autowired
	private QamsQuestionDao qamsQuestionDao;
	@Autowired
	private QamsAnswerDao qamsAnswerDao;
	@Autowired
	private QamsQueService qamsQueService;
	@Autowired
	private QamsQueDao qamsQueDao;
	/**
	 * 根据问卷ID获取问题集合
	 * @param queId
	 * @return
	 */
	@Transactional
	public List<QamsQuestion> getListByQueId(String queId) {
		return this.qamsQuestionDao.getListByQueId(queId);
	}
	
	/**
	 * 新增问题
	 * @param queId
	 * @return
	 */
	public void add(QamsQuestionForm form){
		//问题
		QamsQuestion  it = new QamsQuestion();
		QamsQue qamsQue = this.qamsQueService.getQueById(form.getOid());
		int qnumber=0;
		if(null!=qamsQue.getNumber()){
			qnumber=qamsQue.getNumber();
		}else{
			qnumber=0;
		}
		int oldmin=0;
		int oldmax=0;
		if(null==form.getSid()||"".equals(form.getSid())){
		   it.setSid(StringUtil.uuid());
		}else{
			it.setSid(form.getSid());
			List<QamsAnswer> answers=qamsAnswerDao.getAnswersByQid(form.getSid());
			int min=-1;
			if (null != answers && answers.size() > 0) {
				for(int i=0;i<answers.size();i++){
					QamsAnswer answer = answers.get(i);
					if(Integer.parseInt(answer.getScore())<min || min==-1){
						min=Integer.parseInt(answer.getScore());
						oldmin = min;
					}
					if(Integer.parseInt(answer.getScore())>oldmax){
						oldmax=Integer.parseInt(answer.getScore());
					}
				}
			}
			qamsAnswerDao.delete(answers);
			qnumber--;
		}
		
		it.setQue(qamsQue);
		it.setCh_type(form.getChType());
		it.setContent(form.getIssue());
		it.setCreate_time(new Timestamp(System.currentTimeMillis()));
		it.setAdmin_id(form.getAid());
		QamsQuestion qamsQus=this.qamsQuestionDao.save(it);
		//答案
		List<QamsAnswer> answers = form.getAnswers();
		int min=-1;
		int max=0;
		if (null != answers && answers.size() > 0) {
			for(int i=0;i<answers.size();i++){
				QamsAnswer answer = answers.get(i);
					answer.setIssue(qamsQus);
					if(null==answer.getSn()){
						continue;
					}else{
						answer.setSn(answer.getSn());
						answer.setContent(answer.getContent());
						answer.setScore(answer.getScore());
						answer.setAdmin(form.getAid());
						this.qamsAnswerDao.save(answer);
						if(Integer.parseInt(answer.getScore())<min || min==-1){
							min=Integer.parseInt(answer.getScore());
						}
						if(Integer.parseInt(answer.getScore())>max){
							max=Integer.parseInt(answer.getScore());
						}
					}
					
			}
		}
		
		int number=qnumber+1;
		int minScore=Integer.parseInt(qamsQue.getMinScore())-oldmin+min;
		int maxScore=Integer.parseInt(qamsQue.getMaxScore())-oldmax+max;
		qamsQueDao.updateScore(qamsQue.getSid(), number, minScore+"", maxScore+"");
//		qamsQuestionDao.updateAllQuestion();
	}
	
	
	/**
	 * 排序
	 * @param form
	 */
	public void sort(SortQuestionForm form){
		List<Integer> indexs=form.getIndexs();
		List<String> sids=form.getSids();
		if(indexs.size()>0){
			for(int i=0;i<indexs.size();i++){
				String sid=sids.get(i);
				QamsQuestion que=this.qamsQuestionDao.findOne(sid);
				que.setQue_sort(indexs.get(i));
				this.qamsQuestionDao.save(que);
			} 
		}
	}
/**
 * 根据问题ID查看问题详情
 * @param sid
 * @return
 */
	public QamsQuestionResp getQuesById(String sid) {
		QamsQuestion qamsQuestion = this.qamsQuestionDao.findOne(sid);
		QamsQuestionResp resp = new QamsQuestionResp(qamsQuestion);
		return resp;
	}
	/**
	 * 删除问题
	 * @param form
	 */
	public void del(String qid){
		QamsQuestion  it = this.qamsQuestionDao.getOne(qid);
		QamsQue qamsQue = it.getQue();
		it.setQue(qamsQue);
		it.setStatus("INVALID");
		List<QamsAnswer> answers=qamsAnswerDao.getAnswersByQid(qid);
		int min=-1;
		int max=0;
		if (null != answers && answers.size() > 0) {
			for(int i=0;i<answers.size();i++){
				QamsAnswer answer = answers.get(i);
						if(Integer.parseInt(answer.getScore())<min || min==-1){
							min=Integer.parseInt(answer.getScore());
						}
						if(Integer.parseInt(answer.getScore())>max){
							max=Integer.parseInt(answer.getScore());
						}
					}
			}
		int minScore=Integer.parseInt(qamsQue.getMinScore())-min;
		int maxScore=Integer.parseInt(qamsQue.getMaxScore())-max;
		int number=qamsQue.getNumber()-1;
		qamsQue.setNumber(number);
		qamsQue.setMaxScore(maxScore+"");
		qamsQue.setMinScore(minScore+"");
		this.qamsQuestionDao.save(it);
	}
	
	
}