import sbt.Keys._
import sbt._
import play.PlayScala

/**
 * @author Young Gu
 */
object ProjectBuild extends Build {

  import BuildSettings._
  import Dependencies._

  lazy val root = Project("play-project-template", file("."))
    .aggregate(core, web, `web-functional-tests`)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)

  lazy val core = Project("core", file("core"))
    .settings(projectBuildSettings: _*)
    .settings(libraryDependencies ++=
      compile(scalaParser, scalaXml, scalateUtil, scalateCore, scalalogging, log4j) ++
      test(scalatest)
    )


  lazy val web = Project("web", file("web")).enablePlugins(PlayScala)
    .dependsOn(core % "compile->compile;test->test;it->test")
    .configs(IntegrationTest)
    .settings(projectBuildSettings: _*)
    .settings(playCustomizedSettings: _*)
    .settings(itSettings: _*)
    .settings(libraryDependencies ++=
    compile(webjars,playSlick)
    )


  /**
   * We can create a fun config similar as it testing within site module, but unfortunately, the idea can't recognize the configuration when import from sbt,
   * so we have to create this module. we can remove this module after idea add customized testing configuration support.
   */
  lazy val `web-functional-tests` = Project("web-fun-tests", file("web-fun-tests"))
    .dependsOn(web % "compile->compile;test->test;it->test")
    .configs(IntegrationTest)
    .settings(projectBuildSettings: _*)
    .settings(itSettings: _*)
    .settings(libraryDependencies ++=
    it(seleniumJava) ++ test(seleniumJava)//We also add it dependency to test since idea can't recognize it when import.
    )


}
