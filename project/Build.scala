import play.sbt._
import play.sbt.routes.RoutesKeys._
import sbt.Keys._
import sbt._

/**
 * @author Young Gu
 */
object ProjectBuild extends Build {

  import BuildSettings._
  import Dependencies._

  lazy val root = Project("PROJECT-NAME", file("."))
    .aggregate(core, web, `web-fun-tests`)
    .settings(basicSettings: _*)
    .settings(noPublishing: _*)

  lazy val core = Project("core", file("core"))
    .settings(projectBuildSettings: _*)
    .settings(libraryDependencies ++=
    compile(scalaParser, scalaXml, scalateUtil, scalateCore, h2,scalalogging, log4j) ++
      test(scalatest)
    )


  lazy val web = Project("web", file("web")).enablePlugins(PlayScala).disablePlugins(PlayLayoutPlugin)
    .dependsOn(core % "compile->compile;test->test;it->test")
    .configs(IntegrationTest)
    .settings(projectBuildSettings: _*)
        .settings(playCustomizedSettings: _*)
    .settings(itSettings: _*)
    .settings(routesGenerator := InjectedRoutesGenerator: _*)
    .settings(libraryDependencies ++=
    compile(webjars, playSlick,playSlickEvolutions)
    )


  /**
   * We can create a fun config similar as it testing within site module, but unfortunately, the idea can't recognize the configuration when import from sbt,
   * so we have to create this module. we can remove this module after idea add customized testing configuration support.
   */
  lazy val `web-fun-tests` = Project("web-fun-tests", file("web-fun-tests"))
    .dependsOn(web % "compile->compile;test->test;it->test")
    .configs(IntegrationTest)
    .settings(projectBuildSettings: _*)
    .settings(itSettings: _*)
    .settings(libraryDependencies ++=
    it(scalatestPlay,seleniumJava) ++ test(scalatestPlay,seleniumJava) //We also add it dependency to test since idea can't recognize it when import.
    )


}
