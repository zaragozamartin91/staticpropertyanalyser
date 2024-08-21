package io.github.zaragozamartin91.staticpropertyanalyser.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

public class ConfigurableHelloWorldTask extends DefaultTask {
    /* Input values can be configured
        @ task registration https://docs.gradle.org/current/userguide/custom_tasks.html#sec:an_incremental_task_in_action
        @ task execution via command line https://stackoverflow.com/questions/71335742/how-do-i-specify-default-values-for-gradle-custom-java-task-inputs */
    String salute = "Hello, this is StaticPropertiesPlugin#HelloWorldTask";

    @Input
    public String getSalute() {
        return salute;
    }

    /* salute parameter is configurable via gradle DSL */
    public void setSalute(String salute) {
        this.salute = salute;
    }

    @Override
    public String getDescription() {
        return "Prints a salute message which is configurable via gradle DSL on task registration.";
    }

    @TaskAction
    public void execute() {
        System.out.println("Your salute message is: " + getSalute());
    }
}
