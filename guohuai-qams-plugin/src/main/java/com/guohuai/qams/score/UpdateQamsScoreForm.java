package com.guohuai.qams.score;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateQamsScoreForm implements Serializable {

	private static final long serialVersionUID = -7228870596117718572L;
	
	private String sid;
	private String que;
	private String scoreGrade;
	private String minScore;
	private String maxScore;
	private String adminId;
	
	
}
