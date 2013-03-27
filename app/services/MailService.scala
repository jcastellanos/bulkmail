package services

import java.util.Properties
import services.ConfigurationService._
import javax.mail.Session
import javax.mail.Authenticator
import javax.mail.PasswordAuthentication
import javax.mail.Provider
import javax.mail.Message
import javax.mail.internet.MimeMessage
import javax.mail.internet.InternetAddress
import javax.mail.Transport

object MailService {
  
	val MAIL_HTMLCONTENT = "text/html; charset=ISO-8859-1"
  
	def send(from: String, to: String, subject: String, content: String) = {
	  
		def loadProperties(): Properties = {
			val properties = new Properties()
		    properties.put("mail.transport.protocol", getValue("mail.transport.protocol"));
		    properties.put("mail.smtp.localhost", getValue("mail.smtp.localhost"));
		    properties.put("mail.smtp.host", getValue("mail.smtp.localhost"));
		    properties.put("mail.smtp.port", getValue("mail.smtp.port"));
		    properties.put("mail.smtp.starttls.enable", getValue("mail.smtp.starttls.enable"));
		    properties.put("mail.smtp.socketFactory.port", getValue("mail.smtp.socketFactory.port"));
		    properties.put("mail.smtp.socketFactory.class", getValue("mail.smtp.socketFactory.class"));
		    properties.put("mail.smtp.socketFactory.fallback", getValue("mail.smtp.socketFactory.fallback"));
		    properties.put("mail.email", getValue("mail.email"));
		    properties.put("mail.smtp.mail.sender", getValue("mail.smtp.mail.sender"));
		    properties.put("mail.smtp.auth", getValue("mail.smtp.auth"));
		    properties.put("mail.user", getValue("mail.user"));
		    properties.put("mail.password", getValue("mail.password"));
		    properties.put("mail.debug", getValue("mail.debug"));
		    properties
		}
			    
	    val session = Session.getInstance(loadProperties, new Authenticator() {
	    	override def getPasswordAuthentication(): PasswordAuthentication = {
		    	return new PasswordAuthentication(ConfigurationService.getValue("mail.user"), ConfigurationService.getValue("mail.password"));
		    }
		});
	    session.addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPSSLTransport", null, null));
	    val msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	    msg.addRecipients(Message.RecipientType.TO, to);
	    msg.setSubject(subject);
	    // msg.setText(BODY_PLAIN);
	    msg.setContent(content, MAIL_HTMLCONTENT);
	    /*
	    if(mail.getReplyTo() != null) {
	    	msg.setReplyTo(InternetAddress.parse(mail.getReplyTo()));
	    }
	    */
	    msg.saveChanges();
		Transport.send(msg);
	}
	
}