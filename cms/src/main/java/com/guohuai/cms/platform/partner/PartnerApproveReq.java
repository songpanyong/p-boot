package com.guohuai.cms.platform.partner;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.guohuai.cms.component.web.parameter.validation.Enumerations;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PartnerApproveReq {
	
	@NotBlank(message = "Banner不能为空！")
	private String oid;
	
	@NotBlank(message = "审核结果不能为空！")	
	@Enumerations(values = {"pass","refused"}, message = "审核结果参数有误！")
	private String approveStatus;
	
	@NotBlank(message = "审核意见不能为空！")
	@Length(max = 250, message = "审核意见长度不能超过250（包含）！")
	private String remark;
}
