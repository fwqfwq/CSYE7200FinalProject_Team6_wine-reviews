package models.wine_reviews.src.main.scala.Analysis

import models.wine_reviews.src.main.scala.Data.Overview
import models.wine_reviews.src.main.scala.Data.Wine.{getWineDF, getSpark}

object SQLAnalysis extends App {

  val wineDF = getWineDF
  val spark = getSpark
  Overview.report(wineDF, spark)

  // Function for select
  def sqlAvg = "SELECT %s, AVG(%s) AS %s FROM %s GROUP BY %s ORDER BY %s DESC"
  def sqlMM = "SELECT %s, MAX(%s) AS %s, MIN(%s) AS %s FROM %s GROUP BY %s ORDER BY %s DESC"

  def sqlExecAvg(strl: Array[String]): Unit ={
    spark.sql(sqlAvg.format(strl(0), strl(1), strl(2), strl(3), strl(4), strl(5))).take(20).foreach(println)
  }

  def sqlExecMM(strl: Array[String]): Unit ={
    spark.sql(sqlMM.format(strl(0), strl(1), strl(2), strl(3), strl(4),
       strl(5), strl(6), strl(7))).take(20).foreach(println)
  }

  wineDF.createOrReplaceTempView("wine")
  println("Country Average Price")
  sqlExecAvg(Array("country", "price", "AvgPrice", "wine", "country", "AvgPrice"))
  println("Country Average Point")
  sqlExecAvg(Array("country", "points", "AvgPoint", "wine", "country", "AvgPoint"))

  println("Country Min/Max Point")
  sqlExecMM(Array("country", "points", "AvgPoint", "wine", "country", "AvgPoint"))
  println("Country Min/Max Point")
  sqlExecMM(Array("country", "points", "AvgPoint", "wine", "country", "AvgPoint"))



}
