package controllers

import javax.inject._
import models.wine_reviews.src.main.scala.Main
import play.api._
import play.api.mvc._
/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }
  def predict() = Action{ implicit request: Request[AnyContent] =>
    Ok(views.html.PredictWine())
  }
//   def validatePredict(description : String) = Action{
//     Ok(s"The prediction for you is: $description")
//   }
  def postPredict() = Action { request =>
    val postVals = request.body.asFormUrlEncoded
    postVals.map{args =>
      val description: Seq[String] = args("description")
//      val maxPrice: Option[Double] = try{ Some(args("maxPrice").head.toDouble)} catch{ case _ => None}
//      val minPrice: Option[Double] = try{ Some(args("minPrice").head.toDouble)} catch{ case _ => None}
      val results = Main.doPredict(description).head
      Ok(views.html.resultForm(results))
    }.getOrElse(Redirect(routes.HomeController.predict()))
  }

  def backToMain() = Action{
    Redirect(routes.HomeController.index())
  }
//  def trendyWine() = Action{
//
//  }
  def recommendation()= Action { implicit request: Request[AnyContent] =>
  Ok(views.html.recommendation())
}
  def postRecommendation()=Action{ request =>
  val postVals = request.body.asFormUrlEncoded
  postVals.map{args =>
    val country: Seq[String] = args("country")
//    val points = args("points").head
    val price = args("price").head
    val province: Seq[String] = args("province")
    val region_1: Seq[String] = args("region_1")
    val title: Seq[String] = args("title")
    val variety: Seq[String] = args("variety")
    val results = Main.recommendation(country, price, province, region_1, title, variety)
    Ok(views.html.recommendationResult(results))
  }.getOrElse(Redirect(routes.HomeController.recommendation()))
}
  def overallAnalysis() = Action{
    val results: Seq[String] = Main.overallAnalysis()
    Ok(views.html.overallAnalysis(results))
  }
}
