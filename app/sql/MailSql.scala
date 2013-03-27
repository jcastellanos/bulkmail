package sql

import anorm._


object MailSql {
	def insert(campaignId: Long, subscriberId: Long, unsuscribeToken: String) = SQL(
		  """
		    insert into mail(campaign_id, subscriber_id, queued, unsuscribe_token) 
		    values ({campaign_id}, {subscriber_id}, now(), {unsuscribe_token});
		  """
	   ).on("campaign_id" -> campaignId,
	       "subscriber_id" -> subscriberId,
	       "unsuscribe_token" -> unsuscribeToken
	       )
	 
	def updateSended(campaignId: Long, subscriberId: Long) = SQL(
		  """
		    update mail set sended = now() 
		    where campaign_id = {campaign_id} and subscriber_id = {subscriber_id};
		  """
	   ).on("campaign_id" -> campaignId,
	       "subscriber_id" -> subscriberId
	       )
	       
	def selectSubscriberBySubscriberIdAndUnsuscribeToken(subscriberId: Long, unsuscribeToken: String) = SQL(
		  """
		    select subscriber.* from mail, subscriber 
		    where mail.subscriber_id = {subscriber_id} and mail.unsuscribe_token = {unsuscribe_token}
			and mail.subscriber_id =  subscriber.id;
		  """
	   ).on("subscriber_id" -> subscriberId,
	       "unsuscribe_token" -> unsuscribeToken
	       )
}