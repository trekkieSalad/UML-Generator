package dev.danigpaz.mapper;

import dev.danigpaz.DTO.RestDirTreeDTO;
import dev.danigpaz.DTO.RestDirTreeNodeDTO;
import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.concreteDirTreeNodes.Directory;

public class RestDirTreeDTOToDirTree {

    public static AbstractDirTreeNode toAbstractDirTree(RestDirTreeDTO rdtNodeDTO) {
        Directory adtNode = new Directory("project", "");
        for (RestDirTreeNodeDTO child : rdtNodeDTO.getProject()) {
            adtNode.addChildren(RestDirTreeNodeToDirTreeNode.toAbstractDirTreeNode(child, ""));
        }
        return adtNode;
    }
}
