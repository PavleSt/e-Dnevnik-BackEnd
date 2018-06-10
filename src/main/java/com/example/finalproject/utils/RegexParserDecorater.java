package com.example.finalproject.utils;

import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.format.Parser;

public class RegexParserDecorater<T> implements Parser<T> {

	private final Parser<T> parser;

	private final Pattern regexPattern;

	public RegexParserDecorater(Parser<T> parser, String regex) {
		super();
		this.parser = parser;
		this.regexPattern = Pattern.compile(regex);
	}

	public T parse(String text, Locale locale) throws ParseException {
		if (!regexPattern.matcher(text).matches()) {
			throw new IllegalArgumentException("Text does not match regex: " + text);
		}
		return parser.parse(text, locale);
	}
}
