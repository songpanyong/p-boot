package com.guohuai.qams.answerQue;

import org.springframework.data.domain.Page;

import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QamsAnswerQueListResp extends PageResp<QamsAnswerQueResp>{

	public QamsAnswerQueListResp() {
		super();
	}

	public QamsAnswerQueListResp(Page<QamsAnswerQue> qamsAnswerQue) {
		
		if (null != qamsAnswerQue ) {
			super.setTotal(qamsAnswerQue.getTotalElements());
			if (null != qamsAnswerQue.getContent() && qamsAnswerQue.getContent().size() > 0) {
				for (QamsAnswerQue entity : qamsAnswerQue.getContent() ) {
					super.getRows().add(new QamsAnswerQueResp(entity));
				}
			}
		}
	}

}
