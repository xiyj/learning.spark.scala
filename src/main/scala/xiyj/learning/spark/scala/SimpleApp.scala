package xiyj.learning.spark.scala

import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.SparkConf

import scala.reflect.runtime.{ universe => ru }

import scala.math.random
import org.apache.spark.sql.SparkSession

object SimpleApp {
  def main(args: Array[String]) {
    // need to setup :
    //  System.setProperty("hadoop.home.dir", "PATH/TO/THE/DIR")

    // create Spark context with Spark configuration

    //    simple()
    //
    //    val args = Array("10")
    //    pi(args)

    misc();

  }

  def simple() {
    println("simple spark app");
    // val sc = new SparkContext(new SparkConf().setAppName("Spark Count"))
    val spark = SparkSession
      .builder()
      .appName("Java Spark simple example")
      .config("spark.master", "local")
      .getOrCreate();

    try {
      val data = 1 to 10000; val distData = spark.sparkContext.parallelize(data); distData.filter(_ < 10).collect()
      System.out.println("Hello, world")
    } finally {
      spark.close();
      println("spark session closed.");
    }
  }

  def pi(args: Array[String]) {
    println("pi spark app");
    val spark = SparkSession
      .builder
      .appName("Spark Pi")
      .config("spark.master", "local")
      .getOrCreate()
    try {
      val slices = if (args.length > 0) args(0).toInt else 2
      val n = math.min(100000L * slices, Int.MaxValue).toInt // avoid overflow
      val count = spark.sparkContext.parallelize(1 until n, slices).map { i =>
        val x = random * 2 - 1
        val y = random * 2 - 1
        if (x * x + y * y <= 1) 1 else 0
      }.reduce(_ + _)
      println(s"Pi is roughly ${4.0 * count / (n - 1)}")
      spark.stop()
    } finally {
      spark.close();
      println("spark session closed.");
    }
  }

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]

  def misc() {
    println("misc spark app");
    // val sc = new SparkContext(new SparkConf().setAppName("Spark Count"))
    val spark = SparkSession
      .builder()
      .appName("Java Spark misc example")
      .config("spark.master", "local")
      .getOrCreate();

    try {
      val kvRdd = spark.sparkContext.parallelize(List(("a", 1), ("b", 2), ("c", 3)))
      val keysRdd = kvRdd.keys
      keysRdd.foreach(k => println("key : " + k))
      println("keysRdd type(scala) : " + getTypeTag(keysRdd).tpe.toString());
      
      val valuesRdd = kvRdd.values
      valuesRdd.foreach(v => println("value : " + v))
    } finally {
      spark.close();
      println("spark session closed.");
    }
  }

}
