package analysis

import scala.io.Source
import scala.util.Try

/**
 *
 *
 * @tparam T
 */
class Ingest[T: Parsable] extends (Source => Iterator[Try[T]]) {
  def apply(source: Source): Iterator[Try[T]] = source.getLines.drop(1).map(implicitly[Parsable[T]].parse)
}


/**
 * trait for parse
 * @tparam X
 */
trait Parsable[X] {
  def parse(w: String): Try[X]
}