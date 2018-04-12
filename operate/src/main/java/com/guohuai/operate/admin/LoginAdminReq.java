package com.guohuai.operate.admin;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAdminReq {
	
	@NotBlank
	private String account;
	@NotBlank
	private String password;
	@NotBlank
	private String system;    		//系统
	
}
