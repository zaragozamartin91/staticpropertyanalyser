package io.github.zaragozamartin91.staticpropertyanalyser.model;

import java.util.Objects;

public enum ResourceSetType {
    MAIN, TEST;

    public static ResourceSetType fromString(String sourceSetId) {
        return switch (sourceSetId.toLowerCase()) {
            case "main" -> MAIN;
            case "test" -> TEST;
            default -> throw new IllegalArgumentException("Invalid source set id");
        };
    }

    public boolean matches(String other) {
        return Objects.requireNonNullElse(other, "").equalsIgnoreCase(this.name());
    }
}
