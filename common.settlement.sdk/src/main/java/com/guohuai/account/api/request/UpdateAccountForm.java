package com.guohuai.account.api.request;

import java.io.Serializable;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class UpdateAccountForm implements Serializable {
	
	private static final long serialVersionUID = -7698266127631256122L;
	
	@NotBlank
	private String oid;
	private String accountType;
	private String relationProduct;
	private String status;
	private String phone;
	private String remark;

}
