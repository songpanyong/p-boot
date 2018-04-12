package com.guohuai.qams.question;

import java.sql.Timestamp;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.operate.api.objs.admin.AdminObj;
import com.guohuai.qams.answer.QamsAnswer;
import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
public class QamsQuestionResp extends BaseResp {
	
	public QamsQuestionResp(QamsQuestion question) {
		super();
		this.sid=question.getSid();
		this.name=question.getQue();
		this.type=question.getCh_type();
		this.createTime=question.getCreate_time();
		this.status=question.getStatus();
		this.issue=question.getContent();
	}

	private String sid;
	private QamsQue name;
	private String type;
	private String sn;
	private Timestamp createTime;
	private String status;
	private String number;
	private String totalScore;
	private String admin;
	private String issue;

}
