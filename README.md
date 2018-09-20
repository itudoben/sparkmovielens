# Credits
The Scala code in this repository is based from the MOOC:
https://www.udemy.com/apache-spark-with-scala-hands-on-with-big-data

Thanks for also Sundog Education (www.sundog-education.com) for the great content of the course.

Thanks to Frank for the great course on Spark and Scala.

Reference to the request to use this content as a support for learning Spark and Scala:
https://www.udemy.com/apache-spark-with-scala-hands-on-with-big-data/learn/v4/questions/5174452

# movieslens
Data to perform movie analyses using Scala and Spark.

# Installation of dependencies
First one need to add the dependencies to the build.sbt file.
On IntelliJ IDEA, one can refresh the .odea cache by using the sbt refresh button on the tool window right side.

# Package the driver script (application)
```bash
sbt package
ls -lartH target/scala-2.11
```

The result should show a fat jar file named similarly as this:
```bash
-rw-r--r--  1 hujol  staff   165M Sep 17 11:47 PopularMovies-assembly-0.1.jar

```

Run the code:
```bash
spark-submit --class com.sundogsoftware.spark.MovieSimilarities \
../classes/target/scala-2.11/PopularMovies-assembly-0.1.jar 1
```

Or
```bash
spark-submit --class com.sundogsoftware.spark.FakeFriends \
../classes/target/scala-2.11/PopularMovies-assembly-0.1.jar
```

# Run the 100 M movie lens
Increase the memory to 2g for the driver - on a local machine, it is also running the executor though - for instance:

```bash
spark-submit --executor-memory 2g --class com.sundogsoftware.spark.MovieSimilarities1M \
--properties-file conf/spark-defaults.conf \
../classes/target/scala-2.11/PopularMovies-assembly-0.1.jar 260
```
