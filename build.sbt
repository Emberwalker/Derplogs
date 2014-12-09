name := "derplogs"

version := "1.0"

lazy val `derplogs` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws )

resolvers ++= Seq(
  // For Hasher
  "RoundEights" at "http://maven.spikemark.net/roundeights"
)

libraryDependencies ++= Seq(
  "com.roundeights" %% "hasher" % "1.0.0",
  "net.databinder.dispatch" %% "dispatch-core" % "0.11.2"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  