package views

import controllers.routes
import play.api.templates.Html
import scala.xml.Elem
import config.Configuration

object CustomAssets {

  implicit def stringToJsAsset(path: String): JsAsset = new JsAsset(path)

  implicit def stringToCssAsset(path: String): CssAsset = new CssAsset(path)

  implicit val implicitAssets: Html = {
    Html((Configuration.cssAssets ++ Configuration.jsAssets) map { asset => asset.toString } mkString "\n")
  }

}

trait CustomAsset {
  def toHtml: Elem
  override def toString = toHtml.toString
}

class JsAsset(path: String) extends CustomAsset {
  def toHtml = <script type="text/javascript" src={ routes.Assets.at("javascripts/" + path).toString }></script>
}

class CssAsset(path: String) extends CustomAsset {
  def toHtml = <link rel="stylesheet" href={ routes.Assets.at("stylesheets/" + path).toString }/>
}
