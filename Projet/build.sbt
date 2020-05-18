name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
.settings(
    name := "play-java-ebean-example",
    version := "1.0.0-SNAPSHOT",
    scalaVersion := "2.13.1",
    libraryDependencies ++= Seq(
      guice,
      jdbc,
      "com.h2database" % "h2" % "1.4.199",
    ),
  )


scalaVersion := "2.13.1"

libraryDependencies += guice


//pour persistance
//lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

