package com.guohuai.basic.component.ext.web.parameter;

import java.beans.PropertyEditorSupport;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CustomerSqlDateEditor extends PropertyEditorSupport {

	private static CustomerSqlDateEditor editor = new CustomerSqlDateEditor();

	public static CustomerSqlDateEditor getInstance() {
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
			Date value = new Date(this.df.parse(text).getTime());
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
