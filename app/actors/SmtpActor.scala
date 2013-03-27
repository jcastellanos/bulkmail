package actors
import akka.actor.Actor
import services.MailService
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
			    //Thread.sleep(1000 / getAwsMessagePerSecond)
			    MailService.send(MAIL_FROM, "success@simulator.amazonses.com", email.subject, CampaignService.parseBodyEmail(email.body, Some(email.subscriberId), Some(email.unsuscribeToken)))
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
