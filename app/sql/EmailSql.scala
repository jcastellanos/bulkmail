package sql

import anorm._


object EmailSql {
	def insert(campaignId: Long, subscriberId: Long, unsuscribeToken: String) = SQL(
		  """
		    insert into email(campaign_id, subscriber_id, queued, unsuscribe_token) 
		    values ({campaign_id}, {subscriber_id}, now(), {unsuscribe_token});
		  """
	   ).on("campaign_id" -> campaignId,
	       "subscriber_id" -> subscriberId,
	       "unsuscribe_token" -> unsuscribeToken
	       )
	 
	def updateSended(campaignId: Long, subscriberId: Long) = SQL(
		  """
		    update email set sended = now() 
		    where campaign_id = {campaign_id} and subscriber_id = {subscriber_id};
		  """
	   ).on("campaign_id" -> campaignId,
	       "subscriber_id" -> subscriberId
	       )
	       
	def selectSubscriberBySubscriberIdAndUnsuscribeToken(subscriberId: Long, unsuscribeToken: String) = SQL(
		  """
		    select subscriber.* from email, subscriber 
		    where email.subscriber_id = {subscriber_id} and email.unsuscribe_token = {unsuscribe_token}
			and email.subscriber_id =  subscriber.id;
		  """
	   ).on("subscriber_id" -> subscriberId,
	       "unsuscribe_token" -> unsuscribeToken
	       )
}