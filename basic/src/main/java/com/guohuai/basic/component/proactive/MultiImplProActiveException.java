package com.guohuai.basic.component.proactive;

import java.util.Arrays;

public class MultiImplProActiveException extends RuntimeException {

	private static final long serialVersionUID = 5088618548288465969L;

	private Class<?> clazz;
	private Class<?>[] impls;

	private String message;

	public MultiImplProActiveException(Class<?> clazz, Class<?>[] impls) {
		this.clazz = clazz;
		this.impls = impls;
		String[] implsName = new String[this.impls.length];
		for (int i = 0; i < impls.length; i++) {
			implsName[i] = impls[i].getName();
		}
		this.message = String.format("The interface [%s] marked by [%s], but there are many implements by %s", this.clazz.getName(), SingleProActive.class.getName(), Arrays.toString(implsName));
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
