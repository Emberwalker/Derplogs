package controllers

import akka.actor.Props
import play.api._
import play.api.Play.current
import play.api.mvc._

import com.hacktheburgh.commlog.actors.SocketActor

object Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    Props(new SocketActor(out))
  }

}