import play.sbt.routes.RoutesKeys
import scoverage.ScoverageKeys
import uk.gov.hmrc.DefaultBuildSettings.{defaultSettings, scalaSettings}
import uk.gov.hmrc.sbtdistributables.SbtDistributablesPlugin.publishingSettings
import uk.gov.hmrc.versioning.SbtGitVersioning
import uk.gov.hmrc.versioning.SbtGitVersioning.autoImport.majorVersion
import uk.gov.hmrc.SbtAutoBuildPlugin

val appName = "request-corporation-tax-number-frontend"
lazy val appDependencies: Seq[ModuleID] = AppDependencies.all
lazy val plugins : Seq[Plugins] = Seq(play.sbt.PlayScala, SbtAutoBuildPlugin, SbtGitVersioning, SbtDistributablesPlugin)
lazy val playSettings : Seq[Setting[_]] = Seq.empty

lazy val scoverageSettings =
  Seq(ScoverageKeys.coverageExcludedFiles := "<empty>;Reverse.*;.*filters.*;.*handlers.*;.*components.*;.*models.*;.*repositories.*;" +
    ".*BuildInfo.*;.*javascript.*;.*FrontendAuditConnector.*;.*Routes.*;.*GuiceInjector;.*DataCacheConnector;" +
    ".*ControllerConfiguration;.*LanguageSwitchController",
    ScoverageKeys.coverageMinimum := 75,
    ScoverageKeys.coverageFailOnMinimum := true,
    ScoverageKeys.coverageHighlighting := true
  )

lazy val microservice = Project(appName, file("."))
  .enablePlugins(plugins : _*)
  .settings(playSettings,
    RoutesKeys.routesImport ++= Seq("models._"),
    TwirlKeys.templateImports ++= Seq(
      "play.twirl.api.HtmlFormat",
      "play.twirl.api.HtmlFormat._",
      "views.html.layouts.GovUkTemplate",
      "uk.gov.hmrc.play.views.html.helpers._",
      "uk.gov.hmrc.play.views.html.layouts._"
    ),
    parallelExecution in Test := false,
    scalaSettings,
    publishingSettings,
    defaultSettings(),
    scoverageSettings,
    scalacOptions ++= Seq("-feature"),
    libraryDependencies ++= appDependencies,
    dependencyOverrides += "commons-codec" % "commons-codec" % "1.12",
    retrieveManaged := true,
    PlayKeys.playDefaultPort := 9200,
    evictionWarningOptions in update := EvictionWarningOptions.default.withWarnScalaVersionEviction(false),
    resolvers ++= Seq(
    Resolver.bintrayRepo("hmrc", "releases"),
    Resolver.jcenterRepo,
    "hmrc-releases" at "https://artefacts.tax.service.gov.uk/artifactory/hmrc-releases/",
    Resolver.bintrayRepo("emueller", "maven")),

    // concatenate js
    Concat.groups := Seq(
      "javascripts/requestcorporationtaxnumberfrontend-app.js" -> group(Seq("javascripts/show-hide-content.js", "javascripts/requestcorporationtaxnumberfrontend.js", "javascripts/timeoutDialog.js"))
    ),
    // prevent removal of unused code which generates warning errors due to use of third-party libs
    uglifyCompressOptions := Seq("unused=false", "dead_code=false"),
    pipelineStages := Seq(digest),
    // below line required to force asset pipeline to operate in dev rather than only prod
    pipelineStages in Assets := Seq(concat,uglify),
    // only compress files generated by concat
    includeFilter in uglify := GlobFilter("requestcorporationtaxnumberfrontend-*.js")
  )
  .disablePlugins(JUnitXmlReportPlugin)
  .settings(majorVersion := 1)
  scalaVersion := "2.12.12"

// Silence unused import in views and routes
val silencerVersion = "1.7.0"
libraryDependencies ++= Seq(
  compilerPlugin("com.github.ghik" % "silencer-plugin" % silencerVersion cross CrossVersion.full),
  "com.github.ghik" % "silencer-lib" % silencerVersion % Provided cross CrossVersion.full
)

scalacOptions ++= Seq(
  "-P:silencer:pathFilters=views;routes"
)
