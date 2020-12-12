package models.wine_reviews.src.main.scala

import org.apache.spark.ml.feature.{HashingTF, IDF, IDFModel, StopWordsRemover, Tokenizer}
import org.apache.spark.sql.{DataFrame, SparkSession}
import models.wine_reviews.src.main.scala.Analysis.ModelLoad.{getModel, loadModel}
import models.wine_reviews.src.main.scala.Data.Wine.getSpark
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object Main {

  lazy val spark: SparkSession = getSpark


  def doPredict(describe: Seq[String])= {
    import spark.implicits._

    val desc: String = describe.mkString(" ")
    val desDF: DataFrame = spark.sqlContext.sparkContext
      .parallelize(List(desc)).toDF("desc")

    // Tokenize
    val tokenizer: Tokenizer = new Tokenizer()
      .setInputCol("desc")
      .setOutputCol("words")
//      val countTokens: UserDefinedFunction = udf { (words: Seq[String]) => words.length }
    val tokenized: DataFrame = tokenizer.transform(desDF)

    // Stopwords Removal
    val remover: DataFrame = new StopWordsRemover()
      .setInputCol("words")
      .setOutputCol("filtered")
      .transform(tokenized)

    // TF-IDF
    // Transformer, HashingTF
    val hashingTF: HashingTF = new HashingTF()
      .setInputCol("filtered")
      .setOutputCol("rawFeatures")
      .setNumFeatures(20)
    val featurizedData: DataFrame = hashingTF.transform(remover).drop("description", "words")


    // Estimator, IDF
    val idf: IDF = new IDF()
      .setInputCol("rawFeatures")
      .setOutputCol("descFeatures")
    val idfModel: IDFModel = idf.fit(featurizedData)


    // Rescaled
    val rescaledData: DataFrame = idfModel.transform(featurizedData)
    //  dataShow("rescaled", rescaledData)

    val model = getModel()
    val result = model.transform(rescaledData).select("prediction")
    result.collect.map(_.toSeq.map(_.toString))

  }

    def trendyWine()={
      val results = Seq(s"Some trendy for description",s"Some trendy for price",s"Some trendy for region")
      results
    }
   def overallAnalysis()={
    val results = Seq(s"Some analysis ",s"Some analysis ",s"Some analysis ")
    results
  }

}
