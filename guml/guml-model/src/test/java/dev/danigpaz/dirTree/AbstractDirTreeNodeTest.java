package dev.danigpaz.dirTree;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dev.danigpaz.dirTree.concreteDirTreeNodes.Directory;
import dev.danigpaz.dirTree.concreteDirTreeNodes.File;

public class AbstractDirTreeNodeTest {

    @Test
    public void testGetName() {
        String fileName = "file.txt";
        String dirName = "dir";
        AbstractDirTreeNode nodeFile = new File(fileName, "/path/" + dirName + "/" + fileName);
        AbstractDirTreeNode nodeDir = new Directory(dirName, "/path/" + dirName);
        assertEquals(fileName, nodeFile.getName());
        assertEquals(dirName, nodeDir.getName());
    }

    @Test
    public void testGetPath() {
        String dirPath = "/path/to/dir";
        String filePath = "/path/to/file.txt";
        AbstractDirTreeNode nodeDir = new Directory("dir", dirPath);
        AbstractDirTreeNode nodeFile = new File("file.txt", filePath);
        assertEquals(filePath, nodeFile.getPath());
        assertEquals(dirPath, nodeDir.getPath());
    }

    @Test
    public void testGetChildrens() {
        AbstractDirTreeNode child = new File("example.txt", "/path/to/folder/example.txt");
        assertEquals(null, child.getChildrens());
    }
}
