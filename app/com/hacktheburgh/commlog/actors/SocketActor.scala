package com.hacktheburgh.commlog.actors

import akka.actor.{ActorRef, Actor}
import akka.event.Logging
import com.hacktheburgh.commlog.actors.messages.Commit

/**
 * WebSocket handler actor for pushing JSON to clients.
 *
 * @author Arkan <arkan@drakon.io>
 */
class SocketActor(out: ActorRef) extends Actor {

  val log = Logging(context.system, this)

  override def receive = {
    case x:Commit =>
      out ! x.json
    case x:String =>
      // TODO: Remove me. Catch-and-ignore to avoid dead-letter warnings.
      log.warning("Sending test payload.")
      context.self ! new Commit("Test commit message", "Emberwalker/derplogs", "Emberwalker")
  }

}