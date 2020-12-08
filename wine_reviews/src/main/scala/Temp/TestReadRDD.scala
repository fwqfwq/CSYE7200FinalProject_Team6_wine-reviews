package Temp


import java.io.StringReader

import com.opencsv.CSVReader
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object TestReadRDD {

  val conf = new SparkConf()
    .setAppName("Read")
    .setMaster("local")

  val sc = new SparkContext(conf)

  val csv1_url = "src/main/resources/winemag-data_first150k.csv"
  val csv2_url = "src/main/resources/winemag-data_130k-v2.csv"
  val input = sc.textFile(csv1_url)

  input.take(10).foreach(println)

  println(input.count())

  val result: RDD[Array[String]] = input.map { line =>
    line.split(",", -1)
    val reader = new CSVReader(new StringReader(line))
    reader.readNext()
  }
  result.foreach(print)
  result.foreach(x => x.foreach(print))
}
