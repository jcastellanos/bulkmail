package actors.messages

case class EmailException(exception: Exception, campaignId: Long, subscriberId: Long)