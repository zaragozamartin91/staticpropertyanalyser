package io.github.zaragozamartin91.staticpropertyanalyser.service;

import java.util.Optional;
import java.util.function.Predicate;

public class ResourceSetDirQuery {
    private String resourceSetName;

    public ResourceSetDirQuery(String resourceSetName) {
        this.resourceSetName = validResourceSetName(resourceSetName);
    }

    private String validResourceSetName(String resourceSetName) {
        return Optional.ofNullable(resourceSetName)
                       .filter(Predicate.not(String::isBlank))
                       .map(String::toLowerCase)
                       .map(String::strip)
                       .filter(s -> s.matches("main|test"))
                       .orElseThrow(() -> new IllegalArgumentException("Invalid resource set name"));
    }

    public String getResourceSetName() {
        return resourceSetName;
    }
}
