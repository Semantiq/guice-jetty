package eu.semantiq.webapp

import javax.servlet.http._
import com.google.inject._
import org.apache.commons.io.IOUtils
import com.google.inject.name.Names

class AModule extends AbstractModule {
  def configure {
    bind(classOf[MyServlet])
    bind(classOf[ResourcesServlet])
    bind(classOf[JettyServer])
    bind(classOf[Integer]).annotatedWith(Names.named("port")).toInstance(8080)
  }
}

@Singleton
class MyServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
    resp.getOutputStream().println("[ \"this came from the server\" ]")
    resp.flushBuffer()
  }
}

@Singleton
class ResourcesServlet extends HttpServlet {
  override def doGet(request: HttpServletRequest, response: HttpServletResponse) {
    val uri = request.getRequestURI
    isStaticResource(uri) flatMap loadResource match {
      case Some(is) => IOUtils.copy(is, response.getOutputStream)
      case None => fileNotFound(response)
    }
    response.flushBuffer
  }

  private def isStaticResource(uri: String) = if (uri startsWith "/static/") Some(uri) else None 
  private def loadResource(uri: String) = Option(getClass.getResourceAsStream(uri))
  private def fileNotFound(response: HttpServletResponse) {
    response.setStatus(404)
    response.getOutputStream.println("File Not Found")
  }
}
