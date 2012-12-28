package models

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue
import play.api.libs.json.JsBoolean
import play.api.libs.json.JsString
import play.api.libs.json.JsNumber
import play.api.libs.json.JsNumber

case class Task(id: Long, done: Boolean, label: String)

object Task {

  val task = {
    get[Long]("id") ~
      get[Boolean]("done") ~
      get[String]("label") map {
        case id ~ done ~ label => Task(id, done, label)
      }
  }

  def fromJson(json: JsValue): Task = {
    val label = (json \ "label").as[String]
    val id = (json \ "id").as[Long]
    val done = (json \ "done").as[Boolean]
    Task(id, done, label)
  }

  def toJson(task: Task): JsValue = JsObject(List("label" -> JsString(task.label), "done" -> JsBoolean(task.done), "id" -> JsNumber(task.id)))

  def all(): List[Task] = DB.withConnection { implicit c =>
    SQL("select id, done, label from task").as(task *)
  }

  def create(label: String) {
    DB.withTransaction { implicit t =>
      SQL("insert into task(label, done) values ({label}, false)").on('label -> label).executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withTransaction { implicit t =>
      SQL("delete from task where id = {id}").on('id -> id).executeUpdate()
    }
  }

  def update(task: Task) {
    DB.withTransaction { implicit t =>
      SQL("update task set label = {label}, done = {done} where id = {id}").on('id -> task.id, 'label -> task.label, 'done -> task.done).executeUpdate()
    }
  }

  def archive() {
    DB.withTransaction { implicit t =>
      SQL("delete from task where done = true").executeUpdate()
    }
  }
}