import sbt._
import Keys._

object GuiceJettyBuild extends Build {
  override lazy val settings = super.settings ++ Seq(
    scalaVersion := "2.10.2",
    libraryDependencies ++= Seq(
      "com.google.inject" % "guice" % "3.0",
      "com.google.inject.extensions" % "guice-servlet" % "3.0",
      "org.eclipse.jetty" % "jetty-webapp" % "9.0.6.v20130930",
      "commons-io" % "commons-io" % "2.4",
      "org.scalatra" %% "scalatra" % "2.2.1",
      "org.scalatra" %% "scalatra-json" % "2.2.1",
      "org.scalatra" %% "scalatra-scalate" % "2.2.1",
      "org.json4s" %% "json4s-jackson" % "3.2.4"))
  lazy val root = Project(
    id = "guice-jetty",
    base = file("."),
    settings = Project.defaultSettings ++ settings)
}
