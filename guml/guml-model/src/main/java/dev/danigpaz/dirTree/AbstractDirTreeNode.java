package dev.danigpaz.dirTree;

import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Represents a node in the directory tree. It can be a file or a directory
 */
public abstract class AbstractDirTreeNode implements Iterable<AbstractDirTreeNode>{
    /**
     * Name of the node
     */
    protected String name;

    /**
     * Path of the node
     */
    protected String path;

    /**
     * Returns an iterator over the elements of the node
     * @return Iterator over the elements of the node
     */
    @Override
    public Iterator<AbstractDirTreeNode> iterator() {
        return new IteratorAbstractDirTreeNode(this);
    }

    /**
     * Iterator over the elements of the node of a directory tree
     */
    class IteratorAbstractDirTreeNode implements Iterator<AbstractDirTreeNode> {

        private Stack<AbstractDirTreeNode> stack;

        public IteratorAbstractDirTreeNode(AbstractDirTreeNode node) {
            stack = new Stack<>();
            stack.push(node);
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public AbstractDirTreeNode next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements to iterate.");
            }

            // We get the current node
            AbstractDirTreeNode currentNode = stack.pop();

            // If the current node is not a leaf, we add its children to the stack
            if (!currentNode.isLeaf()) {
                for (AbstractDirTreeNode child : currentNode.getChildrens()) {
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

    /**
     * Creates a new node with the given name and path
     * @param name Name of the node
     * @param path Path of the node
     */
    public AbstractDirTreeNode(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * Getter of the name of the node
     * @return Name of the node
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of the path of the node
     * @return Path of the node
     */
    public String getPath() {
        return path;
    }

    /**
     * Returns the children nodes of the node
     * @return List of children nodes
     */
    public List<AbstractDirTreeNode> getChildrens(){
        return null;
    }

    /**
     * Checks if the node is a leaf (it has no children nodes)
     * @return True if the node is a leaf, false otherwise
     */
    public abstract boolean isLeaf();

    /**
     * Getter of the type of the node (file or directory)
     * @return Type of the node
     */
    public abstract DirTreeType getType();

}
