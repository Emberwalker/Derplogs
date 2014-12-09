package com.hacktheburgh.commlog.github

import scala.util.{Random, Success, Failure}
import akka.actor.Actor

import dispatch.{Http, Req, url, Defaults}, Defaults._
import play.api.Logger
import com.roundeights.hasher.Implicits._

import com.hacktheburgh.commlog.internal.{Configuration, RepoManager}
import com.hacktheburgh.commlog.internal.containers.Repo

/**
 * Handler for PubSub interactions with GitHub.
 *
 * @author Arkan <arkan@drakon.io>
 */
class PubSubAgent extends Actor {

  private val GITHUB_HUB_ENDPOINT = if (Configuration.isDebugEnv) "http://localhost:9000/hub" else "https://api.github.com/hub"

  // This secret has to persist for the whole session
  private val sekrit:String = Random.nextString(16).md5

  Logger.logger.info(s"PubSubAgent initialised (using hub '$GITHUB_HUB_ENDPOINT' with secret '$sekrit')")

  private def getIncompleteRequest(repo:Repo): Req =
    url(GITHUB_HUB_ENDPOINT)
      .POST
      .addParameter("hub.topic", s"https://github.com/${repo.user}/${repo.repo}/events/push")
      .addParameter("hub.callback", s"${Configuration.getRootURL}/github/ingest")
      .addParameter("hub.secret", sekrit)

  // See https://developer.github.com/v3/repos/hooks/#pubsubhubbub
  private def checkSignature(msg:String, hash:String): Boolean = msg.hmac(sekrit).sha1 hash_= hash

  // Can you keep us in the loop on this? Thanks Github.
  private def subscribe(repo:Repo) {
    val req = getIncompleteRequest(repo).addParameter("hub.mode", "subscribe")
    val future = Http(req)
    future onComplete {
      case Success(out) => Logger.logger.debug(s"Subscription for $repo succeeded.")
      case Failure(t) =>
        Logger.logger.warn(s"Repo $repo subscription failed: $t")
        RepoManager.removeRepo(repo)  // Don't keep stale repos lying around.
    }
  }

  // We don't care about this repo anymore.
  private def unsubscribe(repo:Repo) {
    val req = getIncompleteRequest(repo).addParameter("hub.mode", "unsubscribe")
    Http(req)  // We don't care if this fails. Not our problem!
  }

  // Actor ingest
  override def receive = {
    case Subscribe(repo) => subscribe(repo)
    case Unsubscribe(repo) => unsubscribe(repo)
    case SignatureCheck(msg, hash) => sender ! checkSignature(msg, hash)
  }

}

abstract class PubSubRequest
case class Subscribe(repo:Repo) extends PubSubRequest
case class Unsubscribe(repo:Repo) extends PubSubRequest
case class SignatureCheck(msg:String, hash:String) extends PubSubRequest
