
package com.guohuai.qams.question;

import java.io.Serializable;
import java.util.ArrayList;

import com.guohuai.qams.answer.QamsAnswer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QamsQuestionForm implements Serializable {

	private static final long serialVersionUID = -7228870596117718572L;
	
	//问卷ID
	private String oid;
	//管理员ID
	private String aid;
	//问题内容
	private String issue;
	//问题类型
	private String chType;
	//答案
	private ArrayList<QamsAnswer> answers;
	private String sid;
	

	
}

