package services

import play.api.Play.current
import anorm._
import play.api.db._
import anorm.SqlParser._
import sql.ConfigurationSql

object ConfigurationService {
    
	def APP_URL: String = getValue("app.url")
    def REPOSITORY_UPLOAD_PATH: String = getValue("repository.upload.path")
    def AWS_MESSAGE_PER_SECOND: Int = getValue("aws.message.per.second").toInt
    def MAIL_FROM: String = getValue("mail.email")

    
    def getValue(key: String) = {
        val _key = key.trim()
        DB.withConnection { implicit c =>
		  	ConfigurationSql.selectByKey(_key).as(scalar[String].singleOpt) match {
		  	  case None => throw new RuntimeException("No existe ninguna configuracion con la llave: " + _key)
		  	  case Some(key) => key
		  	}
		}
    }
}
