package io.github.zaragozamartin91.staticpropertyanalyser.task;

import java.util.Map;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;
import org.jetbrains.annotations.NotNull;

public class HelloWorldTask extends DefaultTask {
    public static final String TASK_NAME = "helloWorld";

    /* Input values can be configured
        @ task registration https://docs.gradle.org/current/userguide/custom_tasks.html#sec:an_incremental_task_in_action
        @ task execution via command line https://stackoverflow.com/questions/71335742/how-do-i-specify-default-values-for-gradle-custom-java-task-inputs */
    String salute = "Hello, this is StaticPropertiesPlugin#HelloWorldTask";

    @Input
    public String getSalute() {
        return salute;
    }

    /* The @Option annotation makes the "salute" input parameter configurable from command-line */
    @Option(option = "salute", description = "Configures a desired salute")
    public void setSalute(String salute) {
        this.salute = salute;
    }

    @Override
    public @NotNull String getName() {
        return TASK_NAME;
    }

    @Override
    public String getDescription() {
        return "Prints a salute plus project properties. Salute message is configurable on command line via the --salute parameter.";
    }

    @TaskAction
    public void execute() {
        System.out.println(getSalute());
        System.out.println("Module name: " + getProject().getName());
        System.out.println("Module directory: " + getProject().getProjectDir());
        Map<String, ?> projectProperties = getProject().getProperties();
        System.out.println("Project properties: " + projectProperties);
    }
}
