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
import javax.inject.Named

@Singleton
class JettyServer @Inject() (@Named("port") port: Integer) {
  val server = new Server(port)
  val context = new ServletContextHandler(server, "/", ServletContextHandler.NO_SESSIONS)
  context.addFilter(classOf[GuiceFilter], "/*", allOf(classOf[DispatcherType]))
  context.addServlet(classOf[DefaultServlet], "/")
  context.setResourceBase("src/main/webapp")
  def start = server.start()
  def join = server.join()
  def stop = server.stop()
}
