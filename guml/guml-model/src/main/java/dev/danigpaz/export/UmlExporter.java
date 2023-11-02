package dev.danigpaz.export;

import dev.danigpaz.projectTree.ProjectTreeNode;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectPackage;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassAttribute;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassMethod;

public interface UmlExporter {

    /**
     * Generates a PlantUML string from the given project tree.
     * @return the type of the exporter.
     */
    public String export(ProjectTreeNode node, boolean verbose);

    public String exportImage(ProjectTreeNode node, boolean verbose);


    /**
     * Generates a UML string representation from the given attribute.
     * @param attribute The attribute to export.
     * @param verbose Whether to export the attribute in verbose mode.
     * @return A String representing the attribute in UML format.
     */
    String exportAttribute(ClassAttribute attribute, boolean verbose);

    /**
     * Generates a UML string representation from the given method.
     * @param method The method to export.
     * @param verbose Whether to export the method in verbose mode.
     * @return A String representing the method in UML format.
     */
    String exportMethod(ClassMethod method, boolean verbose);

    /**
     * Generates a UML string representation from the given class.
     * @param projectClass The class to export.
     * @param verbose Whether to export the class in verbose mode.
     * @return A String representing the class in UML format.
     */
    String exportClass(ProjectClass projectClass, boolean verbose);

    /**
     * Generates a UML string representation from the given package.
     * @param projectPackage The package to export.
     * @param verbose Whether to export the package in verbose mode.
     * @return A String representing the package in a UML format.
     */
    String exportPackage(ProjectPackage projectPackage, boolean verbose);
}
