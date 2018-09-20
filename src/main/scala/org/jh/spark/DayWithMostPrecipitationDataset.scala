package org.jh.spark

import org.apache.log4j._
import org.apache.spark.sql._
import org.apache.spark.sql.types._

/**
  * Find the day with most precipitation using a Spark dataset.
  */
object DayWithMostPrecipitationDataset {

  final case class Precipitation(station: String, date: String, description: String, value: Double,
                                 col1: String, col2: String, col3: String, col4: String)

  /**
    * Our main function where the action happens
    */
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
    val schemaString = "station date description value col1 col2 col3 col4"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(" ")
      .map(fieldName => {
        // Value is a double
        val dataType = if (fieldName == "value") DoubleType else StringType
        StructField(fieldName, dataType, nullable = true)
      })
    val schema = StructType(fields)

    // Load up each line of the ratings data into a dataset
    val lines = spark.read.schema(schema).csv("../data/1800.csv").as[Precipitation]

    // Select the station with the maximum precipitation
    lines.select("station", "description", "value", "date").filter("description == \"PRCP\"")
      .groupBy("date")
      .max("value")
      .sort($"max(value)".desc)
      .show(1)
  }
}
