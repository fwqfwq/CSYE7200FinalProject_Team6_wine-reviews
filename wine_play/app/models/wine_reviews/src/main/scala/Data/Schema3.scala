package models.wine_reviews.src.main.scala.Data

import org.apache.spark.sql.types._

object Schema3 {
  // !For two csv with two different sets of features, create different schema

  // Schema for '...first150k.csv'
  lazy val schema1: StructType = new StructType(
    Array(StructField("id", IntegerType, nullable = true),
      StructField("country", StringType, nullable = true),
      StructField("description", StringType, nullable = true),
      StructField("designation", StringType, nullable = true),
      StructField("points", IntegerType, nullable = true),
      StructField("price", DoubleType, nullable = true),
      StructField("province", StringType, nullable = true),
      StructField("region_1", StringType, nullable = true),
      StructField("title", StringType, nullable = true),
      StructField("variety", StringType, nullable = true),
      StructField("winery", StringType, nullable = true))
  )
  // Schema for '...130k-v2.csv'
  lazy val schema2: StructType = new StructType(
    Array(StructField("id", IntegerType, nullable = true),
      StructField("country", StringType, nullable = true),
      StructField("description", StringType, nullable = true),
      StructField("designation", StringType, nullable = true),
      StructField("points", IntegerType, nullable = true),
      StructField("price", DoubleType, nullable = true),
      StructField("province", StringType, nullable = true),
      StructField("region_1", StringType, nullable = true),
      StructField("taster_name", StringType, nullable = true),
      StructField("taster_twitter_handle", StringType, nullable = true),
      StructField("title", StringType, nullable = true),
      StructField("variety", StringType, nullable = true),
      StructField("winery", StringType, nullable = true))
  )
  // Schema for preproc1 data, no 'id'
  lazy val schema3_1: StructType = new StructType(
    Array(StructField("country", StringType, nullable = true),
      StructField("description", StringType, nullable = true),
      StructField("designation", StringType, nullable = true),
      StructField("points", IntegerType, nullable = true),
      StructField("price", DoubleType, nullable = true),
      StructField("province", StringType, nullable = true),
      StructField("region_1", StringType, nullable = true),
      StructField("title", StringType, nullable = true),
      StructField("variety", StringType, nullable = true),
      StructField("winery", StringType, nullable = true))
  )

  // Schema for preprocDP data
  lazy val schema3_DP: StructType = new StructType(
    Array(StructField("description", StringType, nullable = true),
      StructField("points", IntegerType, nullable = true))
  )

  // Schema for preproc2 data
  lazy val schema3_2: StructType = new StructType(
    Array(StructField("country", StringType, nullable = true),
      StructField("points", IntegerType, nullable = true),
      StructField("price", DoubleType, nullable = true),
      StructField("province", StringType, nullable = true),
      StructField("region_1", StringType, nullable = true),
      StructField("title", StringType, nullable = true),
      StructField("variety", StringType, nullable = true))
  )//country,points,price,province,region_1,title,variety

}
