package services

import play.api.Play.current
import anorm._
import play.api.db._
import sql.CampaignSql
import sql.EmailSql
import services.ConfigurationService._

object CampaignService {
	def createCampaign(name: String, filename: String, subject: String, body: String): Option[Long] = {
		DB.withConnection { implicit c =>
			CampaignSql.insert(name, filename, subject, body).executeInsert()
		}		
	}
	
	def updateCampaign(id: Long, name: String, filename: String, subject: String, body: String): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.update(id, name, filename, subject, body).executeUpdate()
		}
	}
	
	def updateQueued(id: Long): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.updateQueued(id).executeUpdate()
		}
	}
	
	def updateSended(id: Long): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.updateSended(id).executeUpdate()
		}
	}
	
	def createEmail(campaignId: Long, subscriberId: Long, unsuscribeToken: String): Option[Long] = {
		DB.withTransaction { implicit c =>
		  	CampaignSql.incrementNumQueued(campaignId).executeUpdate()
			EmailSql.insert(campaignId, subscriberId, unsuscribeToken).executeInsert()						
		}		
	}
	
	def updateNumEmails(campaignId: Long, count: Int): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.updateNumEmails(campaignId, count).executeUpdate()
		}		
	}
	def updateNumQueued(campaignId: Long, count: Int): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.updateNumQueued(campaignId, count).executeUpdate()
		}		
	}
	def updateNumSended(campaignId: Long, count: Int): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.updateNumSended(campaignId, count).executeUpdate()
		}		
	}
	def incrementNumError(campaignId: Long): Unit = {
		DB.withConnection { implicit c =>
			CampaignSql.incrementNumError(campaignId).executeUpdate()
		}		
	}
	
	def updateEmailSended(campaignId: Long, subscriberId: Long): Unit = {
		DB.withConnection { implicit c =>
			EmailSql.updateSended(campaignId, subscriberId).executeUpdate()
			CampaignSql.incrementNumSended(campaignId).executeUpdate()
		}		
	}
	
	def parseBodyEmail(body: String, subscriberId: Option[Long] = None, unsuscribeToken: Option[String] = None): String = {
		val query = subscriberId match {
		  case Some(sid) => "?uid=" + sid + "&token=" + unsuscribeToken.get
		  case None => "#"
		}
		val url = APP_URL + "/list/unsuscribe" + query
		body + "<br><br><p><a href='" + url + "' target='_blank'>Unsuscribe</a></p>"
	}
	
}