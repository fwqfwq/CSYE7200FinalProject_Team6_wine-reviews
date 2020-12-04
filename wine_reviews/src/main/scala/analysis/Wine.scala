package analysis

import scala.collection.mutable
import scala.io.Source
import scala.util.Try

case class Wine(country: String, description: String, designation: String,
                points: Integer, price: Double, province: String,
                region_1: String, region_2: String, variety: String,
                winery: String)
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
