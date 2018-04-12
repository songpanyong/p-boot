package com.guohuai.qams.answerQue;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 答卷
 * 
 * @author William Zhang
 *
 */

@Data
public class QamsAnswerQueForm implements Serializable {

	private static final long serialVersionUID = -8956377645274378613L;

	// 问卷ID
	private String sid;

	private List<QamsAnswerQueForm.Questions> questions;

	@Data
	public static class Questions {
		//问题ID
		private String qid;
		//答案ID集合
		private List<String> answers;
	}
}
