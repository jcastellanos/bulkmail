package actors.messages

case class CampaignEmailList(id: Long, name: String, filename: String, emails: Seq[String], subject: String, body: String)
