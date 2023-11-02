package dev.danigpaz.projectTree;

import dev.danigpaz.export.UmlExportable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public abstract class ProjectTreeNode implements UmlExportable, Iterable<ProjectTreeNode> {
    protected String name;
    protected ProjectTreeNode parent = null;
    protected Set<ProjectTreeNode> childrens = new HashSet<>();

    @Override
    public Iterator<ProjectTreeNode> iterator() {
        return new IteratorProjectTreeNode(this);
    }

    class IteratorProjectTreeNode implements Iterator<ProjectTreeNode> {

        private Stack<ProjectTreeNode> stack;

        public IteratorProjectTreeNode(ProjectTreeNode node) {
            stack = new Stack<>();
            stack.push(node);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public ProjectTreeNode next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements to iterate.");
            }

            ProjectTreeNode currentNode = stack.pop();

            if (!currentNode.isLeaf()) {
                for (ProjectTreeNode child : currentNode.getChildrens()) {
                    stack.push(child);
                }
            }

            return currentNode;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }

    public ProjectTreeNode() {
        this.name = "";
    }

    public ProjectTreeNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getFullName(){
        if (parent == null) return name;
        return parent.getFullName() + "." + name;
    }

    public ProjectTreeNode getParent() {
        return parent;
    }

    public void setParent(ProjectTreeNode parent) {
        this.parent = parent;
    }

    public Set<ProjectTreeNode> getChildrens() {
        return childrens;
    }

    public void setChildrens(Set<ProjectTreeNode> childrens) {
        this.childrens = childrens;
    }

    public void addChild(ProjectTreeNode child){
        this.childrens.add(child);
        child.setParent(this);
    }
    public boolean isLeaf(){
        return childrens.isEmpty();
    }

    public boolean isRoot(){
        return parent == null;
    }

    public ProjectTreeNode searchChild(String name){
        for (ProjectTreeNode child : childrens) {
            if (child.getName().equals(name)) return child;
        }
        return null;
    }

    public void reduce(){
        reduceDependencies();
        reduceNode();
    }

    public abstract ProjectTreeNodeType getType();
    public abstract void expandDependencies();
    public abstract void reduceDependencies();
    public abstract void reduceNode();
    public abstract boolean isEmpty();

}
