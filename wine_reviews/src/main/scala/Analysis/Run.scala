package Analysis

import java.nio.file.{Files, Paths}

import Analysis.TFIDF_RFModel._
import Data.Wine.getSpark
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.ml.feature.PCA
import org.apache.spark.sql.Row

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


//
//  df.select("descFeatures").take(10).foreach(println)
//  println()
//  df.select("rawFeatures").take(10).foreach(println)
//  val newDF = df.select("descFeatures")






//  val df = wineDF.na.drop()
//  //println(df.count())   // 280839 non-null value
//  df.show(10)

//  // Split the train and the test
//  val Array(train, test) = df.randomSplit(Array(0.7, 0.3))
//
//  // Create the RF regressor
//  val rf = new RandomForestRegressor()
//    .setLabelCol("points")
//    .fit(train)
//
//  val predictions = rf.transform(test)
//
//  predictions.select("prediction", "points", "predFeatures").show
//

}
