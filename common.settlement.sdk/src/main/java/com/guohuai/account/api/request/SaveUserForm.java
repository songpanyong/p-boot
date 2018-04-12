package com.guohuai.account.api.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class SaveUserForm implements Serializable {
	
	private static final long serialVersionUID = 2455241176909959404L;
	
	private String oid;
	@NotBlank
	private String userType;
	@NotBlank
	private String name;
	private String phone;
	private String remark;

}
