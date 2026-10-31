import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

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

version = "2026.2"

project {
    description = "TeamCity "

    vcsRoot(HttpsGithubComS1ugger96exampleTeamcityGitRefsHeadsMaster)

    buildType(Build)
}

object Build : BuildType({
    artifactRules = "target/*.jar => artifacts"
    name = "Build"

    vcs {
        root(HttpsGithubComS1ugger96exampleTeamcityGitRefsHeadsMaster)
    }

    steps {
        maven {
            name = "Maven Test"
            id = "Maven2"

            conditions {
                doesNotEqual("teamcity.build.branch.is_default", "true")
            }
            goals = "clean test"
        }
        maven {
            name = "Maven Deploy"
            id = "Maven2_1"

            conditions {
                equals("teamcity.build.branch.is_default", "true")
            }
            goals = "clean deploy"
            userSettingsSelection = "nexus-settings"
        }
    }

    triggers {
        vcs {
        }
    }
})

object HttpsGithubComS1ugger96exampleTeamcityGitRefsHeadsMaster : GitVcsRoot({
    name = "https://github.com/S1ugger96/example-teamcity.git#refs/heads/master"
    url = "https://github.com/S1ugger96/example-teamcity.git"
    branch = "refs/heads/master"
    branchSpec = "refs/heads/*"
})
