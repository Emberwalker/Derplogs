package com.hacktheburgh.commlog.internal

/**
 * Config controller.
 *
 * @author Arkan <arkan@drakon.io>
 */
object Configuration {

  private var rootURL = "http://127.0.0.1:9000"
  private var debugEnv = sys.props.contains("DEBUG") // TODO: CHANGEME!

  def getRootURL:String = rootURL

  def isDebugEnv:Boolean = debugEnv

  // TODO: Load things.

}
