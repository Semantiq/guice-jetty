package eu.semantiq.webapp

import java.util.EnumSet._
import javax.servlet.DispatcherType
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet._
import com.google.inject._
import com.google.inject.servlet._
import com.google.inject.servlet.GuiceFilter
import javax.servlet.DispatcherType
import org.eclipse.jetty.servlet.DefaultServlet

object WebApp extends App {
  Guice.createInjector(new ServletModule() {
	override def configureServlets {
		serve("/api/*").`with`(classOf[MyServlet])
    }
  }, new AModule())

  val server = new Server(8080)
  val context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS)
  context.addFilter(classOf[GuiceFilter], "/*", allOf(classOf[DispatcherType]))
  context.addServlet(classOf[DefaultServlet], "/")
  server.start()
  server.join()
}
