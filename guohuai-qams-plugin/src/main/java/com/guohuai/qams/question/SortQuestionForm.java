package com.guohuai.qams.question;

import java.io.Serializable;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortQuestionForm implements Serializable {

	private static final long serialVersionUID = -7228870596117718572L;
	
	private List<Integer> indexs;
	private List<String> sids;
	
	
}
