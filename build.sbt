name          := "subscription-service"

version       := "0.1"

scalaVersion  := "2.10.3"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.1.4"
  val sprayV = "1.1.1"
  Seq(
    "io.spray"            %   "spray-can"     % sprayV,
    "io.spray"            %   "spray-routing" % sprayV,
    "io.spray"            %   "spray-testkit" % sprayV  % "test",
    "io.spray"            %%  "spray-json" % "1.2.5",
    "net.liftweb"         %% "lift-json" % "2.5.1",
    "org.json4s"          %% "json4s-native" % "3.2.4",
    "org.json4s"          %% "json4s-jackson" % "3.2.4",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaV   % "test",
    "org.specs2" %% "specs2" % "2.3.13" % "test",
    "log4j" % "log4j" % "1.2.16"
  )
}

libraryDependencies += "javax.mail" % "mail" % "1.4.7"

libraryDependencies += "org.squeryl" % "squeryl_2.10" % "0.9.5-6"

libraryDependencies += "commons-dbcp" % "commons-dbcp" % "1.3"

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.2.0"

libraryDependencies += "com.h2database" % "h2" % "1.3.174"

libraryDependencies += "junit" % "junit" % "4.11"

libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.31"

//Exclude folders
ideaExcludeFolders += ".idea"

ideaExcludeFolders += ".idea_modules"

Revolver.settings
//mainClass in (Compile, run) := Some("org.service.api.EmailSvcMain")
