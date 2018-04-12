package com.guohuai.operate.admin;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordReq {

	@NotBlank
	private String originalPassword;
	
	@NotBlank
	private String newPassword;
	
}
