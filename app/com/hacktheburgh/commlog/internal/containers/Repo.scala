package com.hacktheburgh.commlog.internal.containers

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Represents a single repository entry.
 *
 * @author Arkan <arkan@drakon.io>
 */
case class Repo(user:String, repo:String) {
  override def toString = s"Repo[$user/$repo]"
}

object Repo {

  def repoFromURL(url:String): Option[Repo] = {
    val URL = """^https?://(?:www.)?github.com/([\w\d-]+)/([\w\d-]+)/?$""".r
    try {
      val URL(user, repo) = url
      Option(new Repo(user, repo))
    } catch {
      case _:MatchError => Option.empty
    }
  }

  // Fancy shizz! https://www.playframework.com/documentation/2.2.x/ScalaJsonCombinators
  implicit val repoWrites:Format[Repo] = (
      (JsPath \ "user").format[String] and
      (JsPath \ "repo").format[String]
    )(Repo.apply, unlift(Repo.unapply))

}
