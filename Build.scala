import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "SoScheme"
    val appVersion      = "1.0-SNAPSHOT"
    
    lazy val util = project.in(file("../jllvm"))

    resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"
    
    val appDependencies = Seq(
      "org.scala-tools" % "scala-stm_2.9.1" % "0.3",
      "jllvm" % "jllvm" % "0.0.1-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
