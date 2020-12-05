package ingest

import org.apache.spark.sql.execution.streaming.Source
import org.apache.spark.sql.{Dataset, SparkSession}
import org.apache.spark.sql.types._


trait Ingest{
  def ingest(srcDir: String, schema: StructType)(implicit spark:SparkSession): Dataset[_]
}

//class Ingest[T: Ingestible] extends (Source => Iterator[Try[T]]) {
//  // parse the source file line by line in Seq[String]
//  // drop the first line
//  // then map it to Seq[Try[X]]
//  // get the iterator of Try[X]
//  def apply(source: Source): Iterator[Try[T]] = source.getLines.toSeq.drop(1).map(e => implicitly[Ingestible[T]].fromString(e)).iterator
//}
//
//trait Ingestible[X] {
//  def fromString(w: String): Try[X]
//}