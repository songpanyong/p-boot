package com.guohuai.mimosa.api.ext;

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
/*		String tsStr = in.nextString();
		if(tsStr!=null){
			Timestamp ts = new Timestamp(System.currentTimeMillis());   
	        try {   
	            ts = Timestamp.valueOf(tsStr);   
	            return ts;
	        } catch (Exception e) {   
	            e.printStackTrace();   
	        }  
		}*/
		
		long l=in.nextLong();
		if(l!=0L){
			return new Timestamp(l);
		}
/*		String str=in.nextString();
		if(str!=null){
			return str;
		}*/
		return null;
	}
}

