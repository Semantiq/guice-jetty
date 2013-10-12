package experiment

import javax.servlet.http._

import com.google.inject._

class AModule extends AbstractModule {
  def configure {
    bind(classOf[MyAppServlet])
  }
}

@Singleton
class MyAppServlet extends FrontServlet {
  import Ok._
  import RequestMatcher._
  
  val hello = get.withPath("/hello").withStringParameter("x")
  val world = get.withPath("/world/{x}").withIntParameter("y")
  
  def mapping(request: HttpServletRequest) = request match {
	case hello(x: String) => Ok(s"Hello: ${x}")
	case world(x: String, y: Int) => Ok(s"World: ${x} ${y + 1}")
  }
}

abstract class FrontServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
	mapping(req)(req, resp)
  }
  def mapping(request: HttpServletRequest): ((HttpServletRequest, HttpServletResponse) => Unit)
}

object RequestMatcher {
  def get = new RequestMatcher().withMethod("GET")
  def post = new RequestMatcher().withMethod("POST")
}

class RequestMatcher(extractors: List[HttpServletRequest => Option[Seq[Any]]] = Nil) {
  
  def unapplySeq(request: HttpServletRequest): Option[Seq[Any]] = {
	val values = extractors.map(e => e(request))
	val everythingMatches = values.forall(_.isDefined)
	if (everythingMatches) Some(values.flatMap(_.get)) else None
  }
  
  def withMethod(method: String) = {
	def methodPredicate(r: HttpServletRequest) = if(r.getMethod == method) Some(Nil) else None
	new RequestMatcher(extractors :+ methodPredicate _)
  }
  
  def withStringParameter(name: String) = {
	def parameterExtractor(r: HttpServletRequest) = Some(Seq(r.getParameter(name)))
	new RequestMatcher(extractors :+ parameterExtractor _)
  }

  def withIntParameter(name: String) = {
	def parameterExtractor(r: HttpServletRequest) = Some(Seq(r.getParameter(name)))
	new RequestMatcher(extractors :+ parameterExtractor _)
  }
  
  def withPath(path: String) = {
	def pathVariableExtractor(r: HttpServletRequest) = if (r.getRequestURI == path) Some(Nil) else None
	new RequestMatcher(extractors :+ pathVariableExtractor _)
  }
}

object Ok {
  implicit def convert(string: String, response: HttpServletResponse) {
	response.getWriter().write(string)
  }
  def apply[T](handler: => T)(implicit converter: (T, HttpServletResponse) => Unit) = {
	(req: HttpServletRequest, resp: HttpServletResponse) => {
		converter(handler, resp)
	}
  }
}
