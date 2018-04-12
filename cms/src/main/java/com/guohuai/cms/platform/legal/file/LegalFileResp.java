package com.guohuai.cms.platform.legal.file;

import java.sql.Timestamp;

import com.guohuai.basic.component.ext.web.BaseResp;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LegalFileResp extends BaseResp {
	
	LegalFileResp (LegalFileEntity en){
		this.oid = en.getOid();
		this.typeOid = en.getType().getOid();
		this.typeCode = en.getType().getCode();
		this.typeName = en.getType().getName();
		this.name = en.getName();
		this.fileUrl = en.getFileUrl();
		this.status = en.getStatus();
		this.statusDisp = statusEn2Ch(en.getStatus());
		this.operator = en.getOperator();
		this.updateTime = en.getUpdateTime();
		this.createTime = en.getCreateTime();
		this.uploadTime = en.getUploadTime();
	}
	
	private String oid;
	private String typeOid;	// 类型Oid
	private String typeCode;	// 类型Code
	private String typeName;	// 类型名称
	private String name;// 文件名称
	private String fileUrl;// 文件地址
	private String status;// 状态
	private String statusDisp;// 状态
	private String operator;//操作人
	private Timestamp uploadTime;//上传时间
	private Timestamp updateTime;
	private Timestamp createTime;

	private String statusEn2Ch(String status) {
		if(status.equals(LegalFileEntity.LEGAL_STATUS_disabled)){
			return "已禁用";
		}else if(status.equals(LegalFileEntity.LEGAL_STATUS_enabled)){
			return "已启用";
		}
		return status;
	}
}
