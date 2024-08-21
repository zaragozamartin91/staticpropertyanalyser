package io.github.zaragozamartin91.staticpropertyanalyser;

import io.github.zaragozamartin91.staticpropertyanalyser.task.HelloWorldTask;
import java.io.File;
import java.util.Set;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSetContainer;

public class StaticPropertiesPlugin implements Plugin<Project> {

    public static final String PLUGIN_GROUP = "StaticProperties";

    @Override
    public void apply(Project project) {
        Task listResourceDirsTask = project.task("listResourceDirs");
        listResourceDirsTask.setGroup(PLUGIN_GROUP);
        listResourceDirsTask.doLast(task -> {
            var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            sourceSets.forEach(sourceSet -> {
                System.out.println("Source set " + sourceSet.getName());
                SourceDirectorySet resourcesDirSet = sourceSet.getResources();
                Set<File> srcDirs = resourcesDirSet.getSrcDirs();
                System.out.println("srcDirs = " + srcDirs);
            });
        });

        /* NOT specifying the group of a task makes it hidden: https://docs.gradle.org/current/userguide/more_about_tasks.html#sec:hidden_tasks */
        /* Creating ta task this way makes it ready to be used via gradle commands. */
        HelloWorldTask helloWorldTask = project.getTasks().create(HelloWorldTask.TASK_NAME, HelloWorldTask.class);
        helloWorldTask.setGroup(PLUGIN_GROUP);
    }
}
