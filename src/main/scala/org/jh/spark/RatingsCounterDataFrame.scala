package org.jh.spark

import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

/**
  * Count up how many of each star rating exists in the MovieLens 100K data set using a Spark data frame.
  */
object RatingsCounterDataFrame {

  final case class Rating(userID: Int, movieID: Int, rating: Int, timestamp: Long)

  /** Our main function where the action happens */
  def main(args: Array[String]) {

    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.ERROR)

    // Define the Spark session.
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      .master("local[*]")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()

    // For implicit conversions like converting RDDs to DataFrames
    import spark.implicits._

    // The schema is encoded in a string
    val schemaString = "userID movieID rating timestamp"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(" ")
      .map(fieldName => {
        // Rating is an integer
        var dataType = if (fieldName == "timestamp") LongType else IntegerType
        StructField(fieldName, dataType, nullable = true)
      })
    val schema = StructType(fields)

    // Load up each line of the ratings data into a dataframe
    val ratingsDF = spark.read.option("sep", "\t").schema(schema).csv("../data/ml-100k/u.data").as[Rating].toDF()

    // Count up how many times each value (rating) occurs
    val results = ratingsDF.groupBy("rating").count().sort($"rating".asc)

    results.show()
  }
}
