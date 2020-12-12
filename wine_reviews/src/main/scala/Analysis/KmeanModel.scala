package Analysis

import Analysis.TFIDF_RFModel.rescaledData
import Data.Wine.{getSpark, getWineDF}
import org.apache.spark.ml.clustering.KMeans
import org.apache.spark.ml.evaluation.ClusteringEvaluator
import org.apache.spark.ml.feature.{FeatureHasher, OneHotEncoder, OneHotEncoderEstimator, PCA, PCAModel, StringIndexer, Word2Vec}
import org.apache.spark.sql.DataFrame

object KmeanModel extends App {

  /* Get SparkSession and DataFrame */
  val spark = getSpark
  val df = getWineDF

  // Feature Harsher - for the String-valued data, desides 'description'
  val hasher = new FeatureHasher()
    .setInputCols("country", "price", "province", "region_1", "title", "variety")
    .setOutputCol("features")

  val featurized = hasher.transform(df).drop("country", "price", "province", "region_1", "title", "variety")
  println("After Harsher: ")
  featurized.show()
//  featurized.take(10).foreach(println)

//  s2index(Array("country", "price", "province", "region_1", "title", "variety"))
//
//  df.show()

//  val encoder = new OneHotEncoderEstimator()
//    .setInputCols(Array("country", "price", "province", "region_1", "title", "variety"))
//    .setOutputCols(Array("rCountry", "rPrice", "rProvince", "rRegion_1", "rTitle", "rVariety"))
////    .setDropLast(false)
//  val model = encoder.fit(df)
//  val encoded = model.transform(df)
//  encoded.show(false)

//  // PCA
//  val pca: PCAModel = new PCA()
//    .setInputCol("hashedFeatures")
//    .setOutputCol("features")
//    .setK(3)
//    .fit(featurized)
//  val result = pca.transform(featurized)
//  result.show()

//   Train a k-means model
  val kmeans = new KMeans().setK(12).setSeed(1L)
  val model = kmeans.fit(featurized)

  val predictions = model.transform(featurized)

  // Evaluate clustering by computing Within Set Sum of Squared Errors.
  val evaluator = new ClusteringEvaluator()

  val silhouette = evaluator.evaluate(predictions)

  // Shows the result.
  println("Cluster Centers: ")
  model.clusterCenters.take(10).foreach(println)

  model.save("kmean.model")


  val loadModel = ClusteringEvaluator.load("kmean.model")

//  def s2index(cols: Array[String]): DataFrame ={
//
//    for (col <- cols) {
//      val indexer = new StringIndexer()
//      .setInputCol(col)
//      .setOutputCol("r" + col)
//      .fit(df)
//
//      val indexed: DataFrame = indexer.transform(df)
//    }
//
//    indexed
//  }
}
