package com.guohuai.operate.api;

import java.sql.Date;

public class Test {

	public static void main(String[] args) {

		printf("2016-09-16");
		printf("2016-12-31");

	}

	public static void printf(String date) {
		System.out.println(date + "    " + Date.valueOf(date).getTime());
	}

}
