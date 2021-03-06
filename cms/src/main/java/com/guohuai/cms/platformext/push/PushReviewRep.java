package com.guohuai.cms.platformext.push;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.guohuai.cms.component.web.parameter.validation.Enumerations;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@lombok.Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class PushReviewRep {
	@NotNull(message = "审批唯一键不能为空！")
	private String oid;
	
	@NotNull(message = "审批结果不能为空！")
	@Enumerations(values = {"pass","refused"}, message = "审批结果参数有误！")
	private String apprResult;
	
	@NotNull(message = "审核意见不能为空！")
	@Length(max = 200, message = "审核意见长度不能超过200（包含）！")
	private String remark;

}
