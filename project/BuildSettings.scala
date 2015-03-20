import com.typesafe.sbt.SbtScalariform._
import sbt.Keys._
import sbt._

/**
 * @author Young Gu
 */
object BuildSettings {
  val VERSION = "0.0.1-SNAPSHOT"

  val lifecycle=addCommandAlias("install", ";test;it:test;publishLocal") ++ addCommandAlias("testing", ";test;it:test")

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

  import com.typesafe.sbt.web.Import
  import play.PlayImport.PlayKeys
  lazy val playCustomizedSettings =
    Seq(
      sourceDirectory in Compile <<= baseDirectory(_ / "src" / "main"),
      sourceDirectory in Test <<= baseDirectory(_ / "src" / "test"),
      scalaSource in Compile <<= (sourceDirectory in Compile)(_ / "scala"),
      scalaSource in Test <<= (sourceDirectory in Test)(_ / "scala"),
      javaSource in Compile <<= (sourceDirectory in Compile)(_ / "java"),
      javaSource in Test <<= (sourceDirectory in Test)(_ / "java"),
      resourceDirectory in Compile <<= (sourceDirectory in Compile)(_ / "resources"),
      PlayKeys.confDirectory <<= ((sourceDirectory in Compile))(_ / "conf"),
      unmanagedResourceDirectories in Compile += (sourceDirectory in Compile).value / "conf",
      resourceDirectory in Import.Assets <<=  (sourceDirectory in Compile)(_ / "public"),
      resourceDirectory in Import.TestAssets <<= (sourceDirectory in Test)(_ / "public")
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
