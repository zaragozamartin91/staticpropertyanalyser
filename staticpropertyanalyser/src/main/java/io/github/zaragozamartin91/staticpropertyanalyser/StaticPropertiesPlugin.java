package io.github.zaragozamartin91.staticpropertyanalyser;

import java.io.File;
import java.util.Set;
import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.resources.ResourceHandler;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

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

        Task listResourceDirsTask = project.task("listResourceDirs");
        listResourceDirsTask.setGroup("parseGroup");
        listResourceDirsTask.doLast(task -> {
            var sourceSets = project.getExtensions().getByType(SourceSetContainer.class);
            sourceSets.stream().forEach(sourceSet -> {
                System.out.println("Source set " + sourceSet.getName());
                SourceDirectorySet resourcesDirSet = sourceSet.getResources();
                Set<File> srcDirs = resourcesDirSet.getSrcDirs();
                System.out.println("srcDirs = " + srcDirs);
            });
        });
    }
}
