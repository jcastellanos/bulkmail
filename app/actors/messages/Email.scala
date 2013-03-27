package actors.messages

case class Email(campaignId: Long, subscriberId: Long,  email: String, subject: String, body: String, unsuscribeToken: String)