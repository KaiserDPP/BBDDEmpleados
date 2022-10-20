package com.example.service.email;

import javax.mail.MessagingException;

import com.example.resource.EmailBody;


public interface EmailService {

	boolean sendEmail(EmailBody emailBody, String rutaAdjunto, String nombreArchivo) throws MessagingException;
}
