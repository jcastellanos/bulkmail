package actors
import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinRouter
import actors.messages.Campaign
import scala.io.Source._
import actors.messages.Email
import services.ConfigurationService._
import actors.messages.CampaignEmailList
import actors.messages.Email
import java.util.Date
import actors.messages.EmailSended
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import actors.messages.EmailException


class MasterActor extends Actor {	  	
  
	val campaignActor = context.actorOf(Props[CampaignActor], name = "CampaignActor")
	val emailListActor = context.actorOf(Props[EmailListActor], name = "EmailListActor")
	val emailActor = context.actorOf(Props[EmailActor].withRouter(RoundRobinRouter(nrOfInstances = AWS_MESSAGE_PER_SECOND)), name = "EmailActor")
	val smtpActor = context.actorOf(Props[SmtpActor].withRouter(RoundRobinRouter(nrOfInstances = AWS_MESSAGE_PER_SECOND)), name = "SmtpActor")
	

	  //getContext().actorOf(new Props(SmtpActor.class).withRouter(new RoundRobinRouter(5)), "smtp");
  
  def receive = {
		//case email: String => smtpActor ! email
		case campaign: Campaign => {
			campaignActor ! campaign
			/*
			val filefull = getRepositoryUploadPath + "/" + campaign.filename
	  		val it = fromFile(filefull).getLines		  		
	  		while (it.hasNext) {
	  			val mail = Mail(it.next(), campaign.id, campaign.subject, campaign.body)
	  			dbActor ! mail
	  			//smtpActor ! mail
	  			Thread.sleep(1000 / getAwsMessagePerSecond);
	  		}
	  		*/
		}
		case campaignEmailList: CampaignEmailList => {
			emailListActor ! campaignEmailList
		}
		case email: Email => {
			emailActor ! email
			smtpActor ! email
		}
		case sended: EmailSended => {
			emailActor ! sended
		}	
		case exception: EmailException => {
			emailActor ! exception
		}
		case _ => {
			throw new RuntimeException("Mensaje invalido")
		}
	}


}
