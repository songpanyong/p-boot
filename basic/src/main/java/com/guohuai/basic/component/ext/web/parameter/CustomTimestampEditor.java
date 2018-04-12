package com.guohuai.basic.component.ext.web.parameter;

import java.beans.PropertyEditorSupport;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomTimestampEditor extends PropertyEditorSupport {

	private static CustomTimestampEditor editor = new CustomTimestampEditor();

	public static CustomTimestampEditor getInstance() {
		return editor;
	}

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void setAsText(String text) throws IllegalArgumentException {

		if (null == text || text.trim().equals("")) {
			super.setValue(null);
			return;
		}

		try {
			Timestamp value = new Timestamp(this.df.parse(text).getTime());
			super.setValue(value);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}

	}

	@Override
	public String getAsText() {
		Timestamp value = (Timestamp) super.getValue();
		if (null == value) {
			return null;
		}

		return this.df.format(new Date(value.getTime()));

	}

}
