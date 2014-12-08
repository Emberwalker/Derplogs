package com.hacktheburgh.commlog.messages

import play.api.libs.json.Json

/**
 * Container for a single repository commit.
 *
 * @author Arkan <arkan@drakon.io>
 */
class Commit(val msg:String, val repo:String, val committer:String) {

  val data = Map("msg" -> msg, "repo" -> repo, "committer" -> committer)
  val json = Json.stringify(Json.toJson(data))

  override def toString = s"Commit[$repo/$committer: $msg]"

}
