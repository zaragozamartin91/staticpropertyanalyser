package io.github.zaragozamartin91.staticpropertyanalyser.task;

import io.github.zaragozamartin91.staticpropertyanalyser.model.ResourceProperties;
import io.github.zaragozamartin91.staticpropertyanalyser.service.ProjectFileSystem;
import io.github.zaragozamartin91.staticpropertyanalyser.service.ResourceFileQuery;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Properties;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.options.Option;

public class SortPropertiesTask extends DefaultTask {
    public static final String TASK_NAME = "sortProperties";

    @Override
    public String getDescription() {
        return "Sorts properties";
    }

    private String resourceFile = "";

    @Input
    public String getResourceFile() {
        return resourceFile;
    }

    @Option(option = "resourceFile", description = "resourceFile to read the properties from.")
    public void setResourceFile(String resourceFile) {
        this.resourceFile = resourceFile;
    }

    private String resourceSet = "main";

    @Input
    public String getResourceSet() {
        return resourceSet;
    }

    @Option(option = "resourceSet", description = "Specifies resource set. Default is 'main'.")
    public void setResourceSet(String resourceSet) {
        this.resourceSet = resourceSet;
    }


    @TaskAction
    public void execute() {
        // find the properties file <- file input | profile key
        ResourceFileQuery resourceSetFileQuery = new ResourceFileQuery(resolveResourceSet(), resolveFilePath());
        ProjectFileSystem projectFileSystem = new ProjectFileSystem(getProject());
        File resourceFile = projectFileSystem.queryResourceFile(resourceSetFileQuery).orElseThrow();

        // read the properties file
        ResourceProperties resourceProperties = ResourceProperties.fromFile(resourceFile);
        System.out.println("Unsorted properties ================");
        resourceProperties.listProperties();

        ResourceProperties sorted = resourceProperties.sort();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sorted.store(byteArrayOutputStream, "Sorted properties ================");
        System.out.println(byteArrayOutputStream.toString(StandardCharsets.UTF_8));

        String suffix = "." + Instant.now().toEpochMilli();
        File siblingFile = projectFileSystem.siblingFile(resourceFile, suffix);
        try (var fos = new java.io.FileOutputStream(siblingFile)) {
            sorted.store(fos, "Sorted properties");
        } catch (java.io.IOException e) {
            throw new IllegalStateException("Error while writing sorted properties to file", e);
        }
    }

    private Path resolveFilePath() {
        // todo : make use of extensions
        return Path.of(getResourceFile());
    }

    private String resolveResourceSet() {
        // todo : make use of extensions
        return getResourceSet();
    }
}
