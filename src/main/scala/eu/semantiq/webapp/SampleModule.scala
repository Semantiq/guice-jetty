package eu.semantiq.webapp

import javax.servlet.http._

import com.google.inject._

class AModule extends AbstractModule {
  def configure {
    bind(classOf[MyServlet])
  }
}

@Singleton
class MyServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.getOutputStream().println("[ \"this came from the server\" ]")
    resp.flushBuffer()
  }
}
