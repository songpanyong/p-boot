package com.guohuai.tulip.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;

public class BeanUtil {

	/**
	 * 拷贝Bean属性且只拷贝指定属性
	 * 
	 * @param source
	 * @param target
	 * @param useProperties
	 * @throws Exception
	 * @author lupeng
	 * @modified 2015年4月13日 下午4:57:23
	 */
	public static void copyProperties(Object source, Object target, String... useProperties) {
		try {
			if (null == source || null == target) {
				return;
			}

			Field[] fields = source.getClass().getDeclaredFields();
			if (null != source.getClass().getSuperclass()) {
				fields = ArrayUtils.addAll(fields, source.getClass().getSuperclass().getDeclaredFields());
			}

			List<String> properties = new ArrayList<String>();
			for (Field field : fields) {
				field.setAccessible(true);

				if (ArrayUtils.isNotEmpty(useProperties) && !ArrayUtils.contains(useProperties, field.getName())) {
					properties.add(field.getName());
				}
			}

			BeanUtils.copyProperties(source, target, properties.toArray(new String[0]));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
