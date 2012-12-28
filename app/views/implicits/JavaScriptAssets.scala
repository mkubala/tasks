package views.implicits

import controllers.routes

object JavaScriptAssets {

  val jsFiles = List("javascripts/jquery-1.7.1.min.js", "bootstrap/js/bootstrap.min.js")

  implicit val jsAssets = jsFiles map { path =>
    new JsAsset(path)
  }

}

class JsAsset(path: String) {
  def toHtml = <script type="text/javascript" src={ routes.Assets.at(path).toString }></script>
}

object cssAssets {
  val cssFiles = List("bootstrap/css/bootstrap.min.css")

  implicit val cssAssets = cssFiles map { path =>
    new CssAsset(path)
  }
}

class CssAsset(path: String) {
  def toHtml = <link rel="stylesheet" src={ routes.Assets.at(path).toString }/>
}

