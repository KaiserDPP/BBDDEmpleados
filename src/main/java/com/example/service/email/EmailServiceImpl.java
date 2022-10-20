package com.example.service.email;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.core.io.FileSystemResource;
import java.io.File;


import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.example.resource.EmailBody;

@Service
public class EmailServiceImpl implements EmailService {
	
	private final JavaMailSender sender;

	public EmailServiceImpl(JavaMailSender sender) {
	    this.sender = sender;
	}

	@Override
	public boolean sendEmail(EmailBody emailBody, String rutaAdjunto, String nombreArchivo) throws MessagingException {
	    //LOGGER.info("EmailBody: {}", emailBody.toString());
	    return sendEmailTool(emailBody.getContent(), emailBody.getEmail(), emailBody.getSubject(), rutaAdjunto, nombreArchivo);
	}
	
	private boolean sendEmailTool(String textMessage, String email, String subject, String rutaAdjunto, String nombreArchivo) throws MessagingException {
	    boolean send = false;
	    MimeMessage message = sender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);
	    
	    try {
	        //helper.setTo(email);  //setTo para un solo receptor
	    	helper.setFrom("testjavi2022@gmail.com");
	        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email)); //setRecipients para multiples receptores separados por "," en un String
	        
	        helper.setText(textMessage, true);
	        helper.setSubject(subject);

	        FileSystemResource file = new FileSystemResource(new File(rutaAdjunto));
            helper.addAttachment(nombreArchivo, file);
	      
	        
	        sender.send(message);
	        send = true;
	        //LOGGER.info("Mail sent!");
	    } catch (MessagingException e) {
	        //LOGGER.error("There has been an error sending mail", e);
	    }
	    return send;
	}
}
