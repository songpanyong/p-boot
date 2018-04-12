package com.guohuai.qams.que;

import org.springframework.data.domain.Page;

import com.guohuai.basic.component.ext.web.PageResp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class QamsQueListResp extends PageResp<QamsQueResp>{

	public QamsQueListResp() {
		super();
	}

	public QamsQueListResp(Page<QamsQue> QamsQue) {
		
		if (null != QamsQue ) {
			super.setTotal(QamsQue.getTotalElements());
			if (null != QamsQue.getContent() && QamsQue.getContent().size() > 0) {
				for (QamsQue order : QamsQue.getContent() ) {
					super.getRows().add(new QamsQueResp(order));
				}
			}
		}
	}

}
