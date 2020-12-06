package analysis

import ingest.Wine
import org.apache.spark.sql.SparkSession

object Sql extends App{
  val spark = SparkSession
    .builder()
    .appName("SQL Analysis")
    .master("local[*]")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

  val df1 = Wine.getWineDF
  df1.drop("region_2")
  df1.na.drop()
//  df1.show()

  df1.createOrReplaceTempView("wine")

  spark.sql("SELECT country, AVG(price) AS AvgPrice FROM wine GROUP BY country ORDER BY AvgPrice DESC").show()


}
