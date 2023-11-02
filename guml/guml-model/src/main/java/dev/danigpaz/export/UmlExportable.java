package dev.danigpaz.export;

public interface UmlExportable {

    /**
     * Generates a UML string representation of the object
     * @param exporter The exporter to use
     * @param verbose Whether to include all the information or not
     * @return The UML string representation of the object
     */
    String export(UmlExporter exporter, boolean verbose);
}
