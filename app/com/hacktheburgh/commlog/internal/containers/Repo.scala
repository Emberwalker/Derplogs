package com.hacktheburgh.commlog.internal.containers

/**
 * Represents a single repository entry.
 *
 * @author Arkan <arkan@drakon.io>
 */
class Repo(val user:String, val repo:String) {}

object Repo {

  def fromURL(url:String): Option[Repo] = {
    val URL = """^https?://(?:www.)?github.com/(\\w+|\\d+)/(\\w+|\\d+)/?$""".r
    val URL(user, repo) = url

    if (user.length == 0 || repo.length == 0)
      Option.empty
    else
      Option(new Repo(user, repo))
  }

}