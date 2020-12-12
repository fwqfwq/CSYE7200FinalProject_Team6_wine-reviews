package Analysis


import Data.Wine.{getSpark, getWineDF}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.feature.{IDFModel, PCA, PCAModel}
import org.apache.spark.ml.regression.{RandomForestRegressionModel, RandomForestRegressor}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.UserDefinedFunction
//import Data.WriteCSV.writecsv
import org.apache.spark.ml.feature.{HashingTF, IDF, StopWordsRemover, Tokenizer}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame


/* TF-IDF
* Term Frequency / Inverse Document Frequency
*/
object TFIDF_RFModel {
//  object TFIDFModel extends App {

  /* Get SparkSession and DataFrame */
  val wineDF: DataFrame = getWineDF // set to "DP", index 1
  val spark: SparkSession = getSpark

  val df: DataFrame = wineDF.na.drop()
  //println(df.count())   // 280839 non-null value
  df.show(10)


  /* Features Processing */

  // Tokenize
  val tokenizer: Tokenizer = new Tokenizer()
    .setInputCol("description")
    .setOutputCol("words")
  val countTokens: UserDefinedFunction = udf { (words: Seq[String]) => words.length }
  val tokenized: DataFrame = tokenizer.transform(df)
//  dataShow("tokenizer", tokenized)


  // Stopwords Removal
  val remover: DataFrame = new StopWordsRemover()
    .setInputCol("words")
    .setOutputCol("filtered")
    .transform(tokenized)
//  dataShow("remover", remover)


  // TF-IDF
  // Transformer, HashingTF
  val hashingTF: HashingTF = new HashingTF()
    .setInputCol("filtered")
    .setOutputCol("rawFeatures")
    .setNumFeatures(20)
  val featurizedData: DataFrame = hashingTF.transform(remover).drop("description", "words")
//  dataShow("featurized", featurizedData)


  // Estimator, IDF
  val idf: IDF = new IDF()
    .setInputCol("rawFeatures")
    .setOutputCol("descFeatures")
  val idfModel: IDFModel = idf.fit(featurizedData)


  // Rescaled
  val rescaledData: DataFrame = idfModel.transform(featurizedData)
//  dataShow("rescaled", rescaledData)


  //PCA
  val pca: PCAModel = new PCA()
    .setInputCol("descFeatures")
    .setOutputCol("features")
//    .setOutputCol("pcaFeatures")
    .setK(10)
    .fit(rescaledData)

  val result: DataFrame = pca.transform(rescaledData)
  println("After PCA: ")
  result.show

  // Save to JSON file
//  result.coalesce(1).write.json("csvdata/tfidfJSON")


//  result.select("pcaFeatures").take(10).foreach(println)


  /* RF Model */
  val data: DataFrame = result.select("points", "features")
  // Split the train and the test 7/3
  val Array(train, test) = data.randomSplit(Array(0.7, 0.3))

  // Create the RF regressor
  val rf: RandomForestRegressionModel = new RandomForestRegressor()
    .setLabelCol("points")
    .fit(train)



  val predictions: DataFrame = rf.transform(test)

//  predictions.select("prediction", "points", "predFeatures").show
  predictions.show()

  // Select (prediction, true label) and compute test error.
  val evaluator = new RegressionEvaluator()
    .setLabelCol("points")
    .setPredictionCol("prediction")
    .setMetricName("mae")

  val mse = evaluator.evaluate(predictions)
  println("Mean Squared Error (MSE) on test data = " + mse)

  predictions.summary()

  rf.save("rf.model")

  /**
   * Output.
   * @param s String of each stage
   * @param df Datafram that generated
   */
  def dataShow(s: String, df: DataFrame): Unit = {
      println("After %s: ".format(s))
      df.show
  }

  /**
   * Get result DataFrame - rescaled data
   * @return Return a Dataframe ready for prediction - RF
   */
  def getProcessedDF: DataFrame =  result
}


