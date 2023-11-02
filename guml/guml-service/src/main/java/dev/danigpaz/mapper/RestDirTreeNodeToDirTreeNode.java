package dev.danigpaz.mapper;

import dev.danigpaz.DTO.RestDirTreeNodeDTO;
import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.concreteDirTreeNodes.Directory;
import dev.danigpaz.dirTree.concreteDirTreeNodes.File;

public class RestDirTreeNodeToDirTreeNode {
    public static AbstractDirTreeNode toAbstractDirTreeNode(RestDirTreeNodeDTO rdtNodeDTO, String path) {
        AbstractDirTreeNode adtNode;
        if (rdtNodeDTO.getType().equals("file")) {
            adtNode = createFile(rdtNodeDTO, path);
        } else {
            adtNode = createDirectory(rdtNodeDTO, path);
        }
        return adtNode;
    }

    private static AbstractDirTreeNode createDirectory(RestDirTreeNodeDTO rdtNodeDTO, String path){
        Directory directory = new Directory(rdtNodeDTO.getName(), path);
        for (RestDirTreeNodeDTO child : rdtNodeDTO.getChildren()) {
            directory.addChildren(toAbstractDirTreeNode(child, path + "/" + rdtNodeDTO.getName()));
        }
        return directory;
    }

    private static AbstractDirTreeNode createFile(RestDirTreeNodeDTO rdtNodeDTO, String path){
        File file = new File(rdtNodeDTO.getName(), path);
        file.setContent(rdtNodeDTO.getContent());
        return file;
    }
}
