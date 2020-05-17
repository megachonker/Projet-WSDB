name := """play-java-seed"""
organization := "com.example"

version := "1.0-SNAPSHOT"

<<<<<<< HEAD
lazy val root = (project in file(".")).enablePlugins(PlayJava)
=======
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

>>>>>>> 09183d0... Ajout de la page contact et suppléments

scalaVersion := "2.13.2"

libraryDependencies += guice


//pour persistance
<<<<<<< HEAD
//lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)
=======
//lazy val myProject = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

>>>>>>> 09183d0... Ajout de la page contact et suppléments
