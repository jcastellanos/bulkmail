package sql

import anorm._


object LogSql {
	def insert(value: String) = SQL(
		  """
		    insert into log(value, created) 
		    values ({value}, now());
		  """
	   ).on("value" -> value)	 
}