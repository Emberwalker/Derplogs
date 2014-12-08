package com.hacktheburgh.commlog.github

import scala.language.postfixOps
import scala.util.Random

import com.roundeights.hasher.Implicits._

/**
 * Handler for PubSub interactions with GitHub.
 *
 * @author Arkan <arkan@drakon.io>
 */
object PubSubAgent {

  // This secret has to persist for the whole session
  val __sekrit:String = Random.nextString(16).md5

  def __checkSignature(msg:String, hash:String): Boolean = msg.hmac(__sekrit).sha1 == hash

}
