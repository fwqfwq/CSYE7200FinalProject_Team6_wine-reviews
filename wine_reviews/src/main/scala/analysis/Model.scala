package analysis

import org.apache.spark.ml.feature.{OneHotEncoder, StringIndexer, VectorIndexer}
import ingest.Wine
import org.apache.spark.sql.SparkSession

object Model extends App{
  val nlp = sparknlp.start()
  Wine.df1.select("description").show()

}
