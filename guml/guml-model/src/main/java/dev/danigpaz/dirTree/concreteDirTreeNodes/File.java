package dev.danigpaz.dirTree.concreteDirTreeNodes;

import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.DirTreeType;

public class File extends AbstractDirTreeNode{

    /**
     * Content of the file
     */
    private String content;

    public File(String name, String path) {
        super(name, path);
        content = "";
    }

    /**
     * Creates a new file node with the given name, path and content
     * @param name Name of the file
     * @param path Path of the file
     * @param content Content of the file
     */
    public File(String name, String path, String content) {
        super(name, path);
        this.content = content;
    }

    @Override
    public DirTreeType getType() {

        return DirTreeType.FILE;
    }

    @Override
    public boolean isLeaf() {
        return true;
    }

    /**
     * Getter of the content of the file
     * @return Content of the file
     */
    public String getContent() {
        return content;
    }

    /**
     * Setter of the content of the file
     * @param content Content of the file
     */
    public void setContent(String content) {
        this.content = content;
    }
}
