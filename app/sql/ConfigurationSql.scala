package sql

import anorm._


object ConfigurationSql {
	def selectByKey(key: String) = SQL(
		  """
		    select value as value from configuration
		    where key = {key};
		  """
	   ).on("key" -> key)
}
