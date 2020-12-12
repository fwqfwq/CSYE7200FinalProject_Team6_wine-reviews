package models.wine_reviews.src.main.scala.Data

import java.nio.file.{Files, Paths, StandardCopyOption}

import models.wine_reviews.src.main.scala.Data.WriteCSV.writecsv
import org.apache.spark.sql
import org.apache.spark.sql.DataFrame


object Preprocessing {

  /**
   * Only do the drop duplicates and drop null values.
   * @param df DataFrame
   * @return Return a DataFrame
   */
  def preproc1(df: sql.DataFrame): DataFrame ={
    /* Drop dulplicates */
    val temp1 = df.dropDuplicates()

    /* Drop null values */
    val temp2 = temp1.na.drop()

    /* Get report of processed data */
    // Only for overview - no need to show after first time
    // report(temp2)
    temp2.show()

    /* Save as csv: dir = "csvdata/preproc1" */
    writecsv(temp2, "preproc1")

    temp2
  }

  /**
   * Extract only "description" & "points", ready for TF-IDF.
   * @param df DataFrame
   * @return Return a DataFrame
   */
  def preprocDP(df: sql.DataFrame): DataFrame ={
    val dp = df.select( "description" , "points" )
    dp.show(false)

    /* Save as csv: dir = "csvdata/preprocDP" */
    writecsv(dp, "preprocDP")

    dp
  }

  /**
   * According to the overview of raw data,
   * drop "desription"(only for TF-IDF), "designation"(too many NaN), "winery(too many NaN)", keep the left.
   * @param df DataFrame
   * @return Return a DataFrame
   */
  def preproc2(df: sql.DataFrame): DataFrame ={
    val temp1 = df.drop( "description" , "designation", "winery" )

    /* Drop null values */
    val temp2 = temp1.na.drop()

    /* Save as csv: dir = "csvdata/preproc2" */
    writecsv(temp2, "preproc2")

    temp2.show()
    temp2
  }

  /* For now no implementation */
  def moveRenameFile(source: String, destination: String): Unit = {
    val path = Files.move(
      Paths.get(source),
      Paths.get(destination),
      StandardCopyOption.REPLACE_EXISTING
    )
  }

}
