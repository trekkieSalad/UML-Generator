package dev.danigpaz.dirTree.concreteDirTreeNodes;

import java.util.List;

import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.DirTreeType;

/**
 * Represents a directory in the directory tree
 */
public class Directory extends AbstractDirTreeNode{

    /**
     * Children nodes of the directory (files and subDirectories)
     */
    private List<AbstractDirTreeNode> childrens;

    /**
     * Creates a new directory with the given name and path
     * @param name Name of the directory
     * @param path Path of the directory
     */
    public Directory(String name, String path) {
        super(name, path);
        childrens = new java.util.ArrayList<>();
    }

    /**
     * Creates a new directory with the given name, path and children nodes
     * @param name Name of the directory
     * @param path Path of the directory
     * @param childrens List of children nodes of the directory
     */
    public Directory(String name, String path, List<AbstractDirTreeNode> childrens) {
        super(name, path);
        this.childrens = new java.util.ArrayList<>(childrens);
    }

    @Override
    public DirTreeType getType() {

        return DirTreeType.DIRECTORY;
    }

    @Override
    public boolean isLeaf() {
        return childrens.isEmpty();
    }

    public List<AbstractDirTreeNode> getChildrens() {
        return childrens;
    }

    /**
     * Sets the children nodes of the directory
     * @param childrens List of children nodes
     */
    public void setChildrens(List<AbstractDirTreeNode> childrens) {
        this.childrens = new java.util.ArrayList<>(childrens);
    }

    /**
     * Adds the given node to the children nodes of the directory
     * @param child Node to be added
     */
    public void addChildren(AbstractDirTreeNode child){
        childrens.add(child);
    }

    /**
     * Removes the given node from the children nodes of the directory
     * @param child Node to be removed
     */
    public void removeChildren(AbstractDirTreeNode child){
        childrens.remove(child);
    }

}
