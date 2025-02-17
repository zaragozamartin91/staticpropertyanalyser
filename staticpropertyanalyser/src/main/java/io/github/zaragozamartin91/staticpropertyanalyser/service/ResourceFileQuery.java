package io.github.zaragozamartin91.staticpropertyanalyser.service;

import io.github.zaragozamartin91.staticpropertyanalyser.model.ResourceSetType;
import java.nio.file.Path;

public class ResourceFileQuery {
    private ResourceSetDirQuery resourceSetDirQuery;
    private Path path;

    public ResourceFileQuery(ResourceSetType resourceSetType, Path path) {
        this.resourceSetDirQuery = new ResourceSetDirQuery(resourceSetType);
        this.path = validPath(path);
    }

    private Path validPath(Path path) {
        if (path == null || path.toString().isBlank()) {
            throw new IllegalArgumentException("Invalid path");
        }

        return path;
    }

    public ResourceSetType getResourceSetType() {
        return resourceSetDirQuery.getResourceSetType();
    }

    public Path getPath() {
        return path;
    }

    public boolean resourceSetMatches(String resourceSetType) {
        return resourceSetDirQuery.resourceSetMatches(resourceSetType);
    }

    public boolean pathMatches(Path path) {
        return path.endsWith(this.path);
    }

    @Override
    public String toString() {
        return "ResourceFileQuery{" +
                "resourceSetDirQuery=" + resourceSetDirQuery +
                ", path=" + path +
                '}';
    }
}
