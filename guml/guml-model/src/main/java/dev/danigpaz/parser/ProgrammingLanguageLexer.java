package dev.danigpaz.parser;

/**
 * This interface is used to add an instance of the adapter to the lexer.
 */
public interface ProgrammingLanguageLexer {
    /**
     * Getter for the adapter instance of the concrete language that uses the lexer.
     * @return Instance of the adapter for the concrete language.
     */
    ProjectTreeAdapter getTreeBuilder();

    /**
     * Setter for the adapter.
     * @param ptBuilder Instance of the adapter for the concrete language.
     */
    void setTreeBuilder(ProjectTreeAdapter ptBuilder);
}
