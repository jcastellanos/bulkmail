package actors
import akka.actor.Actor
import services.EmailService
import services.SubscriberService
import actors.messages._
import scala.io.Source._
import services.ConfigurationService._
import services.CampaignService
import java.util.Date


class EmailActor extends Actor {
	def receive = {
		case email: Email => {
			CampaignService.createEmail(email.campaignId, email.subscriberId, email.unsuscribeToken)
		}
		case sended: EmailSended => {
			CampaignService.updateEmailSended(sended.campaignId, sended.subscriberId)
		}
		case emailException: EmailException => {
			CampaignService.incrementNumError(emailException.campaignId)
		}
		case _ => {
			throw new RuntimeException("Mensaje invalido")
		}
	}
}
