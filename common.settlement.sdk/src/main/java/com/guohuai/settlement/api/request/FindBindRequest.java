package com.guohuai.settlement.api.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class FindBindRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -2960601088552627179L;
	
	private String userOid;
	
}
