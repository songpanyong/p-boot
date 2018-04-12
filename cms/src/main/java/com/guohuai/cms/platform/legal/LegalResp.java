package com.guohuai.cms.platform.legal;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LegalResp extends BaseResp {
	
	LegalResp (LegalEntity en){
		this.oid = en.getOid();
		this.code = en.getCode();
		this.name = en.getName();
		this.status = en.getStatus();
		this.statusDisp = statusEn2Ch(en.getStatus());
		this.operator = en.getOperator();
		this.updateTime = en.getUpdateTime();
		this.createTime = en.getCreateTime();
	}
	
	private String oid;
	private String code;
	private String name;// 名称
	private String status;// 状态
	private String statusDisp;// 状态
	private String operator;//操作人
	private Timestamp updateTime;
	private Timestamp createTime;
	
	private int fileNum = 0;//文件数量

	private String statusEn2Ch(String status) {
		if(status.equals(LegalEntity.LEGAL_STATUS_disabled)){
			return "已禁用";
		}else if(status.equals(LegalEntity.LEGAL_STATUS_enabled)){
			return "已启用";
		}
		return status;
	}
}
