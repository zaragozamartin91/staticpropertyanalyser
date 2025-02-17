package io.github.zaragozamartin91.staticpropertyanalyser.service;

import io.github.zaragozamartin91.staticpropertyanalyser.model.ResourceSetType;

public class ResourceSetDirQuery {
    private ResourceSetType resourceSetType;

    public ResourceSetDirQuery(ResourceSetType resourceSetType) {
        this.resourceSetType = resourceSetType;
    }

    public ResourceSetType getResourceSetType() {
        return resourceSetType;
    }

    public boolean resourceSetMatches(String resourceSetType) {
        return this.resourceSetType.matches(resourceSetType);
    }

    @Override
    public String toString() {
        return "ResourceSetDirQuery{" +
                "resourceSetType=" + resourceSetType +
                '}';
    }
}
