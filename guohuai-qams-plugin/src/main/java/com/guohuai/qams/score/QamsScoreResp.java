package com.guohuai.qams.score;

import java.util.Date;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.qams.que.QamsQue;

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
public class QamsScoreResp extends BaseResp {
	
	public QamsScoreResp(QamsScore qamsScore) {
		this.sid=qamsScore.getSid();
		this.name=qamsScore.getQamsQue().getName();
		this.adminId=qamsScore.getAdminId();
		this.scoreGrade=qamsScore.getScoreGrade();
		this.score=qamsScore.getMinScore()+"~"+qamsScore.getMaxScore();
		this.createTime=qamsScore.getCreateTime();
		this.minScore=qamsScore.getMinScore();
		this.maxScore=qamsScore.getMaxScore();
	}

	private String sid;
	private String name;
	private String scoreGrade;
	private String score;
	private Date createTime;
	private String adminId;
	private String minScore;
	private String maxScore;

}
