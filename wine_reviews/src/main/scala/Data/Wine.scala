package Data

import java.io.StringReader

import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.types._
import Overview._
import org.apache.spark.storage.StorageLevel



/* Case class for dataset feature:
*  id, country,description,designation,points,price,province,
*  region_1,taster_name,taster_twitter_handle,title,variety,winery
*
*  !region_2 has been dropped without enough values.
*/
case class Wine(id: Integer, country: String, description: String, designation: String,
                points: Integer, price: Double, province: String,
                region_1: String, variety: String,
                winery: String)


object Wine extends App{

  /* Lazily create a SparkSession running locally */
  lazy val spark = {
    println("initial spark")
    SparkSession.builder()
      .appName("WineReivewsDataIngest")
      .master("local")
      .getOrCreate()
  }

  /* Schema for DataFrame */
  lazy val schema: StructType = new StructType(
    Array(StructField("id", IntegerType, true),
      StructField("country", StringType, true),
      StructField("description", StringType, true),
      StructField("designation", StringType, true),
      StructField("points", IntegerType, true),
      StructField("price", DoubleType, true),
      StructField("province", StringType, true),
      StructField("region_1", StringType, true),
      StructField("variety", StringType, true),
      StructField("winery", StringType, true))
  )

  /* Read and concatenate csv files with optimizer */
  val csv1_url = "src/main/resources/winemag-data_first150k.csv"
  val csv2_url = "src/main/resources/winemag-data-130k-v2.csv"

  // Create two threads for reading
  val t1 = new load()
  val t2 = new load()

  val wine= t1.loadData(spark, csv1_url, schema)
    .union(t2.loadData(spark, csv2_url, schema).drop("taster_name", "taster_twitter_handle"))
  // Drop the first unrelated 'id' feature
  val wineDF = wine.drop("id")
  // Output
  report(wineDF)
  // Persistence
  val dfPersist = wineDF.persist(StorageLevel.MEMORY_ONLY_SER)
  dfPersist.show(false)


  /**
   *  Get SparkSession.
   * @return  SparkSession
   */
  def getSpark = spark

  /**
   * Get all the wine data of DataFrame type.
   * @return  DataFrame
   */
  def getWineDF = wineDF
}


class load extends Thread {
  /**
   * Read data as DataFrame with Thread.
   * @param url   Data path
   * @return  Return DataFrame
   */
  def loadData(spark: SparkSession, url: String, schema: StructType): DataFrame ={
    lazy val df = spark.read.option("header", true).schema(schema).csv(url)
      .repartition(500)
    df
  }

  /**
   * Read data as DataFrame. (no Thread)
   * @param url   Data path
   * @return  Return DataFrame
   */
  def loadData2(spark: SparkSession, url: String, schema: StructType): DataFrame ={
    lazy val df = spark.read.option("header", true).schema(schema).csv(url)
      .repartition(500)
    df
  }

  /**
   * Drop the row containing null value. (optimizer)
   * @param row  Each sql row
   * @param schema
   * @return  Boolean for filter
   */
  def dropNa(row: Row, schema: StructType): Boolean ={
    if(row != null && row.length == schema.length) true
    else false
  }

}