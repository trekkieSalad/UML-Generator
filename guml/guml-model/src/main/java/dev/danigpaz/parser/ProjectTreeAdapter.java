package dev.danigpaz.parser;

import dev.danigpaz.projectTree.ProjectTreeNode;

/**
 * This interface is used to adapt the project tree to the parser.
 */
public interface ProjectTreeAdapter {
    /**
     * Getter for the project tree.
     * @return the project tree.
     */
    ProjectTreeNode getProjectTree();
}
