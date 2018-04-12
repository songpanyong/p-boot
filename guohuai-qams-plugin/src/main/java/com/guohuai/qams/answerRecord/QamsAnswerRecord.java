package com.guohuai.qams.answerRecord;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.answerIssue.QamsAnswerIssue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答案记录
 * @author William Zhang
 *
 */
@Entity
@Table(name = "T_QAMS_ANSWER_RECORD")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsAnswerRecord implements Serializable{
	
	private static final long serialVersionUID = -1582547738910151544L;

	@Id
	private String sid;
	
	//答题者
	private String author;
	
	//答案
	private String content;
	
	//关联答案
	@ManyToOne
	@JoinColumn(name = "answer_id", referencedColumnName = "sid")
	private QamsAnswer qamsAnswer;
	
	//关联答题
	@ManyToOne
	@JoinColumn(name = "an_issue_id", referencedColumnName = "sid")
	private QamsAnswerIssue qamsAnswerIssue;
	
}
