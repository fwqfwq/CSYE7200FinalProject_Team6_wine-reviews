package ingest

import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types._

import scala.collection.mutable
import scala.io.Source
import scala.util.Try


case class Wine(id: Integer, country: String, description: String, designation: String,
                points: Integer, price: Double, province: String,
                region_1: String, region_2: String, variety: String,
                winery: String)
//country,description,designation,points,price,province,
// region_1,region_2,taster_name,taster_twitter_handle,title,variety,winery

object Wine{

  lazy val schema: StructType = new StructType(
    Array(StructField("id", IntegerType, true),
    StructField("country", StringType, true),
    StructField("description", StringType, true),
    StructField("designation", StringType, true),
    StructField("points", IntegerType, true),
    StructField("price", DoubleType, true),
    StructField("province", StringType, true),
    StructField("region_1", StringType, true),
    StructField("region_2", StringType, true),
    StructField("variety", StringType, true),
    StructField("winery", StringType, true))
  )


  lazy val spark = {
    println("initial spark")
    SparkSession
      .builder()
      .appName("IngestRawToDataset")
      .master("local[*]")
      .getOrCreate()
  }
  lazy val wineDF = spark.read.option("header", true).schema(schema).csv("src/main/resources/winemag-data_first150k.csv")

  /**
  * Get all the wine data of DataFrame type
  * @return       DataFrame contain all the information
  */
  def getWineDF = wineDF

//  import spark.implicits._
//  object IngestWine extends Ingest{
//    def ingest(srcDir: String, schema: StructType)(implicit spark: SparkSession): Dataset[Wine] = {
//      spark.read
//        .option("header", "true")
//        .option("inferSchema", "false")
//        .schema(schema)
//        .format("csv")
//        .load(srcDir)
//        .as[Wine]
//    }
//
//    def filterWines(ds: Dataset[Wine]): Dataset[Wine] = {
//      ds.filter(d => (d.points != 0))
//    }
//  }
//
//  //object IngestWine extends IngestWine
//
//  val df1 = IngestWine.ingest("src/main/resources/winemag-data_first150k.csv", schema)(spark)
//  val df2 = IngestWine.ingest("src/main/resources/winemag-data-130k-v2.csv", schema)(spark)
//  df1.show()
//  df2.show()
}

