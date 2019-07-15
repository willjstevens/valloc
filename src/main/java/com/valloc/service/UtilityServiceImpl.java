/*
 * Property of Will Stevens
 * All rights reserved.
 */
package com.valloc.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.valloc.Category;
import com.valloc.Constants;
import com.valloc.log.LogManager;
import com.valloc.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.PatternSyntaxException;

/**
 * 
 *
 * @author wstevens
 */
@Service
public class UtilityServiceImpl implements UtilityService
{
	private static final Logger logger = LogManager.manager().newLogger(UtilityServiceImpl.class, Category.UTILITY);
	private static final String DEFAULT_URL_ENCODING = "UTF-8";
	@Autowired private ConfigurationService configurationService;
	private Properties emailProps = new Properties();
	private Session emailSession;
	private String senderEmail;

	@Override
	public void init() {
		initEmailSending();

        // default to CST until a user preferences page is created
        Constants.CONVERSATION_TS_FORMAT.setTimeZone(TimeZone.getTimeZone("CST6CDT"));
	}

	@Override
	public void initEmailSending() {
		String emailConnectionString = configurationService.getEmailConnectionString();
		if (logger.isInfo()) {
			logger.info("Initializing email sending with connection string \"%s\".", emailConnectionString);
		}
		emailProps.clear();
		// split string into key/value pairs (i.e. format: mail.smtp.host=smtp.gmail.com,mail.smtp.port=587)
		String[] keyValuePairs = null;
		try {
			keyValuePairs = emailConnectionString.split(",");
		} catch (PatternSyntaxException e) {
			logger.error("Could not parse the email connection string into key/value pairs by commas; string is: \"%s\".", e, emailConnectionString);
			throw e;
		}
        String senderUsername = null;
		String senderPassword = null;
		for (String keyValuePair : keyValuePairs) {
			String[] keyValuePairArray = null;
			try {
				keyValuePairArray = keyValuePair.split("=");
			} catch (PatternSyntaxException e) {
				logger.error("Could not parse the email key/value pair by equals sign; string is: \"%s\".", e, keyValuePair);
				throw e;
			}
			String key = keyValuePairArray[0];
			String value = keyValuePairArray[1];
			if (logger.isFine()) {
				logger.fine("Attempting to load email key/value pair configuration: key=%s, value=%s", key, value);
			}
			//mail.smtp.host=smtp.gmail.com,mail.smtp.port=587,mail.smtp.starttls.enable=true,mail.smtp.auth=true,mail.debug=true,sender.email=vallocllc@gmail.com,sender.password=valloc123#
			if ("sender.email".equals(key)) {
				senderEmail = value;
			} else if ("sender.username".equals(key)) {
                senderUsername = value;
            } else if ("sender.password".equals(key)) {
				senderPassword = value;
			} else { // misc configuration for properties file
				emailProps.put(key, value);
			}
		}
		// next set authenticator info
        final String finalizedUsername = senderUsername != null ? senderUsername : senderEmail;
		final String finalizedSenderEmail = senderEmail, finalizedSenderPassword = senderPassword;
	    emailSession = Session.getInstance(emailProps, new Authenticator() {
	        @Override protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(finalizedUsername, finalizedSenderPassword);
	        }
	    });
	}

	@Override
	public void sendTextEmail(String recipient, String subject, String body) {
		try {
			if (logger.isFiner()) {
				logger.finer("About to send an email to recipient=%s and subject=\"%s\".", recipient, subject);
			}
			if (logger.isFinest()) {
				logger.finest("The body of the text email for recipient=%s and subject=\"%s\" will contain the following: ", recipient, subject);
				logger.finest(body);
			}
	        MimeMessage msg = new MimeMessage(emailSession);
            msg.setFrom(new InternetAddress(senderEmail)) ;
	        msg.setRecipients(Message.RecipientType.TO, recipient);
	        msg.setSubject(subject);
	        msg.setText(body);
	        Transport.send(msg);
	        if (logger.isFiner()) {
				logger.finer("Successfully sent text email to recipient %s.", recipient);
			}
	     } catch (Exception e) {
	        logger.error("Failed to send text email message to recipient %s due to %s.", e, recipient, e);
	     }
	}

	@Override
	public void sendHtmlEmail(String recipient, String subject, String body) {
		try {
			if (logger.isFiner()) {
				logger.finer("About to send an email to recipient=%s and subject=\"%s\".", recipient, subject);
			}
			if (logger.isFinest()) {
				logger.finest("The body of the HTML email for recipient=%s and subject=\"%s\" will contain the following: ", recipient, subject);
				logger.finest(body);
			}
	        MimeMessage msg = new MimeMessage(emailSession);
            msg.setFrom(new InternetAddress(senderEmail)) ;
	        msg.setRecipients(Message.RecipientType.TO, recipient);
	        msg.setSubject(subject);
	        msg.setContent(body, "text/html; charset=utf-8");
	        Transport.send(msg);
	        if (logger.isFiner()) {
				logger.finer("Successfully sent HTML email to recipient %s.", recipient);
			}
	     } catch (Exception e) {
	        logger.error("Failed to send HTML email message to recipient %s due to %s.", e, recipient, e);
	     }
	}


	@Override
	public String jsonStringify(final Object object) {
		String json = null;
		
		final StringWriter stringWriter = new StringWriter();
		final BufferedWriter bufferedWriter = new BufferedWriter(stringWriter);
		final ObjectMapper mapper = new ObjectMapper();
		try {
			final JsonGenerator jsonGenerator = mapper.getJsonFactory().createJsonGenerator(stringWriter);
			if (logger.isFiner()) {
				logger.finer("About to convert the following object to JSON: %s.", object);
			}
			mapper.writeValue(jsonGenerator, object);
			
			json = stringWriter.toString();
			if (logger.isFiner()) {
				logger.finer("Successfully converted the object to JSON: \"%s\".", json);
			}
		} catch (IOException e) {
			String msg = String.format("Exception when attempting to stringify JSON with the following object: %s.", object);
			logger.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			try { 
				bufferedWriter.close(); 
			} catch (IOException e) {
				logger.warn("Exception when attempting to close JSON stringify buffered writer: %s.", e);
			}
			try { 
				stringWriter.close(); 
			} catch (IOException e) {
				logger.warn("Exception when attempting to close JSON stringify string writer: %s.", e);
			}
		}
		
		return json;
	}

	
	@Override
	public <T> T jsonParse(final String json, final Class<T> type) {
		T object;
		
		final ObjectMapper mapper = new ObjectMapper();
		try {
			object = mapper.readValue(json, type);
		} catch (Exception e) {
			String msg = String.format("Exception occurred when attempting to parse JSON into an object: %s.", e);
			logger.error(msg, e);
			logger.warn("The JSON from the Exception (%s) was:\n%s\n.", e, json);
			throw new RuntimeException(msg, e);
		}
		
		return object;
	}


	@Override
	public String urlEncode(String url) {
		String retval = null;
		
		try {
			retval = URLEncoder.encode(url, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding for %s.", e, DEFAULT_URL_ENCODING);
		}
		
		return retval;
	}


	@Override
	public String urlDecode(String url) {
		String retval = null;
		
		try {
			retval = URLDecoder.decode(url, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			logger.error("Unsupported encoding for %s.", e, DEFAULT_URL_ENCODING);
		}
		
		return retval;
	}

	@Override
	public String newUuidString() {
		String retval = null;
		retval = UUID.randomUUID().toString();
		if (logger.isFiner()) {
			logger.finer("Returning generated UUID string: " + retval);
		}
		return retval;
	}

    @Override
    public String toConversationFormat(Date date) {
        return Constants.CONVERSATION_TS_FORMAT.format(date);
    }
}
