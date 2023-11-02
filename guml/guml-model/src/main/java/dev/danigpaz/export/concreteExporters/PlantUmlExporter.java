package dev.danigpaz.export.concreteExporters;

import java.io.File;
import java.nio.charset.Charset;
import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.projectTree.ProjectTreeNode;
import dev.danigpaz.projectTree.ProjectTreeNodeType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectPackage;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ProjectClassType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassAttribute;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassMethod;
import net.sourceforge.plantuml.FileFormat;
import net.sourceforge.plantuml.FileFormatOption;
import net.sourceforge.plantuml.SourceStringReader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

/**
 * Concrete implementation of {@link UmlExporter} that exports the project tree to PlantUML format.
 */
public class PlantUmlExporter implements UmlExporter {


    public String export(ProjectTreeNode projectTree, boolean verbose) {
        UmlExporter exporter = new PlantUmlExporter();
        StringBuilder sb = new StringBuilder();

        // PlantUML header
        sb.append("@startuml\n");
        sb.append("skinparam packageStyle frame\n");

        // Generate package structure
        sb.append(projectTree.export(exporter, verbose));

        sb.append("\n");

        // Generate classes
        for (ProjectTreeNode actualNode : projectTree) {
            if (actualNode.getType().equals(ProjectTreeNodeType.CLASS_NODE)) {
                sb.append(actualNode.export(exporter, verbose));
            }
        }

        sb.append("@enduml");
        return sb.toString();
    }

    @Override
    public String exportImage(ProjectTreeNode node, boolean verbose) {
        String uml = export(node, verbose);

        SourceStringReader reader = new SourceStringReader(uml);
        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        // Escribe la primera imagen a "os"
        try {
            String desc = reader.generateImage(os, new FileFormatOption(FileFormat.SVG));
            os.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // El XML se guarda en svg
        final String svg = new String(os.toByteArray(), Charset.forName("UTF-8"));

        return svg;
    }

    @Override
    public String exportAttribute(ClassAttribute attribute, boolean verbose) {
        Set<ModifierType> modifiers = attribute.getModifiers();
        StringBuilder sb = new StringBuilder();

        // Set visibility modifier
        if (modifiers.contains(ModifierType.PUBLIC))            sb.append("+");
        else if (modifiers.contains(ModifierType.PRIVATE))      sb.append("-");
        else if (modifiers.contains(ModifierType.PROTECTED))    sb.append("#");
        else                                                    sb.append("~");

        sb.append(attribute.getName());

        // If the attribute has a type, add it to the string
        if (attribute.getType() != null && !attribute.getType().isEmpty()) {
            sb.append(" : ");
            sb.append(attribute.getType());
        }

        // Add modifiers to the string
        if (modifiers.contains(ModifierType.FINAL))             sb.append("{readOnly}");
        if (modifiers.contains(ModifierType.STATIC))            sb.append("{static}");

        return sb.toString();
    }

    @Override
    public String exportMethod(ClassMethod method, boolean verbose) {
        Set<ModifierType> modifiers = method.getModifiers();
        StringBuilder sb = new StringBuilder();

        // Set visibility modifier
        if (modifiers.contains(ModifierType.PUBLIC))            sb.append("+");
        else if (modifiers.contains(ModifierType.PRIVATE))      sb.append("-");
        else if (modifiers.contains(ModifierType.PROTECTED))    sb.append("#");
        else                                                    sb.append("~");

        sb.append(method.getName());
        sb.append("(");

        // Add parameters to the string with their types
        for (ClassAttribute parameter : method.getParameters()) {
            sb.append(parameter.getName());
            sb.append(" : ");
            sb.append(parameter.getType());
            sb.append(", ");
        }

        // Remove the last ", " from the string
        if (sb.charAt(sb.length() - 1) == ' ') {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append(") : ");
        sb.append(method.getReturnType());

        // Add modifiers to the string
        if (modifiers.contains(ModifierType.STATIC))            sb.append("{static}");
        if (modifiers.contains(ModifierType.ABSTRACT))          sb.append("{abstract}");
        if (modifiers.contains(ModifierType.FINAL))             sb.append("{leaf}");

        return sb.toString();
    }

    @Override
    public String exportClass(ProjectClass projectClass, boolean verbose) {
        Set<ModifierType> modifiers = projectClass.getModifiers();
        StringBuilder sb = new StringBuilder();

        if (modifiers.contains(ModifierType.ABSTRACT))          sb.append("abstract ");

        if (projectClass.getClassType().equals(ProjectClassType.CLASS)) sb.append("class ");
        else if (projectClass.getClassType().equals(ProjectClassType.INTERFACE)) sb.append("interface ");
        else if (projectClass.getClassType().equals(ProjectClassType.ENUM)) sb.append("enum ");
        else if (projectClass.getClassType().equals(ProjectClassType.ANNOTATION)) sb.append("annotation ");

        sb.append(projectClass.getFullName());

        sb.append(" {\n");

        if (verbose) {
            // if verbose, export attributes and methods
            for (ClassAttribute attribute : projectClass.getAttributes()) {
                sb.append("\t");
                sb.append(attribute.export(this, verbose));
                sb.append("\n");
            }

            for (ClassMethod method : projectClass.getMethods()) {
                sb.append("\t");
                sb.append(method.export(this, verbose));
                sb.append("\n");
            }
        }

        sb.append("}\n");

        // Export relationships
        for (ProjectClass dep : projectClass.getDependencies()) {
            sb.append(projectClass.getFullName()).append(" -- ").append(dep.getFullName()).append("\n");
        }
        for (ProjectClass in : projectClass.getInnerClasses()) {
            sb.append(projectClass.getFullName()).append(" +-- ").append(in.getFullName()).append("\n");
        }
        for (ProjectClass sc : projectClass.getSuperClasses()) {
            sb.append(projectClass.getFullName()).append(" --|> ").append(sc.getFullName()).append("\n");
        }
        for (ProjectClass im : projectClass.getInterfaces()) {
            sb.append(projectClass.getFullName()).append(" ..|> ").append(im.getFullName()).append("\n");
        }

        return  sb.toString();
    }

    @Override
    public String exportPackage(ProjectPackage projectPackage, boolean verbose) {
        StringBuilder sb = new StringBuilder();

        sb.append("package ");
        sb.append(projectPackage.getName());
        sb.append(" {\n");

        // Export subpackages (recursively) generating a package tree
        for (ProjectPackage subPackage : projectPackage.getSubPackages()) {
            sb.append("\t");
            sb.append(subPackage.export(this, verbose));
            sb.append("\n");
        }

        sb.append("}");

        return sb.toString();
    }
}
