package services;

public class Mail {
    private String subject;
    private String sender;
    private String htmlContent;
    private String recipient;
    private String replyTo;
    
    public String getSender() {
    	return sender;
    }

    public void setSender(String sender) {
    	this.sender = sender;
    }


    public String getSubject() {
    	return subject;
    }

    public void setSubject(String subject) {
    	this.subject = subject;
    }

    public String getHtmlContent() {
    	return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
    	this.htmlContent = htmlContent;
    }

    /**
     * Obtiene el conjunto de direcciones de correo electronico receptoras. Puede ser uno solo o bien ser varios.
     * En caso de ser varios, estas se encuentran separadas por coma (,).
     *
     * @return la direccion o el conjunto de direcciones de correo electronico receptoras.
     */
    public String getRecipient() {
    	return recipient;
    }

    /**
     * Asigna la direccion o el conjunto de direcciones de correo electronico receptoras.
     *
     * @param recipient la nueva o el conjunto de nuevas direcciones de correo electronico receptoras. En caso de ser varios,
     * estas deben estar separadas por coma (,).
     */
    public void setRecipient(String recipient) {
    	this.recipient = recipient;
    }

    public String getReplyTo() {
    	return replyTo;
    }

    public void setReplyTo(String replyTo) {
    	this.replyTo = replyTo;
    }
}
