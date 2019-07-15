/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author wstevens
 */
public class JsonPrintedDateDeserializer extends JsonDeserializer<Date>
{
    private static final Logger logger = LogManager.manager().newLogger(JsonPrintedDateDeserializer.class, Category.UTILITY);

    @Override
    public Date deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String dateString = jp.getText();
        Date postTimestamp = null;
        try {
            postTimestamp = Constants.PRINTED_TIMESTAMP_FORMAT.parse(dateString);
        } catch (ParseException e) {
            logger.error("Problem while trying to parse string %s into a date object.", e, dateString);
        }
        return postTimestamp;
    }
}
