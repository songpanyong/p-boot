package com.guohuai.qams.que;

import java.math.BigDecimal;
import java.util.List;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;

@Data
public class QamsQueResultResp extends BaseResp {
	
	public QamsQueResultResp(QamsQue qamsQue) {
		super();
		this.sid=qamsQue.getSid();
		this.name=qamsQue.getName();
	}

	//问卷id
	private String sid;
	//问卷名称
	private String name;
	//问题答案集合
	private List<QueAndAnswer> list;

	@Data
	public static class QueAndAnswer {
		
		//问题id
		private String id;
		//问题内容
		private String content;
		//问题类型
		private String chType;
		//答案集合
		private List<Answer> answers;
		
		@Data
		public static class Answer {
			
			//答案id
			private String aid;
			//答案序号
			private String sn;
			//答案内容
			private String text;
		
			//分数
			private String score;
			//答案票数
			private BigDecimal number = BigDecimal.ZERO;
			//答案百分比
			private BigDecimal percent = BigDecimal.ZERO;
		}
	}
}
