import sbt._

/**
 * @author Young Gu
 */
object Dependencies {

  val scalaParser = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.3"
  val scalaXml = "org.scala-lang.modules" %% "scala-xml" % "1.0.3"

  val scalalogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0"
  val log4j = "org.slf4j" % "slf4j-log4j12" % "1.7.8"

  val scalateUtil = "org.scalatra.scalate" %% "scalate-util" % "1.7.0" notTransitive()
  val scalateCore = "org.scalatra.scalate" %% "scalate-core" % "1.7.0" notTransitive()

  val webjars="org.webjars" %% "webjars-play" % "2.3.0-3"
  val playSlick="com.typesafe.play" %% "play-slick" % "1.0.0"
  val playSlickEvolutions=  "com.typesafe.play" %% "play-slick-evolutions" % "1.0.0"

  val scalatest = "org.scalatest" %% "scalatest" % "2.2.4"
  val scalatestPlay = "org.scalatestplus" %% "play" % "1.4.0-M3"
  val seleniumJava="org.seleniumhq.selenium" % "selenium-java" % "2.45.0"

  val h2="com.h2database" % "h2" % "1.4.177"




  def compile(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "compile")

  def provided(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "provided")

  def test(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "test")

  def it(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "it")

  def runtime(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "runtime")

  def container(deps: ModuleID*): Seq[ModuleID] = deps map (_ % "container")
}
