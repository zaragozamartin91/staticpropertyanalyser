package io.github.zaragozamartin91.staticpropertyanalyser

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

/**
 * Main plugin functional test using GROOVY API
 */
class HelloWorldTaskSpecification extends Specification {
    @TempDir File testProjectDir
    File buildFile
    File settingsFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        settingsFile << "rootProject.name = 'staticpropertyanalyser-functional-test-consumer'"

        buildFile = new File(testProjectDir, 'build.gradle')
    }

    def "helloWorld task is available"() {
        given:
        buildFile << """
        plugins {
            id 'java'
            id 'io.github.zaragozamartin91.staticpropertyanalyser'
        }
        """

        when:
        def result = GradleRunner.create()
                                 .withProjectDir(testProjectDir)
                                 .withArguments(arguments)
                                 .withPluginClasspath()
                                 .build()

        then:
        result.output.contains(expectedOutput)
        result.task(":helloWorld").outcome == SUCCESS

        where:
        salute                      | arguments                             |  expectedOutput
        'This is a custom salute'   | ['helloWorld', '--salute', salute]    |  salute
        null                        | ['helloWorld']                        |  'Hello, this is StaticPropertiesPlugin#HelloWorldTask'
    }
}
