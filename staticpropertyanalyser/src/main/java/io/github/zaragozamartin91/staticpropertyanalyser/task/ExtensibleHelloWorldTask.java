package io.github.zaragozamartin91.staticpropertyanalyser.task;

import io.github.zaragozamartin91.staticpropertyanalyser.extension.HelloWorldExtension;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

public class ExtensibleHelloWorldTask extends DefaultTask {
    public static final String TASK_NAME = "extensibleHelloWorld";

    @Override
    public String getDescription() {
        return "Prints a salute message which is configurable via gradle DSL extension.";
    }

    @TaskAction
    public void execute() {
        System.out.println("Your salute message is: " + getSalute());
    }

    private String getSalute() {
        HelloWorldExtension extension = getExtensions().getByType(HelloWorldExtension.class);
        return extension.getSalute();
    }
}
