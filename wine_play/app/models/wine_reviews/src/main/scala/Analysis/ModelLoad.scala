package models.wine_reviews.src.main.scala.Analysis

import org.apache.spark.ml.clustering.KMeansModel
import org.apache.spark.ml.evaluation.{ClusteringEvaluator, RegressionEvaluator}
import org.apache.spark.ml.regression.RandomForestRegressionModel

object ModelLoad {
  val loadModel = RandomForestRegressionModel.load("rf.model")
  val loadModel2 = KMeansModel.load("ReservedData/kmeans.model")

  val text = "US,90,32.0,California,Santa Lucia Highlands,Syrah,Novy"
  // default: points

  def getModel() = loadModel

}
