package controllers

import play.api.mvc._
import java.io.File
import scala.io.Source._
import play.api.data._
import play.api.data.Forms._
import util._
import services.CampaignService
import actors.MasterActor
import play.api.libs.concurrent.Akka
import play.api.Play.current
import akka.actor.Props
import actors.messages.Campaign
import services.ConfigurationService._

object BulkController extends Controller {
		  
	val master = Akka.system.actorOf(Props[MasterActor], name = "MasterActor")
  
	def index = Action {
		Ok(views.html.bulk.index("Your new application is ready."))
	}
	
	def onupload = Action { implicit request =>
	  	var i = 0
	  	var filename = ""
		request.body.asMultipartFormData.map { body =>
		  	body.file("file").map { file =>
		  	  	filename = Util.generateName
		  	  	val filefull = REPOSITORY_UPLOAD_PATH + "/" + filename
		  		file.ref.moveTo(new File(filefull))
		  		val it = fromFile(filefull).getLines		  		
		  		while (it.hasNext){
		  			it.next()
		  			i = i+1
		  		}
		  	}
		}
		Ok(views.html.bulk.upload(i, filename))
	}
	
	def onpreview = Action { implicit request => {
			val previewForm = Form(
			  tuple(
			    "body" -> text,
			    "hidden" -> ignored(0)
			  )
			)
			previewForm.bindFromRequest.fold(
				formWithErrors => Ok(views.html.error("ERROR")),
				value => {					
					Ok(views.html.bulk.preview(CampaignService.parseBodyEmail(value._1)))
				}
			)    
			
		}	
	}
	
	def onsave = Action { implicit request => {
		val campaignForm = Form(
		  tuple(
		    "id" -> text,
		    "name" -> text,
		    "filename" -> text,
		    "subject" -> text,
		    "body" -> text
		  )
		)
		campaignForm.bindFromRequest.fold(
			formWithErrors => Ok(views.html.error("ERROR")),
			value => {
				if(value._1.length == 0) {
					println("Guardar la camaping");
					val id = CampaignService.createCampaign(value._2, value._3, value._4, value._5).get
					Ok(views.html.bulk.save(id, "Campana creada"))
				}
				else {
					println("else");
					CampaignService.updateCampaign(value._1.toLong, value._2, value._3, value._4, value._5)
					Ok(views.html.bulk.save(value._1.toLong, "Campana actualizada"))
				}
				
			}
		)    	
	}}
	
	def onsendmassive = Action { implicit request => {
		val campaignForm = Form(
		  tuple(
		    "id" -> text,
		    "name" -> nonEmptyText,
		    "filename" -> nonEmptyText,
		    "subject" -> nonEmptyText,
		    "body" -> nonEmptyText
		  )
		)
		campaignForm.bindFromRequest.fold(
			formWithErrors => Ok(views.html.error("Todos los campos son obligatorios")),
			value => {
				var id: Long = 0
				var status = ""
				if(value._1.length == 0) {
					println("Guardar la camaping");
					id = CampaignService.createCampaign(value._2, value._3, value._4, value._5).get
					CampaignService.updateQueued(id)
					status = "Campana creada"
				}
				else {
					println("else");
					id = value._1.toLong
					CampaignService.updateCampaign(id, value._2, value._3, value._4, value._5)
					CampaignService.updateQueued(id)
					status = "Campana actualizada"				
				}
				master ! Campaign(id, value._2, value._3, value._4, value._5)
				status = "Creada la tarea"
				Ok(views.html.bulk.save(id, status))
			}
		)    
		
	}}
}
