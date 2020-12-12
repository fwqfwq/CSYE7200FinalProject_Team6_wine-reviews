package Data

import org.apache.spark.sql.types.{DoubleType, IntegerType, StringType, StructField, StructType}

object Schema3 {
  // !For two csv with two different sets of features, create different schema

  // Schema for '...first150k.csv'
  lazy val schema1: StructType = new StructType(
    Array(StructField("id", IntegerType, true),
      StructField("country", StringType, true),
      StructField("description", StringType, true),
      StructField("designation", StringType, true),
      StructField("points", IntegerType, true),
      StructField("price", DoubleType, true),
      StructField("province", StringType, true),
      StructField("region_1", StringType, true),
      StructField("title", StringType, true),
      StructField("variety", StringType, true),
      StructField("winery", StringType, true))
  )
  // Schema for '...130k-v2.csv'
  lazy val schema2: StructType = new StructType(
    Array(StructField("id", IntegerType, true),
      StructField("country", StringType, true),
      StructField("description", StringType, true),
      StructField("designation", StringType, true),
      StructField("points", IntegerType, true),
      StructField("price", DoubleType, true),
      StructField("province", StringType, true),
      StructField("region_1", StringType, true),
      StructField("taster_name", StringType, true),
      StructField("taster_twitter_handle", StringType, true),
      StructField("title", StringType, true),
      StructField("variety", StringType, true),
      StructField("winery", StringType, true))
  )
  // Schema for preproc1 data, no 'id'
  lazy val schema3_1: StructType = new StructType(
    Array(StructField("country", StringType, true),
      StructField("description", StringType, true),
      StructField("designation", StringType, true),
      StructField("points", IntegerType, true),
      StructField("price", DoubleType, true),
      StructField("province", StringType, true),
      StructField("region_1", StringType, true),
      StructField("title", StringType, true),
      StructField("variety", StringType, true),
      StructField("winery", StringType, true))
  )

  // Schema for preprocDP data
  lazy val schema3_DP: StructType = new StructType(
    Array(StructField("description", StringType, true),
      StructField("points", IntegerType, true))
  )

  // Schema for preproc2 data
  lazy val schema3_2: StructType = new StructType(
    Array(StructField("country", StringType, true),
      StructField("points", IntegerType, true),
      StructField("price", DoubleType, true),
      StructField("province", StringType, true),
      StructField("region_1", StringType, true),
      StructField("title", StringType, true),
      StructField("variety", StringType, true))
  )//country,points,price,province,region_1,title,variety

}
