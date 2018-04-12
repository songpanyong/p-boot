package com.guohuai.qams.answerQue;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QamsAnswerQueResp extends BaseResp {
	
	public QamsAnswerQueResp(QamsAnswerQue qamsAnswerQue) {
		super();
		this.qid = qamsAnswerQue.getQamsQue().getSid();
		this.sid = qamsAnswerQue.getSid();
		this.name = qamsAnswerQue.getAuthorName();
		this.telephone = qamsAnswerQue.getAuthorPhone();
		this.queName=qamsAnswerQue.getQamsQue().getName();
		this.createTime=qamsAnswerQue.getCreateTime();
		this.score=qamsAnswerQue.getScore();
		this.grade=qamsAnswerQue.getGrade();
	}

	//问卷ID
	private String qid;
	//答卷ID
	private String sid;
	//参与者姓名
	private String name;
	//参与者电话
	private String telephone;
	//问卷名称
	private String queName;
	//提交问卷时间
	private Timestamp createTime;
	//参与者评分
	private String score;
	//风险等级
	private String grade;

}
