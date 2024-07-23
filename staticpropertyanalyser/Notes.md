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

## Unit testing

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