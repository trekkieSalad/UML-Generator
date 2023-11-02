package dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements;

import dev.danigpaz.export.UmlExportable;
import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ClassMethod implements UmlExportable {
    private String name;
    private String returnType;
    private Set<ModifierType> modifiers = new HashSet<>();
    private List<ClassAttribute> parameters = new ArrayList<>();
    private ProjectClass projectClass;

    public ClassMethod() {
    }

    public ClassMethod(String name) {
        this.name = name;
    }

    public ClassMethod(String name, String returnType, List<String> decorators, Set<ModifierType> modifiers,
                       List<ClassAttribute> parameters, ProjectClass projectClass) {
        this.name = name;
        this.returnType = returnType;
        this.modifiers = modifiers;
        this.parameters = parameters;
        this.projectClass = projectClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Set<ModifierType> getModifiers() {
        return modifiers;
    }

    public void setModifiers(Set<ModifierType> modifiers) {
        this.modifiers = modifiers;
    }

    public void addModifier(ModifierType modifier) {
        this.modifiers.add(modifier);
    }

    public List<ClassAttribute> getParameters() {
        return parameters;
    }

    public void setParameters(List<ClassAttribute> parameters) {
        this.parameters = parameters;
    }

    public void addParameter(ClassAttribute parameter) {
        this.parameters.add(parameter);
    }

    public ProjectClass getProjectClass() {
        return projectClass;
    }

    public void setProjectClass(ProjectClass projectClass) {
        this.projectClass = projectClass;
    }

    @Override
    public String export(UmlExporter exporter, boolean verbose) {
        return exporter.exportMethod(this, verbose);
    }
}
