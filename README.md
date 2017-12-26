# initial setup
from simple scale/eclipse maven setup, scala-archetype-simple

dependency setup 
* manually add all jars from sparks (specific version) in to user library, e.g SPARK_LIB, and link to project java path
* using maven (preferred one), add spark core/sql/......

initial app, using local master (otherwise got error) for quick run

	  val spark = SparkSession
			  .builder()
			  .appName("Java Spark SQL basic example")
			  .config("spark.master", "local")		// local master
			  .getOrCreate();
	  val data = 1 to 10000; val distData = spark.sparkContext.parallelize(data); distData.filter(_ < 10).collect()

done.

in case running under hadoop, setup winutils/bin/xxx, and setup environment HADOOP_HOME as path to winutils (not include bin)

