package com.guohuai.points.response;

import java.util.List;

import lombok.Data;

import com.guohuai.points.entity.AccountTransEntity;

@Data
public class AccountTransRes {

	private List<AccountTransEntity> rows;
	private int page;
	private int row;
	private int totalPage;
	private long total;
}
