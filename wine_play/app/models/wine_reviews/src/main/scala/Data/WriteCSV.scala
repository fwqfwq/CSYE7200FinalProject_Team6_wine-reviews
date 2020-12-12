package models.wine_reviews.src.main.scala.Data

import java.nio.file.{Files, Paths}

import org.apache.spark.sql.{DataFrame, SaveMode}


object WriteCSV {

  /**
   * Save as one csv file to dir:'csvdata/'
   * @param df DataFrame
   * @param path Subdirectory
   */
  def writecsv(df: DataFrame, path: String): Unit = {
    val filepath = "csvdata/" + path

    // Check the existence of file path
    // If no, create csv file
    if (!Files.exists(Paths.get(filepath))) {
      df.coalesce(1).write
        .mode(SaveMode.Overwrite)
        .option("header", value = true)
        .csv(filepath)

      println("Write out csv successfully")
    }
    else println("Dir exists")


  }

  /**
   * Check file path
   * @return
   */
  def checkFilePath(s: String): Boolean = {
    val re: Boolean = s match {
      case "1" => Files.exists(Paths.get("csvdata/preproc1"))
      case "DP" => Files.exists(Paths.get("csvdata/preprocDP"))
      case "2" => Files.exists(Paths.get("csvdata/preproc2"))
      case _ => false
    }
    re
  }


  /* Rename the data */
  //    val rRating = """^(\w*)(-(\d\d))?$""".r
  //    val rfilepath = "csvdata/prepro.csv"

}


