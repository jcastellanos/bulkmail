package services

import play.api.Play.current
import anorm._
import play.api.db._
import sql.SubscriberSql
import anorm.SqlParser._
import sql.EmailSql

object SubscriberService {
  
	val STATE_VALID = "valid"
	val STATE_UNSUBSCRIBED = "unsubscribed"
	
	/**
	 * Permite crear un nuevo subscriptor.
	 * @return id del subscriptor
	 */
	def createSubscriber(mail: String): Long = {
		val _mail = mail.toLowerCase()
		DB.withConnection { implicit c =>
		  	SubscriberSql.insert(_mail, STATE_VALID).executeInsert().get
		}		
	}
	/**
	 * Retorna una lista con los subsciptores que coninciden con el email
	 * @return List[(Long, String, String)] Lista con tuplas que contienen (id, email, state)
	 */
	def getSubscriberByEmail(email: String): Option[(Long, String, String)] = {
		DB.withConnection { implicit c =>
		  	val list = SubscriberSql.selectByMail(email.toLowerCase()).as(
		  		long("id") ~ str("email") ~ str("state") map(flatten) *
		  	)
		  	if(list.length == 1) {
		  	  Some(list.head)
		  	}
		  	else {
		  	  None
		  	}
		}
	} 
	
	/**
	 * Retorna una lista con los subsciptores que coninciden con los datos de unsubscribe
	 * @return List[(Long, String)] Lista con tuplas de datos del suscriptor que contienen (id, email)
	 */
	def getSubscriberByUnsuscribeData(subscriberId: Long, unsuscribeToken: String): Option[(Long, String)] = {
		DB.withConnection { implicit c =>
		  	val list = EmailSql.selectSubscriberBySubscriberIdAndUnsuscribeToken(subscriberId, unsuscribeToken).as(
		  		long("id") ~ str("email") map(flatten) *
		  	)
		  	if(list.length == 1) {
		  	  Some(list.head)
		  	}
		  	else {
		  	  None
		  	}
		}
	}
	
	def unsuscribe(subscriberId: Long): Unit = {
		DB.withConnection { implicit c =>
			SubscriberSql.updateState(subscriberId, STATE_UNSUBSCRIBED).executeUpdate()
		}
	}
}
