package io.github.zaragozamartin91.staticpropertyanalyser.service;

import java.nio.file.Path;

public class ResourceFileQuery {
    private ResourceSetDirQuery resourceSetDirQuery;
    private Path path;

    public ResourceFileQuery(String resourceSetName, Path path) {
        this.resourceSetDirQuery = new ResourceSetDirQuery(resourceSetName);
        this.path = validPath(path);
    }

    private Path validPath(Path path) {
        if (path == null || path.toString().isBlank()) {
            throw new IllegalArgumentException("Invalid path");
        }

        return path;
    }

    public String getResourceSetName() {
        return resourceSetDirQuery.getResourceSetName();
    }

    public Path getPath() {
        return path;
    }
}
