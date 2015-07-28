import com.github.retronym.SbtOneJar
import sbt._
import Keys._

object build extends Build {
  
  mainClass in (Compile, run) := Some("com.tajpure.scheme.compiler.Main")
  
  def standardSettings = Seq(
    exportJars := true
  ) ++ Defaults.defaultSettings
  
  lazy val beta = Project("SoScheme",
    file("."),
    settings = standardSettings ++ SbtOneJar.oneJarSettings
  )
  
}