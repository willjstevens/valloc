package com.valloc;

import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class Util
{
	private static final Logger logger = LogManager.manager().newLogger(Util.class, Category.UTILITY);
	private static final DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd-HH:mm:ss:SSS");
	
	private Util() {}
	
	public static Date now() {
		return new Date();
	}
	
	public static Date standardStringToDate(String dateString) {
		Date parsedDate = null;
		try {
			parsedDate = dateFormatter.parse(dateString);
		} catch (ParseException e) {
			logger.error("Parse error when parsing \"%s\" to date.", e, dateString);
		}
		return parsedDate;
	}
	
	public static String replaceTokenWithInt(String string, String token, int value) {
		String intStr = value + "";
		return string.replace(token, intStr);
	}


	public static void throwNpe(String msg) {
		throw new NullPointerException(msg);
	}

    public static String[] concat(String[]... stringArrays) {
        int size = 0;
        for (String[] array : stringArrays) {
            size += array.length;
        }
        String[] finalArray = new String[size];
        int currLocation = 0;
        for (String[] array : stringArrays) {
            int arraySize = array.length;
            System.arraycopy(array, 0, finalArray, currLocation, arraySize);
            currLocation += arraySize;
        }

        return finalArray;
    }

	public static String safeTrim(String string, int limit) {
		String returnValue = null;
		Optional<String> optionalString = Optional.ofNullable(string);
		if (optionalString.isPresent()) {
			String val = optionalString.get();
			int valLength = val.length();
			int size = valLength < limit ? valLength : limit;
			returnValue = optionalString.get().substring(0, size);
		}
		return returnValue;
	}
}
