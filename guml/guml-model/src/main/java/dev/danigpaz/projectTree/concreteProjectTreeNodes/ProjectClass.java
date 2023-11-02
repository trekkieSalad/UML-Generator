package dev.danigpaz.projectTree.concreteProjectTreeNodes;

import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.projectTree.ProjectTreeNode;
import dev.danigpaz.projectTree.ProjectTreeNodeType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ProjectClassType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassAttribute;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassMethod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProjectClass extends ProjectTreeNode {
    private ProjectClassType classType;
    private Set<ModifierType> modifiers;
    private Set<ProjectClass> interfaces;
    private Set<ProjectClass> superClasses;
    private Set<ProjectClass> dependencies;
    private Set<ProjectPackage> dependenciesPackages;
    private Set<ProjectClass> calls;
    private Set<ClassAttribute> attributes;
    private Set<ClassMethod> methods;

    //  Constructors

    /**
     * Default constructor
     */
    public ProjectClass() {
        super();
        classType = ProjectClassType.CLASS;
        modifiers = new HashSet<>();
        interfaces = new HashSet<>();
        superClasses = new HashSet<>();
        dependencies = new HashSet<>();
        calls = new HashSet<>();
        dependenciesPackages = new HashSet<>();
        attributes = new HashSet<>();
        methods = new HashSet<>();

    }

    /**
     * Constructor with name
     * @param name name of the class
     */
    public ProjectClass(String name) {
        super(name);
        classType = ProjectClassType.CLASS;
        modifiers = new HashSet<>();
        interfaces = new HashSet<>();
        superClasses = new HashSet<>();
        dependencies = new HashSet<>();
        calls = new HashSet<>();
        dependenciesPackages = new HashSet<>();
        attributes = new HashSet<>();
        methods = new HashSet<>();
    }

    /**
     * Getter for the class type
     * @return type of the class (Class, Interface, Enum)
     */
    public ProjectClassType getClassType() {
        return classType;
    }

    /**
     * Setter for the class type
     * @param classType type of the class (Class, Interface, Enum)
     */
    public void setClassType(ProjectClassType classType) {
        this.classType = classType;
    }


    @Override
    public ProjectTreeNodeType getType() {
        return ProjectTreeNodeType.CLASS_NODE;
    }

    /**
     * Getter for the inner classes
     * @return List of classes
     */
    public List<ProjectClass> getInnerClasses(){
        List<ProjectClass> classes = new java.util.ArrayList<>();
        for (ProjectTreeNode child : childrens) {
            if (child.getType() == ProjectTreeNodeType.CLASS_NODE) classes.add((ProjectClass) child);
        }
        return classes;
    }

    /**
     * Getter of the modifiers of the class
     * @return Set of modifiers
     */
    public Set<ModifierType> getModifiers() {
        return modifiers;
    }

    /**
     * Setter of the modifiers of the class
     * @param modifiers Set of modifiers
     */
    public void setModifiers(Set<ModifierType> modifiers) {
        this.modifiers = modifiers;
    }

    /**
     * Add a modifier to the class
     * @param modifier modifier to add
     */
    public void addModifier(ModifierType modifier){
        this.modifiers.add(modifier);
    }

    /**
     * Getter for the interfaces that the class implements
     * @return Set of interfaces
     */
    public Set<ProjectClass> getInterfaces() {
        return interfaces;
    }

    /**
     * Setter for the interfaces that the class implements
     * @param interfaces Set of interfaces
     */
    public void setInterfaces(Set<ProjectClass> interfaces) {
        this.interfaces = interfaces;
    }

    /**
     * Add an interface to the class
     * @param interfaceClass interface to add
     */
    public void addInterface(ProjectClass interfaceClass){
        this.interfaces.add(interfaceClass);
    }

    /**
     * Getter for the super classes of the class
     * @return Set of super classes
     */
    public Set<ProjectClass> getSuperClasses() {
        return superClasses;
    }

    /**
     * Setter for the super classes of the class
     * @param superClasses Set of super classes
     */
    public void setSuperClasses(Set<ProjectClass> superClasses) {
        this.superClasses = superClasses;
    }

    /**
     * Add a super class to the class
     * @param superClass Class that represents the super class
     */
    public void addSuperClass(ProjectClass superClass){
        this.superClasses.add(superClass);
    }

    /**
     * Search a dependency of the class by its name
     * @param name name of the dependency
     * @return Class that represents the dependency
     */
    public ProjectClass searchDependencyByName(String name){
        for (ProjectClass dependency : dependencies) {
            if (dependency.getName().equals(name)) return dependency;
        }
        for (ProjectPackage dependency : dependenciesPackages) {
            ProjectClass dependencyClass = dependency.searchClass(name);
            if (dependencyClass != null) return dependencyClass;
        }
        return null;
    }

    /**
     * Add a dependency to the class
     * @param dependency Class that represents the dependency
     */
    public void addDependency(ProjectClass dependency){
        this.dependencies.add(dependency);
    }

    /**
     * Add a dependency package to the class
     * @param dependency Package that represents the dependency
     */
    public void addDependency(ProjectPackage dependency){
        this.dependenciesPackages.add(dependency);
    }

    /**
     * Getter for the dependencies of the class
     * @return Set of dependencies
     */
    public Set<ProjectClass> getDependencies(){
        return this.dependencies;
    }

    /**
     * Add a class that calls the class
     * @param caller Class that calls the class
     */
    public void addCall(ProjectClass caller){
        this.calls.add(caller);
    }

    /**
     * Getter for the calls of the class
     * @return Set of classes that call the class
     */
    public Set<ProjectClass> getCalls(){
        return this.calls;
    }

    /**
     * Remove a class that calls the class
     * @param caller Class that calls the class
     */
    public void removeCall(ProjectClass caller){
        this.calls.remove(caller);
    }

    /**
     * Setter for the calls of the class
     * @param calls Set of classes that call the class
     */
    public void setCalls(Set<ProjectClass> calls){
        this.calls = calls;
    }

    /**
     * Add an attribute to the class
     * @param attribute attribute to add
     */
    public void addAttribute(ClassAttribute attribute){
        this.attributes.add(attribute);
    }

    /**
     * Getter for the attributes of the class
     * @return Set of attributes
     */
    public Set<ClassAttribute> getAttributes(){
        return this.attributes;
    }

    /**
     * Setter for the attributes of the class
     * @param attributes Set of attributes
     */
    public void setAttributes(Set<ClassAttribute> attributes){
        this.attributes = attributes;
    }

    /**
     * Getter for the methods of the class
     * @return Set of methods
     */
    public Set<ClassMethod> getMethods() {
        return methods;
    }

    /**
     * Setter for the methods of the class
     * @param methods Set of methods
     */
    public void setMethods(Set<ClassMethod> methods) {
        this.methods = methods;
    }

    /**
     * Add a method to the class
     * @param method method to add
     */
    public void addMethod(ClassMethod method){
        this.methods.add(method);
    }

    public void expandDependencies(){
        for (ProjectPackage dependency : dependenciesPackages) {
            for (ProjectClass dependencyClass : dependency.getClasses()) {
                this.addDependency(dependencyClass);
                dependencyClass.addCall(this);
            }
        }
        for (ProjectTreeNode child : childrens) {
            child.expandDependencies();
        }
    }

    public void reduceDependencies(){
        for (ProjectTreeNode child : childrens) child.reduceDependencies();

        Set<ProjectClass> dependenciesToRemove = new HashSet<>();
        for (ProjectClass dependency : dependencies)
            if (dependency.isEmpty()) dependenciesToRemove.add(dependency);

        for (ProjectClass dependency : dependenciesToRemove) {
            this.dependencies.remove(dependency);
            dependency.removeCall(this);
        }
    }

    public void reduceNode(){
        Set<ProjectTreeNode> childrensToRemove = new HashSet<>();
        for (ProjectTreeNode child : childrens) {
            child.reduceNode();
            if (child.isEmpty()) childrensToRemove.add(child);
        }
        for (ProjectTreeNode child : childrensToRemove) {
            this.childrens.remove(child);
            child.setParent(null);
        }
        Set<ProjectClass> newSuperClasses = new HashSet<>();
        for (ProjectClass superClass : superClasses) {
            for (ProjectClass dependency : dependencies) {
                if (superClass.getName().equals(dependency.getName())) {
                    newSuperClasses.add(dependency);
                    dependencies.remove(dependency);
                    break;
                }
            }
        }
        superClasses = newSuperClasses;
        Set<ProjectClass> newInterfaces = new HashSet<>();
        for (ProjectClass interfc : interfaces) {
            for (ProjectClass dependency : dependencies) {
                if (interfc.getName().equals(dependency.getName())) {
                    newInterfaces.add(dependency);
                    dependencies.remove(dependency);
                    break;
                }
            }
        }
        interfaces = newInterfaces;

    }

    public boolean isEmpty(){
        if (isLeaf()) return (attributes.isEmpty() && methods.isEmpty());
        else return false;
    }

    @Override
    public String export(UmlExporter exporter, boolean verbose) {
        return exporter.exportClass(this, verbose);
    }
}
