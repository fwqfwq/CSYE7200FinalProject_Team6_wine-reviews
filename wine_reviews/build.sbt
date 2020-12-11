name := "wine_reviews"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  //scala
  "org.scalatest" %% "scalatest" % "3.1.0" % "test",
  "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
  "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1",
  //spark
  "org.apache.spark" %% "spark-core" % "2.4.4",
  "org.apache.spark" %% "spark-sql" % "2.4.4",
  "org.apache.spark" %% "spark-streaming" % "2.4.0" % "provided",
  "org.apache.spark" %% "spark-mllib" % "2.4.0",
  "org.apache.spark" %% "spark-hive" % "2.4.4",
//  "org.apache.spark" %% "spark-graphx" % "2.4.3",
  //csv
  "com.opencsv" % "opencsv" % "4.4",
  //akka
  "com.typesafe.akka" %% "akka-actor" % "2.6.9"

)