package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import models.Task
import play.api.libs.json.JsObject
import play.api.libs.json.JsString
import play.api.libs.json.JsObject
import play.api.libs.json.JsArray
import models.Task
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.libs.json.Format
import play.api.libs.json.JsObject
import play.api.libs.json.JsNumber
import play.api.libs.json.JsObject

object TaskController extends Controller {

  import views.CustomAssets.implicitAssets

  val taskForm = Form("label" -> nonEmptyText)

  val successResponse = Ok(JsObject(List("status" -> JsString("OK"))))

  val failureResponse = BadRequest(JsObject(List("status" -> JsString("FAIL"))))

  implicit object TaskFormat extends Format[Task] {
    def reads(json: JsValue): Task = Task.fromJson(json)
    def writes(task: Task): JsValue = Task.toJson(task)
  }

  def tasks = Action {
    Ok(views.html.index())
  }

  def allTasks = Action {
    val tasks = JsArray(Task.all.map(task => Json.toJson(task)))
    Ok(JsObject(List("tasks" -> tasks)))
  }

  def newTask(label: String) = Action {
    Task.create(label)
    successResponse
  }

  def deleteTask(id: Long) = Action {
    Task delete id
    successResponse
  }

  def updateTask() = Action { implicit request =>
    request.body.asFormUrlEncoded match {
      case Some(e) =>
        val task = Task(e("id").head.toLong, e("done").head.toBoolean, e("label").head)
        Task.update(task)
        successResponse
      case _ => failureResponse
    }
  }

  def archiveTasks() = Action {
    Task.archive()
    successResponse
  }
}
