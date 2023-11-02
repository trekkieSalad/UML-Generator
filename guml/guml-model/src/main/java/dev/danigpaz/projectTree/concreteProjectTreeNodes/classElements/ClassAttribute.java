package dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements;

import dev.danigpaz.export.UmlExportable;
import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;

import java.util.HashSet;
import java.util.Set;

public class ClassAttribute implements UmlExportable {
    private String name;
    private String type;
    private String value;
    private Set<ModifierType> modifiers = new HashSet<>();
    private ProjectClass projectClass;

    public ClassAttribute() {
    }

    public ClassAttribute(String name) {
        this.name = name;
    }

    public ClassAttribute(String name, String type, String value, Set<ModifierType> modifiers, ProjectClass projectClass) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.modifiers = modifiers;
        this.projectClass = projectClass;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type == null ? "" : type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<ModifierType> getModifiers() {
        return modifiers;
    }

    public void setModifiers(Set<ModifierType> modifiers) {
        this.modifiers = modifiers;
    }

    public void addModifier(ModifierType modifier){
        modifiers.add(modifier);
    }

    public ProjectClass getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(ProjectClass projectClass) {
        this.projectClass = projectClass;
    }

    @Override
    public String export(UmlExporter exporter, boolean verbose) {
        return exporter.exportAttribute(this, verbose);
    }
}
