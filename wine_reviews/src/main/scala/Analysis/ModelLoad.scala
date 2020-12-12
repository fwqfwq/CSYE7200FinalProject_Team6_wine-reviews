package Analysis

import org.apache.spark.ml.evaluation.ClusteringEvaluator

object ModelLoad {
  val loadModel = ClusteringEvaluator.load("kmean.model")

  val text = "US,90,32.0,California,Santa Lucia Highlands,Syrah,Novy"
  // default: points


}
