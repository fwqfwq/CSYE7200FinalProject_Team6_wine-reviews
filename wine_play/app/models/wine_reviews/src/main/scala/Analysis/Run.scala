package models.wine_reviews.src.main.scala.Analysis

import java.nio.file.{Files, Paths}

import models.wine_reviews.src.main.scala.Analysis.TFIDF_RFModel.getProcessedDF
import models.wine_reviews.src.main.scala.Data.Wine.getSpark


object Run extends App {

  /* Get SparkSession and DataFrame */
  val spark = getSpark

  // Check if JSON file exists
  val json_url = "csvdata/tfidfJSON"
//  val jsonDF = // Get result of TF-IDF
//    if (Files.exists(Paths.get(json_url))) {
//      spark.read.json("csvdata/tfidf.json")
//    }
//    else getProcessedDF
  val jsonDF = getProcessedDF// Get result of TF-IDF

  jsonDF.show(10)



}
