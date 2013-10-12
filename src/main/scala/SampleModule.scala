package experiment

import javax.servlet.http._

import com.google.inject._

class HttpComponent {
  @Inject def serve(request: HttpServletRequest, response: HttpServletResponse) {
    println("request: " + request.getContextPath())
  }
}

class AModule extends AbstractModule {
  def configure {
    bind(classOf[HttpComponent])
    bind(classOf[SampleServlet])
  }
}

@Singleton
class SampleServlet extends HttpServlet {
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse) {
	println("request in servlet: " + req.getContextPath())
	resp.getWriter().write("hello")
  }
}
