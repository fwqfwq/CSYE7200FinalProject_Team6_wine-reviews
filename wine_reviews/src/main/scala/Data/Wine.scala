package Data

import Data.Preprocessing._
import Data.Schema3._
import Data.WriteCSV.checkFilePath
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types._
import Overview._
//import org.apache.spark.storage.StorageLevel



/* Case class for dataset feature:
*  id, country,description,designation,points,price,province,
*  region_1,taster_name,taster_twitter_handle,title,variety,winery
*
*  !region_2 has been dropped without enough values.
*/
case class Wine(id: Integer, country: String, description: String, designation: String,
                points: Integer, price: Double, province: String,
                region_1: String, title: String, variety: String, winery: String)


object Wine {
//object Wine extends App {

  /* Lazily create a SparkSession running locally */
  lazy val spark: SparkSession = {
    println("initial spark")
    SparkSession.builder()
      .appName("WineReivews")
      .master("local")
      .getOrCreate()
  }

  /* Schema for DataFrame */
  // !Defined in object Schema3

  /* Preprocess types, see more in object Preprocessing */
  val preprocType: Array[String] = Array("1", "DP", "2")

  // !Select everytime
  val preprocc: String = preprocType(1)

  lazy val wineDF: DataFrame =
  /* Check file first */
    if (!checkFilePath(preprocc)) {
      println("processing data..")
      /* Read and concatenate csv files */
      val csv1_url = "src/main/resources/winemag-data_first150k.csv"
      val csv2_url = "src/main/resources/winemag-data-130k-v2.csv"

      // Create two threads for reading
      val t1 = new load()
      val t2 = new load()

      // Concatenate two dataframes
      val wine1 = t1.loadData(spark, csv1_url, schema1)
        .union(t2.loadData(spark, csv2_url, schema2).drop("taster_name", "taster_twitter_handle"))

      // Drop the first non-ordered 'id' feature
      val wine2 = wine1.drop("id")

      // Overview before preprocessing
      //report(wine2, spark)

      /* Rpreproc() doing the preprocessing, return a DataFrame */
      //preproc1(wine2)   // a rough way
      //preprocDP(wine2)
      preproc2(wine2)
    }
    else {
      /* Directly call the generated data into DataFrame */
      println("searching for data..")
      val t = new load()
      val csv_url = "csvdata/preproc%s.csv"
      val re = preprocc match {
        case "1" => t.loadData(spark, csv_url.format(preprocc), schema3_1)
        case "DP" => t.loadData(spark, csv_url.format(preprocc), schema3_DP)
        case "2" => t.loadData(spark, csv_url.format(preprocc), schema3_2)
      }
      println("data found.")
      re
    }

  // Overview the data
  //report(wineDF, spark)

  // Persistence
  //  val dfPersist = wineDF.persist(StorageLevel.MEMORY_ONLY_SER)
  //  dfPersist.show(false)

  /**
   *  Get SparkSession.
   * @return  SparkSession
   */
  def getSpark: SparkSession = spark

  /**
   * Get all the wine data of DataFrame type.
   * @return  DataFrame
   */
  def getWineDF: DataFrame = wineDF
}


class load extends Thread {
  /**
   * Read data as DataFrame with Thread.
   * @param url   Data path
   * @return  Return DataFrame
   */
  def loadData(spark: SparkSession, url: String, schema: StructType): DataFrame ={
    lazy val df = spark.read.option("header", value = true).schema(schema).csv(url)
      .repartition(500)
//      .filter()
    df
  }

  /**
   * Read data as DataFrame. (no Thread)
   * @param url   Data path
   * @return  Return DataFrame
   */
  def loadData2(spark: SparkSession, url: String, schema: StructType): DataFrame ={
    lazy val df = spark.read.option("header", value = true).schema(schema).csv(url)
      .repartition(500)
    df
  }

  /**
   * Drop the row containing null value. (optimizer)
   * @param row  Each sql row
   * @param schema StructType, for DF
   * @return  Boolean for filter
   */
  def dropNa(row: Row, schema: StructType): Boolean ={
    if(row != null && row.length == schema.length) true
    else false
  }

}