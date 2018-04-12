package com.guohuai.cms.component.util;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.guohuai.cms.component.exception.MoneyException;

public class BeanUtil {
	/**
	 * copy bean's properties
	 * @param dest
	 * @param orig
	 */
	public static void copy(Object dest,Object orig){
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new MoneyException(e.getMessage());
		}
	}
}

