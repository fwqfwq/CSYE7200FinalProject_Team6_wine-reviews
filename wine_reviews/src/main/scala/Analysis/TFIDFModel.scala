package Analysis


import Data.Wine.{getSpark, getWineDF}
//import Data.WriteCSV.writecsv
import org.apache.spark.ml.feature.{FeatureHasher, HashingTF, IDF, StopWordsRemover, Tokenizer}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.DataFrame


/* TF-IDF
* Term Frequency / Inverse Document Frequency
*/
object TFIDFModel {
//  object TFIDFModel extends App {

  /* Get SparkSession and DataFrame */
  val wineDF = getWineDF // set to "DP", index 1
  val spark = getSpark

  val df = wineDF.na.drop()
  //println(df.count())   // 280839 non-null value
  df.show(10)

  // Tokenize
  val tokenizer = new Tokenizer()
    .setInputCol("description").setOutputCol("words")
  val countTokens = udf { (words: Seq[String]) => words.length }
  val tokenized = tokenizer.transform(df)
//  dataShow("tokenizer", tokenized)

  // Stopwords Removal
  val remover = new StopWordsRemover()
    .setInputCol("words")
    .setOutputCol("filtered").transform(tokenized)
//  dataShow("remover", remover)

  // TF-IDF
  // Transformer, HashingTF
  val hashingTF = new HashingTF()
    .setInputCol("filtered").setOutputCol("rawFeatures").setNumFeatures(20)
//  val featurizedData = hashingTF.transform(remover).drop("description", "words", "filtered")
  val featurizedData = hashingTF.transform(remover)
//  dataShow("featurized", featurizedData)

//  // Write out
//  writecsv(featurizedData, "hashingTF")

  // Estimator, IDF
  val idf = new IDF().setInputCol("rawFeatures").setOutputCol("descFeatures")
  val idfModel = idf.fit(featurizedData)

  // Rescaled
  val rescaledData = idfModel.transform(featurizedData)
  dataShow("rescaled", rescaledData)

  // Save to JSON file
  rescaledData.coalesce(1).write.json("csvdata/tfidfJSON")





//  rescaledData.select("descFeatures").take(10).foreach(println)


  // Sort results




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
  def getProcessedDF: DataFrame =  rescaledData
}


