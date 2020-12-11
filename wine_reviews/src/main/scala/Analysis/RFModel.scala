package Analysis

import java.nio.file.{Files, Paths}

import Analysis.TFIDFModel._
import Data.Wine.getSpark
import org.apache.spark.ml.regression.RandomForestRegressor
import org.apache.spark.sql.Row

object RFModel extends App {

  /* Get SparkSession and DataFrame */
  val spark = getSpark

  // Check if JSON file exists
  val json_url = "csvdata/tfidfJSON"
  val jsonDF = // Get result of TF-IDF
    if (Files.exists(Paths.get(json_url))) {
      spark.read.json("csvdata/tfidf.json")
    }
    else getProcessedDF

//  jsonDF.show(10)

  // Drop nno use cols
  val df = jsonDF.drop("description", "words")
  df.show(10)

  df.select("descFeatures").take(10).foreach(println)
//  val a: Row = df.select("rawFeatures").head
//  println(a)
//  val b: Any = a(0)
//  println(b)
//  val c = b._2
//  println(c)
//
//  a.getList()

  //  //PCA
  //  val pca = new PCA()
  //    .setInputCol("features")
  //    .setOutputCol("pcaFeatures")
  //    .setK(10)
  //    .fit(featurizedData)
  //
  //  val result = pca.transform(featurizedData)
  //  println("After PCA: ")
  //  result.show


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
