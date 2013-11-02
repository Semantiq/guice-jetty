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

class WebApp(port: Integer = 8080) {
  val injector = Guice.createInjector(new ServletModule() {
	override def configureServlets {
		serve("/api/*").`with`(classOf[MyServlet])
    }
  }, new AModule())

  val server = new Server(port)
  val context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS)
  context.addFilter(classOf[GuiceFilter], "/*", allOf(classOf[DispatcherType]))
  context.addServlet(classOf[DefaultServlet], "/")
  context.setResourceBase("src/main/resources")
  
  def start = server.start()
  def join = server.join()
  def stop = server.stop()
}
