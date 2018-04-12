package com.guohuai.basic.component.ext.web.parameter;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TimestampJsonSerializer extends JsonSerializer<Timestamp> {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void serialize(Timestamp arg0, JsonGenerator arg1, SerializerProvider arg2) throws IOException, JsonProcessingException {
		if (null != arg0) {
			arg1.writeString(this.df.format(new Date(arg0.getTime())));
		}
	}

}
