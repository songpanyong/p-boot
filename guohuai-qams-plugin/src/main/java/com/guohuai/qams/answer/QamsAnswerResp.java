package com.guohuai.qams.answer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.question.QamsQuestion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false)
public class QamsAnswerResp extends BaseResp {
	
	public QamsAnswerResp(QamsAnswer qamsAnswer) {
		this.sid=qamsAnswer.getSid();
		this.content=qamsAnswer.getContent();
		this.sn=qamsAnswer.getSn();
		this.issue=qamsAnswer.getIssue();
		this.score=qamsAnswer.getScore();
		this.admin=qamsAnswer.getAdmin();
		this.status=qamsAnswer.getStatus();
	}

	private String sid;
	private String sn;
	private String content;
	private QamsQuestion issue;
	private String score;
	private String status;
	private String admin;

}
