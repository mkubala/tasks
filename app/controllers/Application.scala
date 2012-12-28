package controllers

import play.api._
import play.api.mvc._

object Application extends Controller {

  def index = Action {
    Redirect(routes.TaskController.tasks)
  }

  def main = Action {
    Redirect(routes.TaskController.tasks)
  }

}