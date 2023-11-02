package dev.danigpaz.export;

import dev.danigpaz.export.concreteExporters.PlantUmlExporter;
import dev.danigpaz.treeManagerExceptions.CreationExporterException;

/**
 * Enum that contains all the available exporters
 */
public enum ExporterTypes {

    PLANTUML("PlantUML", new PlantUmlExporter()),
    YUML("YUML", null);

    /**
     * The name of the exporter
     */
    private final String name;

    /**
     * The exporter
     */
    private UmlExporter exporter;

    /**
     * Returns the exporter type that matches the given string
     * @param type The string to match
     * @return The exporter type that matches the given string
     */
    public static ExporterTypes getExporterType(String type) {
        type = type.toUpperCase();
        for (ExporterTypes eType : ExporterTypes.values()) {
            if (eType.toString().equals(type)) return eType;
        }
        return null;
    }

    /**
     * Constructor for the enum
     * @param name The name of the exporter
     * @param exporter The exporter
     */
    ExporterTypes(String name, UmlExporter exporter) {
        this.name = name;
        this.exporter = exporter;
    }

    /**
     * Getter for the name of the exporter
     * @return The name of the exporter
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the exporter that the enum represents
     * @return The exporter
     * @throws CreationExporterException If the exporter is not implemented yet
     */
    public UmlExporter getExporter() throws CreationExporterException {
        if (exporter == null) throw new CreationExporterException("Exporter for " + name + " not implemented yet");
        return exporter;
    }
}
