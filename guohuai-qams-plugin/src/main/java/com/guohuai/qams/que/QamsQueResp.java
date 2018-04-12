
package com.guohuai.qams.que;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;
import com.guohuai.operate.api.objs.admin.AdminObj;
import com.guohuai.qams.que.QamsQue;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
public class QamsQueResp extends BaseResp {
	
	public QamsQueResp(QamsQue queScore) {
		super();
		this.sid=queScore.getSid();
		this.name=queScore.getName();
		this.type=queScore.getType();
		this.sn=queScore.getSn();
		this.createTime=queScore.getCreate_time();
		this.status=queScore.getStatus();
		this.adminId = queScore.getAdmin_id();
		this.minScore=queScore.getMinScore();
		this.maxScore=queScore.getMaxScore();
		this.number=queScore.getNumber();
	}

	private String sid;
	private String name;
	private String type;
	private String sn;
	private Timestamp createTime;
	private String status;
	private Integer number;
	private String minScore;
	private String maxScore;
	private String adminId;
}
