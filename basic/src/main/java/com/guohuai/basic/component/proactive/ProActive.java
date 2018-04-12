package com.guohuai.basic.component.proactive;

import lombok.Data;

public abstract interface ProActive {

	public abstract Integer priority();

	public static abstract interface Execution<T extends ProActive, R extends Object> {
		public abstract R execute(T t);
	}

	@Data
	public static class Result<R> {
		private String name;
		private Class<?> clazz;
		private R result;
	}

}
