package dev.danigpaz.dirTree.concreteDirTreeNodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.DirTreeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.danigpaz.dirTree.concreteDirTreeNodes.Directory;
import dev.danigpaz.dirTree.concreteDirTreeNodes.File;

public class DirectoryTest {

    @Test
    public void testGetType() {
        Directory directory = new Directory("folder", "/path/to/folder");
        Assertions.assertEquals(DirTreeType.DIRECTORY, directory.getType());
    }

    @Test
    public void testIsLeaf() {
        Directory directory = new Directory("folder", "/path/to/folder");
        Directory directoryLeaf = new Directory("folder", "/path/to/folder");
        directory.addChildren(new File("file.txt", "/path/to/folder/file.txt"));
        assertFalse(directory.isLeaf());
        assertTrue(directoryLeaf.isLeaf());
    }

    @Test
    public void testGetChildrens() {
        Directory directory = new Directory("folder", "/path/to/folder");
        File file = new File("file.txt", "/path/to/folder/file.txt");
        directory.addChildren(file);

        assertEquals(1, directory.getChildrens().size());
        assertEquals(file, directory.getChildrens().get(0));
    }

    @Test
    public void testSetChildrens() {
        Directory directory = new Directory("folder", "/path/to/folder");
        File file = new File("file.txt", "/path/to/folder/file.txt");
        List<AbstractDirTreeNode> childrens = new ArrayList<>();
        childrens.add(file);
        directory.setChildrens(childrens);

        assertEquals(1, directory.getChildrens().size());
        assertEquals(childrens, directory.getChildrens());
        assertEquals(file, directory.getChildrens().get(0));

        childrens.remove(0);

        assertEquals(1, directory.getChildrens().size());
        assertNotEquals(childrens, directory.getChildrens());
    }

    @Test
    public void testAddChildren() {
        Directory directory = new Directory("folder", "/path/to/folder");
        File file = new File("file.txt", "/path/to/folder/file.txt");

        assertEquals(0, directory.getChildrens().size());

        directory.addChildren(file);

        assertEquals(1, directory.getChildrens().size());
        assertEquals(file, directory.getChildrens().get(0));
    }

    @Test
    public void testRemoveChildren(){
        Directory directory = new Directory("folder", "/path/to/folder");
        File file = new File("file.txt", "/path/to/folder/file.txt");

        assertEquals(0, directory.getChildrens().size());

        directory.addChildren(file);

        assertEquals(1, directory.getChildrens().size());
        assertEquals(file, directory.getChildrens().get(0));

        directory.removeChildren(file);

        assertEquals(0, directory.getChildrens().size());
    }

}
