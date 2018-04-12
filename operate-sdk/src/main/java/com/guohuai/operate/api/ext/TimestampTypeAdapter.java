package com.guohuai.operate.api.ext;

import java.io.IOException;
import java.sql.Timestamp;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class TimestampTypeAdapter extends TypeAdapter<Timestamp> {

	@Override
	public void write(JsonWriter out, Timestamp value) throws IOException {
		out.value(value.getTime());
	}

	@Override
	public Timestamp read(JsonReader in) throws IOException {

		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		long l = in.nextLong();
		return new Timestamp(l);
	}
}
