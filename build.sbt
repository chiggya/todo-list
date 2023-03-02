val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "todo-list",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.15" % "test",
      "org.typelevel" %% "cats-effect" % "3.4.7",
      "org.typelevel" %% "cats-core" % "2.9.0"
      
    )   )
