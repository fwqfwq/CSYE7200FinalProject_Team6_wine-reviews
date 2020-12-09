package Analysis

import Data.Overview
import Data.Wine.{getSpark, getWineDF}

object Analysis extends App {

  val wineDF = getWineDF
  val spark = getSpark
  Overview.report(wineDF)

  // Function for select
  def sqlAvg = "SELECT %s, AVG(%s) AS %s FROM %s GROUP BY %s ORDER BY %s DESC"
  def sqlMax = "SELECT %s, MAX(%s) AS %s FROM %s GROUP BY %s ORDER BY %s DESC"

  def sqlExec(strl: Array[String]): Unit ={
    spark.sql(sqlAvg.format(strl(0), strl(1), strl(2), strl(3), strl(4), strl(5))).take(20).foreach(println)
  }

  wineDF.createOrReplaceTempView("wine")
  println("Country Average Price")
  sqlExec(Array("country", "price", "AvgPrice", "wine", "country", "AvgPrice"))
  println("Country Average Point")
  sqlExec(Array("country", "points", "AvgPoint", "wine", "country", "AvgPoint"))

}
