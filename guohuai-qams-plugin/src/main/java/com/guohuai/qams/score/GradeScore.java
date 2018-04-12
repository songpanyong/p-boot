package com.guohuai.qams.score;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.guohuai.qams.que.QamsQue;
import com.guohuai.qams.score.QamsScore.QamsScoreBuilder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradeScore {
	private String scoreGrade;
	private String minScore;
	private String maxScore;
} 
