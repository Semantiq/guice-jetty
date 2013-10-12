import sbt._
import Keys._

object GuiceJettyBuild extends Build {
	override lazy val settings = super.settings ++ Seq(
	  libraryDependencies ++= Seq(
	    "com.google.inject" % "guice" % "3.0",
	    "com.google.inject.extensions" % "guice-servlet" % "3.0",
	    "org.eclipse.jetty" % "jetty-webapp" % "9.0.6.v20130930"
	  )
	)
	lazy val root = Project(
	  id = "guice-jetty",
	  base = file("."),
	  settings = Project.defaultSettings ++ settings)
}
