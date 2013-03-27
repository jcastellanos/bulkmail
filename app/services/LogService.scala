package services

import play.api.Play.current
import anorm._
import play.api.db._
import sql.LogSql

object LogService {
	def createLog(value: String): Option[Long] = {
		DB.withConnection { implicit c =>
			LogSql.insert(value).executeInsert()
		}		
	}		
}