package dev.danigpaz.dirTree.concreteDirTreeNodes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.DirTreeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.danigpaz.dirTree.concreteDirTreeNodes.File;

public class FileTest {
    @Test
    public void testGetType() {
        AbstractDirTreeNode file = new File("example.txt", "/path/to/example.txt");
        File file2 = new File("example.txt", "/path/to/example.txt");
        Assertions.assertEquals(DirTreeType.FILE, file.getType());
        assertEquals(DirTreeType.FILE, file2.getType());
    }

    @Test
    public void testGetContent() {
        String content = "This is some content.";
        File file = new File("example.txt", "/path/to/example.txt", content);
        assertEquals(content, file.getContent());
    }

    @Test
    public void testSetContent() {
        String content = "This is some content.";
        File file = new File("example.txt", "/path/to/example.txt");
        File file2 = new File("example.txt", "/path/to/example.txt", content);
        file.setContent(content);
        assertEquals(content, file.getContent());
        assertEquals(content, file2.getContent());
    }
}
