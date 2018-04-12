package com.guohuai.basic.component.ext.web.parameter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class JavaDateJsonDeserializer extends JsonDeserializer<Date> {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		if (null == arg0.getText() || arg0.getText().toString().trim().equals("")) {
			return null;
		}
		try {
			return this.df.parse(arg0.getText());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

}
