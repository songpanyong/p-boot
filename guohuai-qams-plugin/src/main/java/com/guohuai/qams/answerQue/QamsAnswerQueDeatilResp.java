package com.guohuai.qams.answerQue;

import java.sql.Timestamp;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;

@Data
public class QamsAnswerQueDeatilResp extends BaseResp {
	
	public QamsAnswerQueDeatilResp(QamsAnswerQue qamsAnswerQue) {
		super();
		this.sid = qamsAnswerQue.getSid();
		this.queName=qamsAnswerQue.getQamsQue().getName();
		this.createTime=qamsAnswerQue.getCreateTime();
	}

	//答卷ID
	private String sid;
	//问卷名称
	private String queName;
	//提交问卷时间
	private Timestamp createTime;
	
	//问题答案集合
	private List<QueAndAnswer> list;

	@Data
	public static class QueAndAnswer {
		
		//问题id
		private String id;
		//问题内容
		private String content;
		//答案集合
		private List<Ans> answers;
		
		@Data
		public static class Ans {
			
			//答案id
			private String aid;
			//答案序号
			private String sn;
			//答案内容
			private String text;
			//答案分数
			private String score;
			//答案是否选中
			private String checked = null;
		}
	}
}
