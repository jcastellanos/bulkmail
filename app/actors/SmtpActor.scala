package actors
import akka.actor.Actor
import services.EmailService
import services.Mail
import actors.messages.Email
import services.ConfigurationService._
import java.util.Date
import messages.EmailSended
import services.CampaignService
import messages.EmailException
import actors.messages.EmailException


class SmtpActor extends Actor {
	def receive = {
		case email: Email =>{
			try {
				val emailService  = new EmailService()
			    val _mail = new Mail()
			    _mail.setSender(MAIL_FROM)
			    _mail.setSubject(email.subject)
			    _mail.setHtmlContent(CampaignService.parseBodyEmail(email.body, Some(email.subscriberId), Some(email.unsuscribeToken)))
			    _mail.setRecipient("success@simulator.amazonses.com")
			    //Thread.sleep(1000 / getAwsMessagePerSecond)
			    emailService.sendMail(_mail)
			    sender ! EmailSended(email.campaignId, email.subscriberId)
			}
		    catch {
		    	case e: Exception => {
		    	  e.printStackTrace()
		    	  sender ! EmailException(e, email.campaignId, email.subscriberId)
		    	}
		    }
		}
	}
}
