package com.guohuai.qams.question;

import org.springframework.data.domain.Page;

import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QamsQuestionListResp extends PageResp<QamsQuestionResp>{

	public QamsQuestionListResp() {
		super();
	}

	public QamsQuestionListResp(Page<QamsQuestion> qamsQuestion) {
		
		if (null != qamsQuestion ) {
			super.setTotal(qamsQuestion.getTotalElements());
			if (null != qamsQuestion.getContent() && qamsQuestion.getContent().size() > 0) {
				for (QamsQuestion entity : qamsQuestion.getContent() ) {
					super.getRows().add(new QamsQuestionResp(entity));
				}
			}
		}
	}

}
