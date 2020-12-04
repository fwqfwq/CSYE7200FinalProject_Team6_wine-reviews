package analysis

import scala.collection.mutable
import scala.io.Source
import scala.util.Try

case class Wine(country: String, format: Format, production: Production,
                reviews: Reviews, director: Principal, actor1: Principal,
                actor2: Principal, actor3: Principal, genres: Seq[String],
                plotKeywords: Seq[String], imdb: String)
//country,description,designation,points,price,province,
// region_1,region_2,taster_name,taster_twitter_handle,title,variety,winery
/**
 *
 * @param color
 * @param language
 * @param aspectRatio
 * @param duration
 */
case class Format(color: Boolean, language: String, aspectRatio: Double, duration: Int) {
  override def toString = {
    val x = if (color) "Color" else "B&W"
    s"$x,$language,$aspectRatio,$duration"
  }
}
