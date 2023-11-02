package dev.danigpaz.parser.programmingLanguageParsers.java;

import dev.danigpaz.parser.ProjectTreeAdapter;
import dev.danigpaz.projectTree.ProjectTreeNode;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectClass;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.ProjectPackage;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ModifierType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElementProperties.ProjectClassType;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassAttribute;
import dev.danigpaz.projectTree.concreteProjectTreeNodes.classElements.ClassMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * This class has two functions, on the one hand it acts as an adapter between the parser
 * and the tree and, on the other hand, it acts as a facade.
 */
public class JavaProjectTreeAdapter implements ProjectTreeAdapter {
    /**
     * Root of the tree that will be returned
     */
    private ProjectTreeNode project;

    /**
     * List of classes that are being built
     */
    private List<ProjectClass> classes;

    /**
     * List of modifiers that are being built
     */
    private List<ModifierType> classModifiers;

    /**
     * Current package that is being built
     */
    private ProjectPackage currentPackage;

    /**
     * Current attribute that is being built
     */
    private ClassAttribute currentAttribute;

    /**
     * Current method that is being built
     */
    private ClassMethod currentMethod;

    /**
     * Current class type that is being built
     */
    private ProjectClassType currentClassType;

    /**
     * Counter that indicates the number of brackets that are open
     */
    public int bracketCount = 0;

    /**
     * Counter that indicates the number of parenthesis that are open
     */
    public int parenthesisCount = 0;

    /**
     * Boolean that indicates if the parser is inside a method
     */
    public boolean inEnum = false;

    //region Constructors and reset

    /**
     * Constructor of the class
     */
    public JavaProjectTreeAdapter() {
        this.project = new ProjectPackage("root");
        this.currentPackage = new ProjectPackage();
        this.classes = new ArrayList<>();
        this.currentMethod = new ClassMethod();
        this.currentAttribute = new ClassAttribute();
        classModifiers = new ArrayList<>();
    }

    /**
     * Reset the modifiers list
     */
    public void resetModifiers(){
        classModifiers = new ArrayList<>();
    }

    //endregion

    //region Packages

    /**
     * Add the last class created to the current package
     */
    public void addClassToCurrentPackage() {
        currentPackage.addChild(classes.get(classes.size() - 1));
        classes.remove(classes.size() - 1);
    }

    //endregion

    //region Classes

    /**
     * Add a new class to the list of classes
     */
    public void addClass() {
        classes.add(new ProjectClass());
    }

    /**
     * Check if the current class is an interface
     * @return true if the current class is an interface, false otherwise
     */
    public boolean isAnInterface(){
        return currentClassType == ProjectClassType.INTERFACE;
    }

    /**
     * Check if the current parse element is abstract
     * @return true if the current parse element is abstract, false otherwise
     */
    public boolean isAnAbstract(){
        return classModifiers.contains(ModifierType.ABSTRACT);
    }

    /**
     * Add the last class created to the previous class created
     */
    public void addInnerClassToCurrentClass() {
        classes.get(classes.size() - 2).addChild(classes.get(classes.size() - 1));
        classes.remove(classes.size() - 1);
    }

    /**
     * Add the modifiers to the last class created
     */
    public void addModifiersToClass(){
        for (ModifierType modifier : classModifiers) {
            classes.get(classes.size() - 1).addModifier(modifier);
        }
    }

    /**
     * Add the current attribute to the last class created
     */
    public void addAttributeToCurrentClass() {
        classes.get(classes.size() - 1).addAttribute(currentAttribute);
        currentAttribute = new ClassAttribute();
    }

    /**
     * Add the current method to the last class created
     */
    public void addMethodToCurrentClass() {
        classes.get(classes.size() - 1).addMethod(currentMethod);
        currentMethod = new ClassMethod();
    }

    /**
     * Setter of the name of the last class created
     * @param className name of the class
     */
    public void setClassName(String className) {
        classes.get(classes.size() - 1).setName(className);
    }

    /**
     * Setter of the current class type to the last class created
     */
    public void setDeclarationToCurrentClass(){
        classes.get(classes.size() - 1).setClassType(currentClassType);
        addModifiersToClass();
    }

    /**
     * Getter of the name of the last class created
     * @return name of the last class created
     */
    public String getClassName(){
        return classes.get(classes.size() - 1).getName();
    }

    /**
     * Set the current class type to class
     */
    public void isSimpleClass(){
        currentClassType = ProjectClassType.CLASS;
    }

    /**
     * Set the current class type to interface
     */
    public void isInterface(){
        currentClassType = ProjectClassType.INTERFACE;
    }

    /**
     * Set the current class type to enum
     */
    public void isEnum(){
        currentClassType = ProjectClassType.ENUM;
    }

    /**
     * Add an interface to the last class created
     * @param interfaceName name of the interface
     */
    public void addClassInterface(String interfaceName){
        // Search the interface in the known dependencies of the class
        ProjectClass writeClass = classes.get(classes.size() - 1);
        ProjectClass interfaceClass = writeClass.searchDependencyByName(interfaceName);

        // If the interface is not found, search it in the current package
        if (interfaceClass == null) {
            interfaceClass = currentPackage.searchClass(interfaceName);
            // If the interface is not found, create it
            if (interfaceClass == null){
                interfaceClass = new ProjectClass(interfaceName);
                interfaceClass.setClassType(ProjectClassType.INTERFACE);
                currentPackage.addChild(interfaceClass);
            }
            // Add the interface to the dependencies of the class
            writeClass.addDependency(interfaceClass);
            // Add the class to the calls of the interface
            interfaceClass.addCall(writeClass);
        }
        writeClass.addInterface(interfaceClass);
    }

    /**
     * Add a superclass to the last class created
     * @param superClass name of the superclass
     */
    public void addClassSuperClass(String superClass){
        ProjectClass writeClass = classes.get(classes.size() - 1);
        ProjectClass superClassClass = writeClass.searchDependencyByName(superClass);

        if(superClassClass == null){
            superClassClass = currentPackage.searchClass(superClass);
            if(superClassClass == null){
                superClassClass = new ProjectClass(superClass);
                currentPackage.addChild(superClassClass);
            }
            writeClass.addDependency(superClassClass);
            superClassClass.addCall(writeClass);
        }
        writeClass.addSuperClass(superClassClass);
    }


    //endregion

    //region Attributes

    /**
     * Setter of the name of the current attribute
     * @param value name of the attribute
     */
    public void setAttributeValue(String value){
        currentAttribute.setValue(value);
    }

    /**
     * Setter of the name of the current attribute
     * @param name name of the attribute
     */
    public void setAttributeName(String name){
        currentAttribute.setName(name);
    }

    /**
     * Setter of the type of the current attribute
     * @param type type of the attribute
     */
    public void setAttributeType(String type){
        currentAttribute.setType(type);
    }

    /**
     * Add the modifiers to the current attribute
     */
    public void addModifiersToAttribute(){
        for (ModifierType modifier : classModifiers) {
            currentAttribute.addModifier(modifier);
        }
    }

    //endregion

    //region Methods

    /**
     * Add the current attribute to the current method
     */
    public void addMethodParameter(){
        currentMethod.addParameter(currentAttribute);
        currentAttribute = new ClassAttribute();
    }

    /**
     * Setter of the name of the current method
     * @param name name of the method
     */
    public void setMethodName(String name){
        currentMethod.setName(name);
    }

    /**
     * Setter of the return type of the current method
     * @param type type of the method
     */
    public void setMethodReturnType(String type){
        currentMethod.setReturnType(type);
    }

    /**
     * Add the modifiers to the current method
     */
    public void addModifiersToMethod(){
        for (ModifierType modifier : classModifiers) {
            currentMethod.addModifier(modifier);
        }
    }

    //endregion

    //region Search methods

    /**
     * Search a package in the project
     * @param packageName name of the package
     * @return the package if it is found, null otherwise
     */
    public ProjectPackage searchOrCreatePackage(String packageName, boolean setAsCurrent) {
        String[] packageNames = packageName.split("\\.");
        ProjectPackage currentPackage = (ProjectPackage) project;
        for (String name : packageNames) {
            ProjectPackage subPackage = currentPackage.getSubPackages().stream().filter(p -> p.getName().equals(name)).findFirst().orElse(null);
            if (subPackage == null) {
                subPackage = new ProjectPackage(name);
                currentPackage.addChild(subPackage);
            }
            currentPackage = subPackage;
        }
        if (setAsCurrent) this.currentPackage = currentPackage;
        return currentPackage;
    }

    /**
     * Search a class in the current package
     * @param className name of the class
     */
    public void searchClassOnCurrentPath(String className) {
        ProjectClass projectClass = currentPackage.getClasses().stream().filter(c -> c.getName().equals(className)).findFirst().orElse(null);
        if (projectClass != null) {
            for (ProjectClass dependency : projectClass.getCalls()) {
                classes.get(classes.size() - 1).addCall(dependency);
                dependency.addDependency(classes.get(classes.size() - 1));
            }
        }
    }

    /**
     * Search a class in the project and create it if it is not found
     * @param classPath path of the class
     * @return the class
     */
    public ProjectClass searchOrCreateClass(String classPath) {
        String[] classPathParts = classPath.split("\\.");
        String className = classPathParts[classPathParts.length - 1];
        String packageName = String.join(".", Arrays.copyOfRange(classPathParts, 0, classPathParts.length - 1));

        ProjectPackage packageNode = searchOrCreatePackage(packageName, false);
        ProjectClass projectClass = packageNode.getClasses().stream().filter(c -> c.getName().equals(className)).findFirst().orElse(null);
        if (projectClass == null && !className.equals("*")) {
            projectClass = new ProjectClass(className);
            packageNode.addChild(projectClass);
        }
        if (projectClass == null) {
            classes.get(classes.size() - 1).addDependency(packageNode);
        }
        if (projectClass != null){
            classes.get(classes.size() - 1).addDependency(projectClass);
            projectClass.addCall(classes.get(classes.size() - 1));
        }
        return projectClass;
    }

    //endregion

    //region Builder methods

    /**
     * Add a modifier to the list of modifiers
     * @param modifier modifier to add
     */
    public void addModifier(ModifierType modifier){
        classModifiers.add(modifier);
    }

    @Override
    public ProjectTreeNode getProjectTree() {
        return project;
    }

    //endregion

}
