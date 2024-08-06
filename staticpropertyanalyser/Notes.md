# Creating gradle plugin for property analysis

Creating custom gradle plugin
* https://www.baeldung.com/gradle-create-plugin

Passing command line arguments to gradle
* https://www.baeldung.com/gradle-command-line-arguments

Video tutorial on creating a custom gradle plugin
* https://www.youtube.com/watch?v=CbHslSLsAWI

Writing a binary plugin (official gradle docs)
* https://docs.gradle.org/current/userguide/implementing_gradle_plugins_binary.html

How to publish the plugin
* https://docs.gradle.org/current/userguide/publishing_gradle_plugins.html

Gradle plugin sample (greeting plugin)
* https://docs.gradle.org/current/samples/sample_gradle_plugin.html

## Manual , Functional & Unit testing

Binary gradle plugin testing can be done by using compose builds: https://docs.gradle.org/current/userguide/testing_gradle_plugins.html

That means... in the same level / folder one can have the plugin project and a consumer project.

On the consumer project, the plugin can be included by doing:
```groovy
pluginManagement {
    includeBuild '../url-verifier-plugin'
}
```

The gradle plugin development plugin comes with TestKit preconfigured for implementing unit and functional testing of the plugin
via emulation of gradle builds:
* https://docs.gradle.org/current/userguide/test_kit.html#test_kit


Using groovy API & GradleRunner end to end functional tests can be written to test the plugin:
* https://docs.gradle.org/current/userguide/test_kit.html#example_using_gradlerunner_with_groovy_and_spock
* https://docs.gradle.org/current/userguide/test_kit.html#example_automatically_injecting_the_code_under_test_classes_into_test_builds

Add groovy plugin:
```groovy
plugins {
    id 'groovy'
    id 'java-gradle-plugin'
}

dependencies {
    testImplementation('org.spockframework:spock-core:2.2-groovy-3.0') {
        exclude group: 'org.codehaus.groovy'
    }
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}
```

Define functional test using groovy API:
```groovy
import org.gradle.testkit.runner.GradleRunner
import static org.gradle.testkit.runner.TaskOutcome.*
import spock.lang.TempDir
import spock.lang.Specification

class BuildLogicFunctionalTest extends Specification {
    @TempDir File testProjectDir
    File settingsFile
    File buildFile

    def setup() {
        settingsFile = new File(testProjectDir, 'settings.gradle')
        buildFile = new File(testProjectDir, 'build.gradle')
    }

    def "hello world task prints hello world"() {
        given:
        settingsFile << "rootProject.name = 'hello-world'"
        buildFile << """
            task helloWorld {
                doLast {
                    println 'Hello world!'
                }
            }
        """

        when:
        def result = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments('helloWorld')
            .build()

        then:
        result.output.contains('Hello world!')
        result.task(":helloWorld").outcome == SUCCESS
    }
}
```

The same functional test can be written using the JAVA API:
```java
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BuildLogicFunctionalTest {

    @TempDir File testProjectDir;
    private File settingsFile;
    private File buildFile;

    @BeforeEach
    public void setup() {
        settingsFile = new File(testProjectDir, "settings.gradle");
        buildFile = new File(testProjectDir, "build.gradle");
    }

    @Test
    public void testHelloWorldTask() throws IOException {
        writeFile(settingsFile, "rootProject.name = 'hello-world'");
        String buildFileContent = "task helloWorld {" +
                "    doLast {" +
                "        println 'Hello world!'" +
                "    }" +
                "}";
        writeFile(buildFile, buildFileContent);

        BuildResult result = GradleRunner.create()
                                         .withProjectDir(testProjectDir)
                                         .withArguments("helloWorld")
                                         .build();

        assertTrue(result.getOutput().contains("Hello world!"));
        assertEquals(SUCCESS, result.task(":helloWorld").getOutcome());
    }

    private void writeFile(File destination, String content) throws IOException {
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(destination));
            output.write(content);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}
```

## Defining extensions

From Baeldung: https://www.baeldung.com/gradle-create-plugin

Define extension:
```java
public class GreetingPluginExtension {
    private String greeter = "Baeldung";
    private String message = "Message from the plugin!"
    // standard getters and setters
}
```

Add extension to the plugin:
```java
@Override
public void apply(Project project) {
    GreetingPluginExtension extension = project.getExtensions()
      .create("greeting", GreetingPluginExtension.class);

    project.task("hello")
      .doLast(task -> {
          System.out.println(
            "Hello, " + extension.getGreeter());
          System.out.println(
            "I have a message for You: " + extension.getMessage());
      });
}
```

Use the extension to configure the plugin:
```groovy
greeting {
    greeter = "Stranger"
    message = "Message from the build script" 
}
```

## Java Gradle Development Plugin

_When we’re writing our plugins in Java, we can benefit from the Java Gradle Development Plugin.
This will automatically compile and add gradleApi() dependencies. It will also perform plugin metadata validation as a part of the gradle jar task.
We can add plugin by adding following block to our build script:_

```
plugins {
    id 'java-gradle-plugin'
}
```


## Sample plugin code

```java
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class StaticPropertiesPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        String f = project.hasProperty("mainFile")
                ? "main file is " + project.getProperties().get("mainFile")
                : "";

        project.task("parse")
               .doLast(task -> {
                   System.out.println("This is StaticPropertiesPlugin! " + f);
                   System.out.println("Module name: " + project.getName());
                   System.out.println("Module directory: " + project.getProjectDir());
               });
    }
}
```