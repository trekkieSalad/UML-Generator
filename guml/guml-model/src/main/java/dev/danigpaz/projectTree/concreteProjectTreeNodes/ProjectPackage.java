package dev.danigpaz.projectTree.concreteProjectTreeNodes;

import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.projectTree.ProjectTreeNode;
import dev.danigpaz.projectTree.ProjectTreeNodeType;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectPackage extends ProjectTreeNode {

    public ProjectPackage() {
        super();
    }

    public ProjectPackage(String name) {
        super(name);
    }

    @Override
    public ProjectTreeNodeType getType() {
        return ProjectTreeNodeType.PACKAGE_NODE;
    }

    public List<ProjectPackage> getSubPackages(){
        List<ProjectPackage> packages = new ArrayList<>();
        for (ProjectTreeNode child : childrens) {
            if (child.getType() == ProjectTreeNodeType.PACKAGE_NODE) packages.add((ProjectPackage) child);
        }
        return packages;
    }

    public List<ProjectClass> getClasses(){
        List<ProjectClass> classes = new ArrayList<>();
        for (ProjectTreeNode child : childrens) {
            if (child.getType() == ProjectTreeNodeType.CLASS_NODE) classes.add((ProjectClass) child);
        }
        return classes;
    }

    public ProjectClass searchClass(String name){
        for (ProjectTreeNode child : childrens) {
            if (child.getType() == ProjectTreeNodeType.CLASS_NODE && child.getName().equals(name)) return (ProjectClass) child;
        }
        return null;
    }

    public void expandDependencies(){
        for (ProjectTreeNode child : childrens) {
            child.expandDependencies();
        }
    }

    public void reduceDependencies(){
        for (ProjectTreeNode child : childrens) {
            child.reduceDependencies();
        }
    }

    public void reduceNode(){
        Set<ProjectTreeNode> childrensToRemove = new HashSet<>();
        for (ProjectTreeNode child : childrens) {
            child.reduceNode();
            if (child.isEmpty()) childrensToRemove.add(child);
        }
        for (ProjectTreeNode child : childrensToRemove) {
            childrens.remove(child);
            child.setParent(null);
        }
        if (getClasses().isEmpty()){
            if (getSubPackages().size() == 1){
                ProjectPackage subPackage = getSubPackages().get(0);
                name = name + "." + subPackage.getName();
                childrens = subPackage.childrens;
                for (ProjectTreeNode child : childrens) {
                    child.setParent(this);
                }
                subPackage.childrens = new HashSet<>();
                subPackage.setParent(null);
            }
        }
    }

    public boolean isEmpty(){
        return isLeaf();
    }


    @Override
    public String export(UmlExporter exporter, boolean verbose) {
        return exporter.exportPackage(this, verbose);
    }
}
