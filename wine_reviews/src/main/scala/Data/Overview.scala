package Data

import org.apache.spark.sql
import org.apache.spark.sql.functions._

/* Get a overview of dataset */
object Overview {

  def report(df: sql.DataFrame): Unit ={
    println("Report for dataset")
    desc(df)
//    getUniqueCount(df)
  }

  /* Description with counts for features */
  def desc(df: sql.DataFrame): Unit = {
    println("Description: ")
    df.describe().show()
  }

  /* Distinct-value counts for each feature */
  def getUniqueCount(df: sql.DataFrame): Unit = {
    println("Distinct values for each feature: ")
    val cols = df.columns
    for(col <- cols) {
      print(col + ": ")
      print(df.agg(countDistinct(col)).collect().map(_(0)).toList(0).toString + "\n")
    }
  }

}
