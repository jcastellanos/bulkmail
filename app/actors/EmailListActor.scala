package actors
import akka.actor.Actor
import services.EmailService
import services.SubscriberService
import actors.messages._
import scala.io.Source._
import services.ConfigurationService._
import java.util.Date
import services.CampaignService


class EmailListActor extends Actor {
	def receive = {
		case campaign: CampaignEmailList => {
			CampaignService.updateNumEmails(campaign.id, campaign.emails.length)
			campaign.emails.foreach { it =>
			  SubscriberService.getSubscriberByEmail(it.toLowerCase()) match {
			    case Some(subscriber) => {
			    	subscriber._3 match {
			    	  case "valid" => sender ! Email(campaign.id, subscriber._1, subscriber._2, campaign.subject, campaign.body, new Date().getTime().toString())
			    	  case _ => None
			    	}
			    	
			    }
			    case None => {
			    	sender ! Email(campaign.id, SubscriberService.createSubscriber(it.toLowerCase()), it.toLowerCase(), campaign.subject, campaign.body, new Date().getTime().toString())
			    }
			  }
			}
		}
		case _ => {
			throw new RuntimeException("Mensaje invalido")
		}
	}
}
