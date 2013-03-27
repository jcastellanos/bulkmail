package sql

import anorm._


object SubscriberSql {
	def insert(email: String, state: String) = SQL(
		  """
		    insert into subscriber(email, state, created) 
		    values ({email}, {state}, now());
		  """
	   ).on("email" -> email.trim().toLowerCase(),
	       "state" -> state
	       )
	       
	def selectByMail(email: String) = SQL(
		  """
		    select id as id, email as email, state as state from subscriber
		    where email = {email};
		  """
	   ).on("email" -> email.trim().toLowerCase())

	def updateState(id: Long, state: String) = SQL(
		  """
		    update subscriber set state = {state} 
		    where id = {subscriber_id};
		  """
	   ).on("subscriber_id" -> id,
	       "state" -> state
	       )
}