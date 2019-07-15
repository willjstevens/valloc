package com.valloc;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public final class Test {


	public static void main(String[] args) {
		

		   Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
//		    props.put("mail.from","willjstevens@gmail.com");
		    props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.port", "587");
		    props.put("mail.smtp.auth", "true");
		    props.put("mail.debug", "true");

		    Session session = Session.getInstance(props, new Authenticator() {
		        @Override
		        protected PasswordAuthentication getPasswordAuthentication() {
//		            return new PasswordAuthentication("willjstevens@gmail.com", "googley123#");
		        	return new PasswordAuthentication("vallocllc@gmail.com", "valloc123#");
		        }
		    });
		    try {
		        MimeMessage msg = new MimeMessage(session);
//		        msg.setFrom();
		        msg.setRecipients(Message.RecipientType.TO, "willjstevens@gmail.com");
		        msg.setSubject("Test message - date 2");
		        
//		        msg.setfr
//		        msg.setSentDate(new Date());
//		        msg.setText("Hello, world!\n");
		        
		        StringBuilder builder = new StringBuilder();
		        builder.append("<img src='https://www.valloc.com/r/pblogo.png'>");
		        builder.append("<br />");
		        builder.append("<h2>Welcome to Valloc.</h2>");
		        builder.append("<br />");
		        builder.append("Please click on the link below to verify your email address. Then you can begin using Valloc.");
		        builder.append("<br />");
		        builder.append("Thank you!");
		        msg.setContent(builder.toString(), "text/html; charset=utf-8");
		        
		        
		        Transport.send(msg);
		     } catch (MessagingException mex) {
		        System.out.println("send failed, exception: " + mex);
		     }
	
	}
	
	
//	public static void main(String[] args) {
//		
//		// of course you would use DI in any real-world cases
//		JavaMailSenderImpl sender = new JavaMailSenderImpl();
//		sender.setHost("smtp.gmail.com");
//		sender.setPort(587);
//		Properties props = new Properties();
//		props.put("mail.transport.protocol", "smtp");
//		props.put("mail.smtp.host", "smtp.gmail.com");
//		props.put("mail.smtp.port", "587");
//		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.starttls.enable", "true");
//		props.put("mail.smtp.user", "willjstevens@gmail.com");
//		props.put("mail.smtp.password", "googley123#");
//		props.put("mail.debug", "true");
//		
//		MimeMessage message = sender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message);
//		try {
//			helper.setTo("willjstevens@gmail.com");
//			helper.setText("Hello world!");
//		} catch (MessagingException e) {
//			e.printStackTrace();
//		}
//
//		sender.send(message);
//
//	}
	
	

}
