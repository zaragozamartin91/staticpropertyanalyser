package io.github.zaragozamartin91.staticpropertyanalyser

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

/**
 * Main plugin functional test using GROOVY API
 */
class HelloWorldTaskSpecification extends Specification {
    @TempDir
    File testProjectDir
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
        salute                    | arguments                          | expectedOutput
        'This is a custom salute' | ['helloWorld', '--salute', salute] | salute
        null                      | ['helloWorld']                     | 'Hello, this is StaticPropertiesPlugin#HelloWorldTask'
    }

    def "ConfigurableHelloWorldTask task is configurable via gradle DSL on registration"() {
        given:
        buildFile << """
        plugins {
            id 'java'
            id 'io.github.zaragozamartin91.staticpropertyanalyser'
        }
        
        tasks.register("${taskName}", io.github.zaragozamartin91.staticpropertyanalyser.task.ConfigurableHelloWorldTask) {
            group = 'StaticProperties'
            salute = 'hey there'
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
        result.task(":${taskName}").outcome == SUCCESS

        where:
        taskName                 | arguments                  | expectedOutput
        'configurableHelloWorld' | ['configurableHelloWorld'] | 'Your salute message is: hey there'
    }

    def "ExtensibleHelloWorldTask task is configurable via gradle DSL using extensions"() {
        given:
        buildFile << """
        plugins {
            id 'java'
            id 'io.github.zaragozamartin91.staticpropertyanalyser'
        }
        
        helloWorldExtension {
            salute = '${salute}'
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
        result.task(":extensibleHelloWorld").outcome == SUCCESS

        where:
        salute         | arguments                | expectedOutput
        'Hello there'  | ['extensibleHelloWorld'] | "Your salute message is: ${salute}"
        'Salutations!' | ['extensibleHelloWorld'] | "Your salute message is: ${salute}"
    }

}
