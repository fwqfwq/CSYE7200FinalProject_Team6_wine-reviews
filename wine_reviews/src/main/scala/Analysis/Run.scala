package Analysis

import java.nio.file.{Files, Paths}

import Analysis.TFIDF_RFModel._
import Data.Wine.getSpark
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.ml.feature.PCA
import org.apache.spark.sql.Row

object Run extends App {

  // Run object only for TF-IDF

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
