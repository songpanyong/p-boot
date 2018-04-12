package com.guohuai.basic.component.ext.web.parameter;

import java.io.IOException;
import java.sql.Timestamp;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class TimestampJsonDeserializer extends JsonDeserializer<Timestamp> {

	@Override
	public Timestamp deserialize(JsonParser arg0, DeserializationContext arg1) throws IOException, JsonProcessingException {
		if (null == arg0.getText() || arg0.getText().toString().trim().equals("")) {
			return null;
		}
		return Timestamp.valueOf(arg0.getText());
	}

}
