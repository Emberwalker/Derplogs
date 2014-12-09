package controllers

import akka.actor.Props
import play.api._
import play.api.Play.current
import play.api.mvc._
import play.libs.Akka

import com.hacktheburgh.commlog.actors.SocketActor
import com.hacktheburgh.commlog.github.PubSubAgent

object Application extends Controller {

  // Our PubSubHubbub ActorRef. Important for a few components, espectially the RepoManager.
  val psAgent = Akka.system().actorOf(Props(new PubSubAgent()), "pubsubhubbub-agent")

  // Landing page
  def index = Action {
    Ok(views.html.index())
  }

  // List log view
  def log = Action {
    Ok(views.html.log())
  }

  // *insert Star Wars music here*
  def starlog = Action {
    Ok(views.html.stars())
  }

  // The data socket endpoint
  def socket = WebSocket.acceptWithActor[String, String] { request => out =>
    Props(new SocketActor(out))
  }

  // Github ingest endpoint
  def ingest = Action {
    // TODO: Actually process input.
    Ok("Accepted.")
  }

  // For debug use with /hub
  def yesMan(path:String) = Action {
    Logger.logger.info(s"/hub: $path")
    Ok("Done.")
  }

}