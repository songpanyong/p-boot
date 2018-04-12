package com.guohuai.basic.component.ext.web.parameter;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JavaDateJsonSerializer extends JsonSerializer<Date> {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Date arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		if (null != arg0) {
			arg1.writeString(this.df.format(arg0));
		}
	}

}
