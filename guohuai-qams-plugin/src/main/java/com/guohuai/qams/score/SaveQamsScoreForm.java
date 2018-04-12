package com.guohuai.qams.score;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveQamsScoreForm implements Serializable {

	private static final long serialVersionUID = -7228870596117718572L;
	
	private String sid;
	private String que;
	private List<GradeScore> gradeScores;
	private String adminId;	
}

