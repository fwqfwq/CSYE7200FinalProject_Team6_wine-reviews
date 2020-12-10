package Analysis

import Data.Wine
import org.apache.spark
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{FeatureHasher, HashingTF, IDF, NGram, OneHotEncoderEstimator, PCA, StopWordsRemover, StringIndexer, Tokenizer, VectorAssembler}
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.regression.{RandomForestRegressionModel, RandomForestRegressor}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, Row}

import scala.util.{Success, Try}


object Model extends App {

  val df = Wine.getWineDF

//  val countryIndexer = new StringIndexer().setInputCol("country").setOutputCol("countryIndexted")
//  val designationIndexer = new StringIndexer().setInputCol("designation").setOutputCol("designationIndexted")
//  val provinceIndexer = new StringIndexer().setInputCol("province").setOutputCol("provinceIndexted")
//  val regionIndexer = new StringIndexer().setInputCol("region_1").setOutputCol("regionIndexted")
//  val varietyIndexer = new StringIndexer().setInputCol("variety").setOutputCol("varietyIndexted")
//  val wineryIndexer = new StringIndexer().setInputCol("winery").setOutputCol("wineryIndexted")
//  val c = countryIndexer.fit(df).transform(df)
//  val d = designationIndexer.fit(c).transform(c)
//  val p = provinceIndexer.fit(d).transform(d)
//  val r = regionIndexer.fit(p).transform(p)
//  val v = varietyIndexer.fit(r).transform(r)
//  val w = wineryIndexer.fit(v).transform(v)
//  val indexedDF = df1.drop("country", "designation", "province", "region_1", "variety", "winery")
//  indexedDF.show
//  c.show

//  val encoder = new OneHotEncoderEstimator()
//    .setInputCols(Array("countryIndexed", "designationIndexed", "provinceIndexed", "region_1Indexed", "varietyIndexed", "wineryIndexed"))
//    .setOutputCols(Array("countryEncoded", "designationEncoded", "provinceEncoded", "region_1Encoded", "varietyEncoded", "wineryEncoded"))
//
//  val encodedDF = encoder.fit(indexedDF).transform(indexedDF)

  //Feature Harsher
  val hasher = new FeatureHasher()
    .setInputCols("country", "designation", "province", "region_1", "variety", "winery")
    .setOutputCol("features")

  val featurized = hasher.transform(df).drop("country", "designation", "province", "region_1", "variety", "winery")
//  featurized.show

  //Tokenize
  val tokenizer = new Tokenizer()
    .setInputCol("description").setOutputCol("words")
  val countTokens = udf { (words: Seq[String]) => words.length }
  val tokenized = tokenizer.transform(featurized)
//  tokenized.show

  //Stopwords Removal
  val remover = new StopWordsRemover()
    .setInputCol("words")
    .setOutputCol("filtered").transform(tokenized)
//  remover.show

  //TF-IDF
  val hashingTF = new HashingTF()
    .setInputCol("filtered").setOutputCol("rawFeatures").setNumFeatures(20)
  val featurizedData = hashingTF.transform(remover).drop("description", "words", "filtered")
//  featurizedData.show

//  val idf = new IDF().setInputCol("rawFeatures").setOutputCol("descFeatures")
//  val idfModel = idf.fit(featurizedData)
//
//  val rescaledData = idfModel.transform(featurizedData)
//  rescaledData.show

//  //PCA
//  val pca = new PCA()
//    .setInputCol("features")
//    .setOutputCol("pcaFeatures")
//    .setK(10)
//    .fit(featurizedData)
//
//  val result = pca.transform(featurizedData)
//  result.show



  val Array(train, test) = featurized.randomSplit(Array(0.8, 0.2))

  val rf = new RandomForestRegressor()
    .setLabelCol("points")
    .fit(train)

  val predictions = rf.transform(test)

  predictions.select("prediction", "points", "predFeatures").show

}