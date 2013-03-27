package sql

import anorm._


object CampaignSql {
	def insert(name: String, filename: String, subject: String, body: String) = SQL(
		  """
		    insert into campaign(name, filename, subject, body, created, updated) 
		    values ({name}, {filename}, {subject}, {body}, now(), now());
		  """
	   ).on("name" -> name,
	       "filename" -> filename,
	       "subject" -> subject,
	       "body" -> body)
	       
	 def update(id: Long, name: String, filename: String, subject: String, body: String) = SQL(
		  """
		    update campaign set name = {name}, filename = {filename}, 
			subject = {subject}, body = {body}, updated = now() 
			where id = {id};
		  """
	   ).on("name" -> name,
	       "filename" -> filename,
	       "subject" -> subject,
	       "body" -> body,
	       "id" -> id)
	       
	 def updateQueued(id: Long) = SQL(
		  """
		    update campaign set queued = now() 
			where id = {id};
		  """
	   ).on("id" -> id)
	   
	 def updateSended(id: Long) = SQL(
		  """
		    update campaign set sended = now() 
			where id = {id};
		  """
	   ).on("id" -> id)
	 
	 def updateNumEmails(id: Long, num: Int) = SQL(
		  """
		    update campaign set num_emails = {num} 
			where id = {id};
		  """
	   ).on("id" -> id,
	       "num" -> num)
	       
	 def incrementNumQueued(id: Long) = SQL(
		  """
		    update campaign set num_queued = num_queued + 1  
			where id = {id};
		  """
	   ).on("id" -> id)
	   
	 def updateNumQueued(id: Long, num: Int) = SQL(
		  """
		    update campaign set num_queued = {num}  
			where id = {id};
		  """
	   ).on("id" -> id,
	       "num" -> num)
	  
	 def incrementNumSended(id: Long) = SQL(
		  """
		    update campaign set num_sended = num_sended + 1 
			where id = {id};
		  """
	   ).on("id" -> id)
	   
	 def incrementNumError(id: Long) = SQL(
		  """
		    update campaign set num_error = num_error + 1 
			where id = {id};
		  """
	   ).on("id" -> id)
	       
	       
	 def updateNumSended(id: Long, num: Int) = SQL(
		  """
		    update campaign set num_sended = {num}  
			where id = {id};
		  """
	   ).on("id" -> id,
	       "num" -> num)
	     
}