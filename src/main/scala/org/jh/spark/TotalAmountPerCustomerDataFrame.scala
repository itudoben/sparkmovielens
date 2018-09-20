package org.jh.spark

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._

/**
  *
  * User: hujol
  * Date: 9/10/18
  * Time: 15:47
  */
object TotalAmountPerCustomerDataFrame {

  final case class Customer(id: Int, itemId: Int, amount: Double)

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
    val schemaString = "id itemId amount"

    // Generate the schema based on the string of schema
    val fields = schemaString.split(" ")
      .map(fieldName => {
        // Rating is an integer
        var dataType = if (fieldName == "amount") DoubleType else IntegerType
        StructField(fieldName, dataType, nullable = true)
      })
    val schema = StructType(fields)

    // Load up each line of the ratings data into a dataframe
    val customerDF = spark.read.option("sep", ",").schema(schema)
      .csv("../data/customer-orders.csv").as[Customer].toDF().cache()

    // Print the result
    customerDF.show(5)

    // Compute the amount for each customer
    customerDF.groupBy("id").agg(Map("amount" -> "sum")).sort($"sum(amount)".desc) show 5
  }
}
