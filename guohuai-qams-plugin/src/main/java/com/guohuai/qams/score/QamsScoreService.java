package com.guohuai.qams.score;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;

import com.guohuai.basic.component.exception.GHException;
import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.basic.component.ext.web.PageResp;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.que.QamsQueDao;

@Service
@Transactional
public class QamsScoreService {

	@Autowired
	private QamsScoreDao qamsScoreDao;
	@Autowired
	private QamsQueDao qamsQueDao;

	/**
	 * 根据分数获取风险等级
	 * 
	 * @param scorestr
	 * @return
	 */
	@Transactional
	public String getGradeByScore(String scorestr) {
		String grade = null;
		int score = Integer.parseInt(scorestr);
		List<QamsScore> scores = list();
		if (null != scores && scores.size() > 0) {
			for (QamsScore qamsScore : scores) {
				int minScore = Integer.parseInt(qamsScore.getMinScore());
				int maxScore = Integer.parseInt(qamsScore.getMaxScore());
				if (score >= minScore && score <= maxScore) {
					grade = qamsScore.getScoreGrade();
				}
			}
		}
		return grade;
	}

	@Transactional
	public List<QamsScore> list() {
		return this.qamsScoreDao.findAll();
	}

	@Transactional
	public PageResp<QamsScoreResp> list(Specification<QamsScore> spec, Pageable pageable) {
		final Page<QamsScore> cas = this.qamsScoreDao.findAll(spec, pageable);
		PageResp<QamsScoreResp> pagesRep = new PageResp<QamsScoreResp>();
		List<QamsScoreResp> rows = new ArrayList<QamsScoreResp>();
		for (QamsScore qamsScore : cas) {
			QamsScoreResp queryRep = new QamsScoreResp(qamsScore);
			rows.add(queryRep);
		}
		pagesRep.setRows(rows);
		pagesRep.setTotal(cas.getTotalElements());
		return pagesRep;
	}
	@Transactional
	public BaseResp save(String user, SaveQamsScoreForm form) {
		BaseResp response = new BaseResp();
		QamsQue que = this.qamsQueDao.findOne(form.getQue());
		List<QamsScore> qamsScores=this.qamsScoreDao.findAllByQueSid(que.getSid());
		if(qamsScores.size()>0){
			for(QamsScore qamsScore:qamsScores){
				this.qamsScoreDao.delete(qamsScore);
			}
		}
		ArrayList<QamsScore> list = new ArrayList();
			List<GradeScore> gradeScores=form.getGradeScores();
			for(GradeScore gradeScore:gradeScores){
				QamsScore qamsScore = new QamsScore();
				qamsScore.setQamsQue(que);
				qamsScore.setScoreGrade(gradeScore.getScoreGrade());
				qamsScore.setMinScore(gradeScore.getMinScore());
				qamsScore.setMaxScore(gradeScore.getMaxScore());
				qamsScore.setAdminId(user);
				list.add(qamsScore);
			}
		this.qamsScoreDao.save(list);
		return response;
	}
	@Transactional
	public BaseResp delete(String sid) {
		BaseResp response = new BaseResp();
		final QamsScore qamsScore = qamsScoreDao.findOne(sid);
		this.qamsScoreDao.delete(qamsScore);
		return response;
	}

	public QamsScoreResp read(String oid) {
		QamsScore qamsScore = qamsScoreDao.findOne(oid);
		QamsScoreResp queryRep = new QamsScoreResp(qamsScore);
		return queryRep;
	}

	public BaseResp update(String user,SaveQamsScoreForm form) {
		BaseResp response = new BaseResp();
	
			QamsQue que = this.qamsQueDao.findOne(form.getQue());
			if (que != null) {
			List<GradeScore> gradeScores=form.getGradeScores();
			ArrayList<QamsScore> list = new ArrayList();
			for(GradeScore gradeScore:gradeScores){
				QamsScore qamsScore = new QamsScore();
				qamsScore.setQamsQue(que);
				qamsScore.setScoreGrade(gradeScore.getScoreGrade());
				qamsScore.setMinScore(gradeScore.getMinScore());
				qamsScore.setMaxScore(gradeScore.getMaxScore());
				qamsScore.setAdminId(user);
				list.add(qamsScore);
			}
			this.qamsScoreDao.save(list);
		}
		return response;
	}
	public BaseResp update(String user,UpdateQamsScoreForm form) {
		BaseResp response = new BaseResp();
		QamsScore qamsScore = qamsScoreDao.findOne(form.getSid());
		if (qamsScore != null) {
			QamsQue que = this.qamsQueDao.findOne(form.getQue());
			qamsScore.setQamsQue(que);
			qamsScore.setScoreGrade(form.getScoreGrade());
			
				qamsScore.setMinScore(form.getMinScore());
				qamsScore.setMaxScore(form.getMaxScore());
			
			qamsScore.setAdminId(user);
			this.qamsScoreDao.saveAndFlush(qamsScore);
		}
		return response;
	}
}
