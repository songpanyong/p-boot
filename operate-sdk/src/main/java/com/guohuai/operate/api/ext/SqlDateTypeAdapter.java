package com.guohuai.operate.api.ext;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class SqlDateTypeAdapter extends TypeAdapter<Date> {

	@Override
	public void write(JsonWriter out, Date value) throws IOException {
		out.value(value.getTime());
	}

	private DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Date read(JsonReader in) throws IOException {

		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		String s = in.nextString();
		try {
			return new Date(this.format.parse(s).getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}
