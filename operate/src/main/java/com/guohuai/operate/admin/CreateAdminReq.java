package com.guohuai.operate.admin;

import java.sql.Date;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.guohuai.operate.component.web.parameter.validation.Enumerations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAdminReq {

	@NotBlank
	@Length(max = 30, message = "账号长度不能超过30（包含）！")
	private String account;
	
	@NotBlank
	private String password;
	
	@NotBlank
	@Length(max = 30, message = "姓名长度不能超过30（包含）！")
	private String name;
	
	@Email
	private String email;
	
	@Length(max = 30, message = "联系方式长度不能超过30（包含）！")
	private String phone;
	
	@NotBlank
	@Enumerations(values = { "USERREGIST", "PLATFORM" })
	private String resources;
	
	@NotBlank
	private String system;
	
	@Length(max = 200, message = "备注内容长度不能超过200（包含）！")
	private String comment;
	
	private Date validTime;
	
	private String[] roles;
}
