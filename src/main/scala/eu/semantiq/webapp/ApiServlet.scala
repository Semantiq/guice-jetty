package eu.semantiq.webapp

import org.scalatra.ScalatraServlet
import org.scalatra.json.JacksonJsonSupport
import org.json4s.Formats
import org.json4s.DefaultFormats

trait ApiServlet extends ScalatraServlet with JacksonJsonSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats
  before() {
    contentType = formats("json")
  }
}