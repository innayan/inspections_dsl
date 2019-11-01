package patches.buildTypes

import jetbrains.buildServer.configs.kotlin.v2018_2.*
import jetbrains.buildServer.configs.kotlin.v2018_2.IdeaDuplicates
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.v2018_2.buildSteps.powerShell
import jetbrains.buildServer.configs.kotlin.v2018_2.ideaDuplicates
import jetbrains.buildServer.configs.kotlin.v2018_2.ui.*

/*
This patch script was generated by TeamCity on settings change in UI.
To apply the patch, change the buildType with id = 'Dublicates'
accordingly, and delete the patch script.
*/
changeBuildType(RelativeId("Dublicates")) {
    params {
        add {
            password("secure1", "credentialsJSON:a76bfc38-811c-4633-8d84-478f11fd4c40")
        }
    }

    expectSteps {
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
    steps {
        insert(1) {
            powerShell {
                executionMode = BuildStep.ExecutionMode.RUN_ON_FAILURE
                scriptMode = script {
                    content = "sleep 200"
                }
            }
        }
        update<IdeaDuplicates>(2) {
            pathToProject = "java_eclipse/pom.xml"
            jvmArgs = "-Xmx1G -XX:ReservedCodeCacheSize=240m"
            targetJdkHome = "%env.JDK_18%"
            lowerBound = 10
            discardCost = 0
            distinguishVariables = true
            distinguishMethods = true
            distinguishTypes = true
            distinguishLiterals = true
            extractSubexpressions = true
            includeTestSources = true
            param("duplicates.runner.field", "true")
        }
    }

    requirements {
        add {
            exists("yyy", "RQ_3")
        }
    }

    expectDisabledSettings()
    updateDisabledSettings("RQ_3")
}
