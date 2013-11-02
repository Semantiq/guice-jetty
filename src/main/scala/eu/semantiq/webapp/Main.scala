package eu.semantiq.webapp

object Main extends App {
  val webApp = new WebApp
  webApp.start
  System.in.read()
  webApp.stop
}
