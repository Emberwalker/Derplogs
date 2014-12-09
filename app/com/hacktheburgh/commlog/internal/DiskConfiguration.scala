package com.hacktheburgh.commlog.internal

import play.api.{Configuration, Play}

/**
 * Config controller.
 *
 * @author Arkan <arkan@drakon.io>
 */
object DiskConfiguration {

  private var rootURL = "http://127.0.0.1:9000"
  private var yoConf = new YoConfig
  private val debugEnv = sys.props.contains("DEBUG") // TODO: CHANGEME!

  def getRootURL:String = rootURL
  def getYoConf:YoConfig = yoConf
  def isDebugEnv:Boolean = debugEnv

  def loadConfig() {
    val conf = Play.current.configuration
    // App globals
    rootURL = conf.getString("commlogs.rooturl") getOrElse "http://127.0.0.1:9000"

    // Yo
    yoConf = new YoConfig(conf)
  }

  class YoConfig(val enabled:Boolean, val apiKey:String, val locationEnabled:Boolean, val lat:Double, val lng:Double,
                 val locationThreshold:Double) {

    // Import from HOCON config.
    def this(conf:Configuration) {
      this(
        conf.getBoolean("commlogs.yo.enabled") getOrElse false,
        conf.getString("commlogs.yo.key") getOrElse "",
        conf.getBoolean("commlogs.yo.verifylocation") getOrElse false,
        conf.getDouble("commlogs.yo.lat") getOrElse 0.0,
        conf.getDouble("commlogs.yo.lng") getOrElse 0.0,
        conf.getDouble("commlogs.yo.threshold") getOrElse 0.0
      )
    }

    // Lets avoid nulls.
    def this() {
      this(false, "", false, 0.0, 0.0, 0.0)
    }

  }

}
