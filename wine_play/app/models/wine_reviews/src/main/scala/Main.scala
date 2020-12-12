package models.wine_reviews.src.main.scala

import models.wine_reviews.src.main.scala.Analysis.KmeansModel
import models.wine_reviews.src.main.scala.Analysis.KmeansModel.{df, featurized, model}
import org.apache.spark.ml.feature.{FeatureHasher, HashingTF, IDF, IDFModel, PCA, PCAModel, StopWordsRemover, Tokenizer}
import org.apache.spark.sql.{DataFrame, SparkSession}
import models.wine_reviews.src.main.scala.Analysis.ModelLoad.{getModel, loadModel, loadModel2}
import models.wine_reviews.src.main.scala.Analysis.TFIDF_RFModel.{pca, rescaledData}
import models.wine_reviews.src.main.scala.Data.Wine.{getSpark, getWineDF}
import org.apache.spark.ml.clustering.KMeansModel
import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object Main {

  lazy val spark: SparkSession = getSpark
  import spark.implicits._


  def doPredict(describe: Seq[String])= {

    val desc: String = describe.mkString(" ")
    println(desc)
    val desDF: DataFrame = spark.sqlContext.sparkContext
      .parallelize(List(desc)).toDF("desc")
//    desDF.show()

    // Tokenize
    val tokenizer: Tokenizer = new Tokenizer()
      .setInputCol("desc")
      .setOutputCol("words")
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
      .setOutputCol("features")
    val idfModel: IDFModel = idf.fit(featurizedData)


    // Rescaled
    val rescaledData: DataFrame = idfModel.transform(featurizedData)
    //  dataShow("rescaled", rescaledData)
    rescaledData.show()



    val model = getModel()
    val result = model.transform(rescaledData).select("prediction")
    result.collect.map(_.toSeq.map(_.toString))



  }

    def recommendation(country: Seq[String], price: String, province: Seq[String], region_1: Seq[String], title: Seq[String], variety: Seq[String])={
      val data = spark.read.json("ReservedData/csvdata/km.json")

      val cty = country.mkString(" ")
      val pri = price.toDouble
      val prov = province.mkString(" ")
      val region = region_1.mkString(" ")
      val tit = title.mkString(" ")
      val vari = variety.mkString(" ")

      val df: DataFrame = spark.sqlContext.sparkContext
        .parallelize(List((cty, pri, prov, region, tit, vari))).toDF("country", "price", "province", "region_1", "title", "variety")



      // Feature Harsher - for the String-valued data, desides 'description'
      val hasher = new FeatureHasher()
        .setInputCols("country", "price", "province", "region_1", "title", "variety")
        .setOutputCol("features")

      val featurized = hasher.transform(df)
      //.drop("country", "points", "price", "province", "region_1", "title", "variety")

      val model = loadModel2

      val predictions = model.transform(featurized)
      val cluster: DataFrame = predictions.select("prediction")
      val num = cluster.head.getInt(0)
      predictions.show
      data.createOrReplaceTempView("wine")



      val sql = spark.sql("SELECT country, price, province, region_1, title, variety FROM wine WHERE prediction = \"%d\" ORDER BY points DESC LIMIT 10"
        .format(num))


      val result = sql.collect().map(_.toString).toSeq
      result.foreach(println)

      result


    }
   def overallAnalysis()={
    val results = Seq(s"Some analysis ",s"Some analysis ",s"Some analysis ")
    results
  }

}
