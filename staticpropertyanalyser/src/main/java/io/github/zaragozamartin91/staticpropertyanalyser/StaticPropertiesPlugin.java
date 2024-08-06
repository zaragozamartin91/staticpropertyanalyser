package io.github.zaragozamartin91.staticpropertyanalyser;

import java.io.File;
import java.util.Map;
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
        Task parseTask = project.task("parse");
        parseTask.setGroup(PLUGIN_GROUP);
        parseTask.doLast(task -> {
            System.out.println("This is StaticPropertiesPlugin!");
            System.out.println("Module name: " + project.getName());
            System.out.println("Module directory: " + project.getProjectDir());
            Map<String, ?> projectProperties = project.getProperties();
            System.out.println("Project properties: " + projectProperties);
        });

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
    }
}
