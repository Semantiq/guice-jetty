package eu.semantiq.webapp

import com.google.inject.Guice
import com.google.inject.servlet.ServletModule

object Main extends App {
  val injector = Guice.createInjector(new ServletModule() {
	override def configureServlets {
		serve("/api/*").`with`(classOf[MyServlet])
		serve("/static/*").`with`(classOf[ResourcesServlet])
    }
  }, new AModule())
  
  val webApp = injector.getInstance(classOf[JettyServer])
  webApp.start
  System.in.read()
  webApp.stop
}
