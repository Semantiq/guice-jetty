package eu.semantiq.webapp

import org.apache.commons.io.IOUtils
import org.json4s.jvalue2extractable
import com.google.inject.AbstractModule
import com.google.inject.Singleton
import com.google.inject.name.Names
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.scalatra._
import org.scalatra.scalate._

class AModule extends AbstractModule {
  def configure {
    bind(classOf[MyServlet])
    bind(classOf[ResourcesServlet])
    bind(classOf[JettyServer])
    bind(classOf[Integer]).annotatedWith(Names.named("port")).toInstance(8080)
  }
}

case class TODOList(todos: Seq[String])
case class AddTaskRequest(task: String)

@Singleton
class MyServlet extends ApiServlet {
  var todos = Seq("Go shopping")

  get("/tasks") {
    TODOList(todos)
  }

  post("/tasks") {
    val task = parsedBody.extract[AddTaskRequest]
    todos :+= task.task
    TODOList(todos)
  }
}

@Singleton
class HomePageServlet extends ScalatraServlet with ScalateSupport {
  get("/") {
    contentType = "text/html"
    ssp("/layouts/index.ssp")
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
