package Temp

import Data.Wine.{getSpark, getWineDF}

object RFModelTest extends App {

  /* Get SparkSession and DataFrame */
  val wineDF = getWineDF // set to "DP", index 1
  val spark = getSpark

  val df = wineDF.na.drop()
  println(df.count()) // 280839 non-null value
  df.show(10)

  //  val countryIndexer = new StringIndexer().setInputCol("country").setOutputCol("countryIndexted")
  //  val designationIndexer = new StringIndexer().setInputCol("designation").setOutputCol("designationIndexted")
  //  val provinceIndexer = new StringIndexer().setInputCol("province").setOutputCol("provinceIndexted")
  //  val regionIndexer = new StringIndexer().setInputCol("region_1").setOutputCol("regionIndexted")
  //  val varietyIndexer = new StringIndexer().setInputCol("variety").setOutputCol("varietyIndexted")
  //  val wineryIndexer = new StringIndexer().setInputCol("winery").setOutputCol("wineryIndexted")
  //  val c = countryIndexer.fit(df).transform(df)
  //  val d = designationIndexer.fit(c).transform(c)
  //  val p = provinceIndexer.fit(d).transform(d)
  //  val r = regionIndexer.fit(p).transform(p)
  //  val v = varietyIndexer.fit(r).transform(r)
  //  val w = wineryIndexer.fit(v).transform(v)
  //  val indexedDF = df.drop("country", "designation", "province", "region_1", "variety", "winery")
  //  indexedDF.show
  //  c.show
  //
  //  val encoder = new OneHotEncoderEstimator()
  //    .setInputCols(Array("countryIndexed", "designationIndexed", "provinceIndexed", "region_1Indexed", "varietyIndexed", "wineryIndexed"))
  //    .setOutputCols(Array("countryEncoded", "designationEncoded", "provinceEncoded", "region_1Encoded", "varietyEncoded", "wineryEncoded"))
  //
  //  val encodedDF = encoder.fit(indexedDF).transform(indexedDF)


  /* Features Processing */
  // Feature Harsher - for the String-valued data, desides 'description'
  //  val hasher = new FeatureHasher()
  //    .setInputCols("country", "designation", "province", "region_1", "variety", "winery")
  //    .setOutputCol("features")
  //
  //  val featurized = hasher.transform(df).drop("country", "designation", "province", "region_1", "variety", "winery")
  //  println("After Harsher: ")
  //  featurized.show
  //
  //  // Tokenize -- for 'description'
  //  val tokenizer = new Tokenizer()
  //    .setInputCol("description").setOutputCol("words")
  //  val countTokens = udf { (words: Seq[String]) => words.length }
  //  val tokenized = tokenizer.transform(featurized).drop("description")
  //  println("After Tokenizer: ")
  //  tokenized.show
  //
  //  // Stopwords Removal
  //  val remover = new StopWordsRemover()
  //    .setInputCol("words")
  //    .setOutputCol("filtered").transform(tokenized)
  //  println("After Stopwords Removal: ")
  //  remover.show
  //
  //
  //
  //  //TF-IDF
  //  val hashingTF = new HashingTF()
  //    .setInputCol("filtered").setOutputCol("rawFeatures").setNumFeatures(20)
  //  val featurizedData = hashingTF.transform(remover).drop("description", "words", "filtered")
  //  featurizedData.show
  //
  //  val idf = new IDF().setInputCol("rawFeatures").setOutputCol("descFeatures")
  //  val idfModel = idf.fit(featurizedData)
  //
  //  val rescaledData = idfModel.transform(featurizedData)
  //  rescaledData.show
  //
  //  //PCA
  //  val pca = new PCA()
  //    .setInputCol("features")
  //    .setOutputCol("pcaFeatures")
  //    .setK(10)
  //    .fit(featurizedData)
  //
  //  val result = pca.transform(featurizedData)
  //  result.show
  //
  //
  //  val Array(train, test) = featurized.randomSplit(Array(0.7, 0.3))
  //
  //  val rf = new RandomForestRegressor()
  //    .setLabelCol("points")
  //    .fit(train)
  //
  //  val predictions = rf.transform(test)
  //
  //  predictions.select("prediction", "points", "predFeatures").show

}
