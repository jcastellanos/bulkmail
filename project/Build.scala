import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "bulkmail"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "postgresql" % "postgresql" % "8.4-702.jdbc4",
    "com.amazonaws" % "aws-java-sdk" % "1.3.27",
    "javax.mail" % "mail" % "1.4"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
