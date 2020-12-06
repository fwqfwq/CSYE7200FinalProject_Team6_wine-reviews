package models

object dataPredict {
    def doPredict(describe : Seq[String], maxPrice :Option[Double], minPrice :Option[Double]) = {
      val des = describe.head
      val results = Seq(s"Wine1 $des $maxPrice $minPrice", s"Wine2 $des $maxPrice $minPrice", s"Wine3 $des $maxPrice $minPrice")
      results
    }
    def trendyWine()={
      val results = Seq(s"Some trendy for description",s"Some trendy for price",s"Some trendy for region")
      results
    }
   def overallAnalysis()={
    val results = Seq(s"Some analysis ",s"Some analysis ",s"Some analysis ")
    results
  }

}
