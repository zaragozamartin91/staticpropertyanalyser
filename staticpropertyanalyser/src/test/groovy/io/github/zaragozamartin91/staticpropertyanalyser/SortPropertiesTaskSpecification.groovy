package io.github.zaragozamartin91.staticpropertyanalyser

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

/**
 * Main plugin functional test using GROOVY API
 */
class SortPropertiesTaskSpecification extends Specification {
    @TempDir
    File testProjectDir
    File buildFile
    File settingsFile
    File resourcesDir
    File applicationPropertiesFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        settingsFile << "rootProject.name = 'staticpropertyanalyser-functional-test-consumer'"

        buildFile = new File(testProjectDir, 'build.gradle')

        // create src/main/resources directories
        resourcesDir = new File(testProjectDir, 'src/main/resources')
        assert resourcesDir.mkdirs()
        // create application.properties file
        applicationPropertiesFile = new File(resourcesDir, 'application.properties')
    }


    def "SortPropertiesTask task picks up resource files"() {
        given:
        applicationPropertiesFile << """
        message=hello_world
        """

        and:
        buildFile << """
        plugins {
            id 'java'
            id 'io.github.zaragozamartin91.staticpropertyanalyser'
        }
        
        tasks.named('sortProperties').configure { t ->
            resourceFile = 'application.properties'
        }
        """

        when:
        def result = GradleRunner.create()
                                 .withProjectDir(testProjectDir)
                                 .withArguments(arguments)
                                 .withPluginClasspath()
                                 .withDebug(true)
                                 .build()

        then: 'The properties are listed in stdout'
        result.output.contains('message=hello_world')

        and: 'The task is successful'
        result.task(":${taskName}").outcome == SUCCESS

        and: 'The task creates a new file with the sorted properties'
        Arrays.stream(resourcesDir.listFiles()).filter(it -> it.name.startsWith('application.properties')).count() == 2

        where:
        taskName         | arguments
        'sortProperties' | ['sortProperties']
    }
}
