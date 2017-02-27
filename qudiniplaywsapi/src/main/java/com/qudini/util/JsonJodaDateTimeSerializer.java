package com.qudini.util;

import java.io.IOException;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class JsonJodaDateTimeSerializer extends StdSerializer<DateTime> {

	private static final long serialVersionUID = 1L;
	private static DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");

	public JsonJodaDateTimeSerializer(Class<DateTime> t) {
		super(t);
	}

	public JsonJodaDateTimeSerializer() {
		this(null);
	}

	@Override
	public void serialize(DateTime val, JsonGenerator gen, SerializerProvider arg2) throws IOException {
		gen.writeString(formatter.print(val));

	}

	/*
	 * @Override public void serialize(DateTime val, JsonGenerator gen,
	 * SerializerProvider arg2) throws IOException, JsonProcessingException {
	 * gen.writeString(formatter.print(val));
	 * 
	 * }
	 */

}