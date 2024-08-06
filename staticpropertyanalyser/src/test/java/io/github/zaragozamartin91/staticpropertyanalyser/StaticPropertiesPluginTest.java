package io.github.zaragozamartin91.staticpropertyanalyser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.SortedMap;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

class StaticPropertiesPluginTest {

    @Test
    void apply() {
        Project project = ProjectBuilder.builder().build();
        project.getPluginManager().apply( "io.github.zaragozamartin91.staticpropertyanalyser");
        SortedMap<String, Task> taskMap = project.getTasks().getAsMap();

        assertTrue(taskMap.containsKey("listResourceDirs"));
        assertTrue(taskMap.containsKey("helloWorld"));
    }
}