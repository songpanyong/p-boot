package com.guohuai.operate.role;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRoleReq {

	@NotBlank
	private String oid;
	@NotBlank
	private String name;
	@NotBlank
	private String systemOid;

}
