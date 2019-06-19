import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaInspections
import jetbrains.buildServer.configs.kotlin.v2018_2.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2019.1"

project {

    vcsRoot(HttpsGithubComInnayanJavaEclipse)

    buildType(Inspections)
    buildType(Dublicates)
}

object Dublicates : BuildType({
    name = "Duplicates"

    vcs {
        root(HttpsGithubComInnayanJavaEclipse)
    }

    steps {
        maven {
            goals = "clean test"
            pomLocation = "java_eclipse/pom.xml"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
        ideaDuplicates {
            executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
            pathToProject = "java_eclipse/pom.xml"
            jvmArgs = "-Xmx1G -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
            lowerBound = 10
            discardCost = 0
            distinguishMethods = true
            distinguishTypes = true
            distinguishLiterals = true
            extractSubexpressions = true
            param("idea.app.home", "%teamcity.tool.idea%")
        }
    }
})

object Inspections : BuildType({
    name = "Inspections"

    vcs {
        root(HttpsGithubComInnayanJavaEclipse)
    }

    steps {
        maven {
            goals = "clean test"
            pomLocation = "java_eclipse/pom.xml"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
        ideaInspections {
            pathToProject = "java_eclipse/pom.xml"
            jvmArgs = "-Xmx512m -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
        }
    }
})

object HttpsGithubComInnayanJavaEclipse : GitVcsRoot({
    name = "https://github.com/innayan/java_eclipse"
    url = "https://github.com/innayan/java_eclipse"
})
