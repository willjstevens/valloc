/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.object.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.valloc.Constants;

import java.io.IOException;
import java.util.Date;

//import org.codehaus.jackson.JsonGenerator;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.JsonSerializer;
//import org.codehaus.jackson.map.SerializerProvider;

/**
 * @author wstevens
 */
public class JsonPrintedDateSerializer extends JsonSerializer<Date>
{

    @Override
    public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        String formattedDate = Constants.PRINTED_TIMESTAMP_FORMAT.format(date);
        jgen.writeString(formattedDate);
    }
}
