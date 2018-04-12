package com.guohuai.qams.answerIssue;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.guohuai.qams.answerQue.QamsAnswerQue;
import com.guohuai.qams.question.QamsQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 答题
 * @author William Zhang
 *
 */
@Entity
@Table(name = "T_QAMS_ANSWER_ISSUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QamsAnswerIssue implements Serializable{
	
	private static final long serialVersionUID = 1454916163436157765L;

	@Id
	private String sid;
	
	//关联问题
	@ManyToOne
	@JoinColumn(name = "issue_id", referencedColumnName = "sid")
	private QamsQuestion qamsQuestion;
	
	//关联答卷
	@ManyToOne
	@JoinColumn(name = "que_id", referencedColumnName = "sid")
	private QamsAnswerQue answerQue;
	
	//答题者
	@Column(name = "author_id")
	private String authorId;
}
