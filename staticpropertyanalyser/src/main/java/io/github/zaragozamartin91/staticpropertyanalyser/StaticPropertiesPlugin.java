package io.github.zaragozamartin91.staticpropertyanalyser;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.resources.ResourceHandler;

public class StaticPropertiesPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        String f = project.hasProperty("mainFile")
                ? "main file is " + project.getProperties().get("mainFile")
                : "";

        Task parseTask = project.task("parse");
        parseTask.setGroup("parseGroup");
        parseTask.doLast(task -> {
            System.out.println("This is StaticPropertiesPlugin! " + f);
            System.out.println("Module name: " + project.getName());
            System.out.println("Module directory: " + project.getProjectDir());
        });
    }
}
