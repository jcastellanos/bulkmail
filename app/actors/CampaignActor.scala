package actors
import akka.actor.Actor
import services.EmailService
import services.SubscriberService
import actors.messages._
import scala.io.Source._
import services.ConfigurationService._


class CampaignActor extends Actor {
	def receive = {
		case campaign: Campaign => {
			val filefull = REPOSITORY_UPLOAD_PATH + "/" + campaign.filename
	  		val it = fromFile(filefull).getLines
	  		var count = 0
	  		val emails: Seq[String] = it.toList.flatMap { value =>
	  		  	count = count + 1
	  			Set(value)
			}
			sender ! CampaignEmailList(campaign.id, campaign.name, campaign.filename, emails, campaign.subject, campaign.body)
		}
		case _ => {
			throw new RuntimeException("Mensaje invalido")
		}
	}
}
