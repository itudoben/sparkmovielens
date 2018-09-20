name := "PopularMovies"

version := "0.1"

organization := "org.jh"

scalaVersion := "2.11.12"

// https://www.scala-sbt.org/1.x/docs/Library-Management.html
libraryDependencies ++= Seq(
  "org.apache.spark" % "spark-core_2.11" % "2.3.1" % "provided", // Already provided on the cluster.
  "org.apache.spark" %% "spark-mllib" % "2.3.1",
  "org.apache.spark" % "spark-sql_2.11" % "2.3.1",

  // Dependencies for AWS S3 Active client.
  // https://hadoop.apache.org/docs/stable/hadoop-aws/tools/hadoop-aws/index.html#S3A
  "org.apache.hadoop" % "hadoop-aws" % "2.7.3"
  //  "com.amazonaws" % "aws-java-sdk-s3" % "1.11.409",
  //  "com.amazonaws" % "aws-java-sdk-core" % "1.11.409",
  //  "com.amazonaws" % "aws-java-sdk-kms" % "1.11.409",
  //  "joda-time" % "joda-time" % "2.10"
)

// See doc
// https://github.com/sbt/sbt-assembly/blob/master/README.md#merge-strategy


assemblyMergeStrategy in assembly := {
  case PathList("javax", "servlet", xs@_*) => MergeStrategy.last
  case PathList("javax", "ws", xs@_*) => MergeStrategy.last
  case PathList("javax", "inject", xs@_*) => MergeStrategy.last
  case PathList("com", "amazonaws", xs@_*) => MergeStrategy.last
  case PathList("com", "sun", xs@_*) => MergeStrategy.last
  case PathList("org", "aopalliance", xs@_*) => MergeStrategy.last
  case PathList("org", "apache", xs@_*) => MergeStrategy.last
  case "git.properties" => MergeStrategy.last
  case "mime.types" => MergeStrategy.last
  case x =>
    val oldStrategy = (assemblyMergeStrategy in assembly).value
    oldStrategy(x)
}