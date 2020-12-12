package models.wine_reviews.src.main.scala.Data

import org.apache.spark.sql
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

/* Get a overview of dataset */
object Overview {

  /**
   * Call all functions below
   * @param df DataFrame
   * @param spark sparkSession
   */
  def report(df: sql.DataFrame, spark: SparkSession): Unit ={
    println("Report for dataset")
    desc(df)
//    getUniqueCount(df, spark)
  }

  /**
   * Description with counts for features
   * @param df DataFrame
   */
  def desc(df: sql.DataFrame): Unit = {
    println("Description: ")
    df.describe().show()
  }

  /**
   * Distinct-value counts for each feature
   * @param df DataFrame
   * @param spark SparkSession
   */
  def getUniqueCount(df: sql.DataFrame, spark: SparkSession): Unit = {
    println("Distinct values for each feature: ")
    // Either way to do
    if (true) {
      /* Function 2: sql groupBy() */
      println("doing sql...")
      df.createOrReplaceTempView("uniqueCounts")
      for (col <- df.columns
           if col != "id") {
        val count = spark.sql("SELECT %s, count(distinct(%s)) FROM uniqueCounts GROUP BY %s"
          .format(col, col, col))
          .count()
        println(col + ": " + count)
      }

    }
    else {
      /* Function 1: aggregation */
      println("aggregating...")
      val cols = df.columns
      for(col <- cols) {
        print(col + ": ")
        print(df.agg(countDistinct(col)).collect().map(_ (0)).toList.head.toString + "\n")
      }
    }
  }

}
