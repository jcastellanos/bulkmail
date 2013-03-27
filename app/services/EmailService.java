package services;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Provider;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailService {
    
    private Session session;
    
    private static final String MAIL_HTMLCONTENT = "text/html; charset=ISO-8859-1";
    

    public void sendMail(Mail mail) throws SmtpException {
		openMailSession();
		try {
		    InternetAddress[] direccionesReceptoras = InternetAddress.parse(mail.getRecipient());
		    Message msg = new MimeMessage(session);
		    if(mail.getSender() != null) {
		    	msg.setFrom(new InternetAddress(mail.getSender()));
		    }
		    msg.addRecipients(Message.RecipientType.TO, direccionesReceptoras);
		    msg.setSubject(mail.getSubject());
		    // msg.setText(BODY_PLAIN);
		    msg.setContent(mail.getHtmlContent(), MAIL_HTMLCONTENT);
		    if(mail.getReplyTo() != null) {
		    	msg.setReplyTo(InternetAddress.parse(mail.getReplyTo()));
		    }
		    msg.saveChanges();
			Transport.send(msg);
		    
		} catch (Exception ex) {
		    throw new SmtpException(ex);
		} finally {
		    closeMailSession();
		}
    }

    private void openMailSession() throws SmtpException {
	    final Properties properties = new Properties(); 
	    properties.put("mail.transport.protocol", ConfigurationService.getValue("mail.transport.protocol"));
	    properties.put("mail.smtp.localhost", ConfigurationService.getValue("mail.smtp.localhost"));
	    properties.put("mail.smtp.host", ConfigurationService.getValue("mail.smtp.localhost"));
	    properties.put("mail.smtp.port", ConfigurationService.getValue("mail.smtp.port"));
	    properties.put("mail.smtp.starttls.enable", ConfigurationService.getValue("mail.smtp.starttls.enable"));
	    properties.put("mail.smtp.socketFactory.port", ConfigurationService.getValue("mail.smtp.socketFactory.port"));
	    properties.put("mail.smtp.socketFactory.class", ConfigurationService.getValue("mail.smtp.socketFactory.class"));
	    properties.put("mail.smtp.socketFactory.fallback", ConfigurationService.getValue("mail.smtp.socketFactory.fallback"));
	    properties.put("mail.email", ConfigurationService.getValue("mail.email"));
	    properties.put("mail.smtp.mail.sender", ConfigurationService.getValue("mail.smtp.mail.sender"));
	    properties.put("mail.smtp.auth", ConfigurationService.getValue("mail.smtp.auth"));
	    properties.put("mail.user", ConfigurationService.getValue("mail.user"));
	    properties.put("mail.password", ConfigurationService.getValue("mail.password"));
	    properties.put("mail.debug", ConfigurationService.getValue("mail.debug"));
    
		try {		    
			session = Session.getInstance(properties, new Authenticator() {
		    	@Override
		    	protected PasswordAuthentication getPasswordAuthentication(){
			    	return new PasswordAuthentication(ConfigurationService.getValue("mail.user"), ConfigurationService.getValue("mail.password"));
			    }
			});
			session.addProvider(new Provider(Provider.Type.TRANSPORT, "smtp", "com.sun.mail.smtp.SMTPSSLTransport", null, null));  
		}  catch (IllegalArgumentException ex) {
		    throw new SmtpException(ex);
		} 	
    }
    
    
    private void closeMailSession() {
    	session = null;
    }
    
}
