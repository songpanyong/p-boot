package com.guohuai.basic.component.ext.web.parameter;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomerJavaDateEditor extends PropertyEditorSupport {

	private static CustomerJavaDateEditor editor = new CustomerJavaDateEditor();

	public static CustomerJavaDateEditor getInstance() {
		return editor;
	}

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (null == text || text.trim().equals("")) {
			super.setValue(null);
			return;
		}

		try {
			Date value = this.df.parse(text);
			super.setValue(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	public String getAsText() {
		Date value = (Date) super.getValue();
		if (null == value) {
			return null;
		}

		return this.df.format(value);

	}

}
