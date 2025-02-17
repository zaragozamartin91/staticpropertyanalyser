package io.github.zaragozamartin91.staticpropertyanalyser.service;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;

public class ProjectFileSystem {
    Project project;

    public ProjectFileSystem(Project project) {
        this.project = project;
    }

    public Optional<File> queryResourceFile(ResourceFileQuery resourceFileQuery) {
        // Source sets queried following https://discuss.gradle.org/t/custom-plugin-how-to-find-all-defined-source-sets/39051
        SourceSetContainer sourceSets = project.getExtensions().getByType(SourceSetContainer.class);

        List<File> fileMatches = sourceSets.stream()
                                           .filter(sourceSet -> resourceFileQuery.resourceSetMatches(sourceSet.getName()))
                                           .map(SourceSet::getResources)
                                           .map(SourceDirectorySet::getSrcDirs)
                                           .flatMap(Collection::stream)
                                           .flatMap(resourceDirectory -> Optional.ofNullable(resourceDirectory.listFiles()).stream())
                                           .flatMap(Arrays::stream)
                                           .filter(f -> resourceFileQuery.pathMatches(f.toPath()))
                                           .toList();

        if (fileMatches.isEmpty()) {
            System.err.printf("Resource for path %s not found%n", resourceFileQuery);
            return Optional.empty();
        }

        if (fileMatches.size() > 1) {
            throw new IllegalStateException("There are multiple sources with matching criteria: %s".formatted(fileMatches.toString()));
        }

        Optional<File> match = fileMatches.stream().findFirst();
        System.out.println("Matching resource file found: " + match.get());
        return match;
    }

    public File siblingFile(File file, String suffix) {
        return new File(file.getParentFile(), file.getName() + suffix);
    }
}
