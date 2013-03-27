package services;

public class SmtpException extends Exception {

	private static final long serialVersionUID = -4466132243687297212L;

	public SmtpException(Throwable thrwbl){
        super(thrwbl);
    }
}
