package com.example.finalproject.utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomDateDeserializer extends StdDeserializer<Date> {

	private static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

	public CustomDateDeserializer() {
		this(null);
	}

	public CustomDateDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Date deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
		String date = jsonParser.getText();
		
		try {
			return formatter.parse(date);
			} catch (ParseException e) {
			throw new RuntimeException(e);
			}
	}
	
	
	
}

	

