import com.typesafe.sbt.SbtScalariform._
import sbt.Keys._
import sbt._

/**
 * @author Young Gu
 */
object BuildSettings {
  val VERSION = "0.0.1-SNAPSHOT"

  val lifecycle = addCommandAlias("install", ";test;it:test;publishLocal") ++ addCommandAlias("testing", ";test;it:test")

  val basicSettings = Defaults.coreDefaultSettings ++ lifecycle ++ Seq(
    version := VERSION,
    homepage := Some(new URL("https://lifecosys.com/developer/play-project-template")),
    organization := "com.lifecosys",
    organizationHomepage := Some(new URL("https://lifecosys.com")),
    description := "play-project-template",
    startYear := Some(2015),
    scalaVersion := "2.11.5",
    //NOTE:https://github.com/typesafehub/scalalogging/issues/23LoggerFactory
    parallelExecution in Test := false,
    scalacOptions := Seq(
      "-encoding", "utf8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-target:jvm-1.6",
      "-language:postfixOps",
      "-language:implicitConversions",
      "-Xlog-reflective-calls"
    )
  )

  import net.virtualvoid.sbt.graph.Plugin._

  lazy val projectBuildSettings = basicSettings ++ formattingSettings ++ graphSettings ++
    Seq(
      publishMavenStyle := true
    )

  import play.sbt.routes.RoutesKeys._
  import com.typesafe.sbt.web.SbtWeb.autoImport._
  import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._
  lazy val playCustomizedSettings =
    Seq(
      routesGenerator := InjectedRoutesGenerator,

    //Reset the project layout to sbt standard. Currently, we use disablePlugins(PlayLayoutPlugin)

//      sourceDirectory in Compile := baseDirectory.value / "src" / "main",
//      sourceDirectory in Test := baseDirectory.value / "src" / "test",
//
//      scalaSource in Compile := (sourceDirectory in Compile).value / "scala",
//      scalaSource in Test := (sourceDirectory in Test).value / "scala",
//
//      javaSource in Compile := (sourceDirectory in Compile).value / "java",
//      javaSource in Test := (sourceDirectory in Test).value / "java",
//
//      resourceDirectory in Compile <<= (sourceDirectory in Compile)(_ / "resources"),
//
//      // sbt-web
//      sourceDirectory in Assets := (sourceDirectory in Compile).value / "assets",
//      sourceDirectory in TestAssets := (sourceDirectory in Test).value / "assets",
//      resourceDirectory in Assets := (sourceDirectory in Compile).value / "public",

      // Native packager
      sourceDirectory in Universal := target.value / "dist"

    )

  lazy val itSettings = Defaults.itSettings ++ Seq(
    fork in IntegrationTest := true,
    javaOptions in(IntegrationTest, run) += "-Xms256m -Xmx512m -XX:ReservedCodeCacheSize=128m -XX:MaxMetaspaceSize=256m"
  )


  lazy val noPublishing = seq(
    publish :=(),
    publishLocal :=()
  )


  lazy val siteSettings = basicSettings ++ formattingSettings ++ noPublishing

  lazy val docsSettings = basicSettings ++ noPublishing ++ seq(
    unmanagedSourceDirectories in Test <<= baseDirectory {
      _ ** "code" get
    }
  )

  import scalariform.formatter.preferences._

  val formattingSettings = scalariformSettings ++ Seq(
    ScalariformKeys.preferences := ScalariformKeys.preferences.value
      .setPreference(RewriteArrowSymbols, true)
      .setPreference(AlignParameters, true)
      .setPreference(AlignSingleLineCaseStatements, true)
      .setPreference(DoubleIndentClassDeclaration, true)
      .setPreference(PreserveDanglingCloseParenthesis, true))

}
