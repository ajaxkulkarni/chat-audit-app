package com.audit.app.Common;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Tool {

	public static void main(String[] args) {
		DateTimeFormatter format = DateTimeFormat.forPattern("MM/dd");
		long val = new DateTime().getMillis();
		System.out.println(new DateTime(val).toString(format));
	}

}
