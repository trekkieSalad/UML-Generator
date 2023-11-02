package dev.danigpaz.parser;

import java_cup.runtime.Symbol;

/**
 * This interface is used to add an instance of the adapter to the parser.
 */
public interface ProgrammingLanguageParser {
    /**
     * This method is used to parse the file.
     * @return the symbol resulting from the parsing.
     * @throws Exception
     * @implNote This method is implemented by the parser generated by CUP. YOU SHOULD NOT IMPLEMENT IT.
     */
    Symbol parse() throws Exception;

    /**
     * Getter of the adapter of the programming language to the parser.
     * @return the adapter of the programming language.
     */
    ProjectTreeAdapter getTreeBuilder();

    /**
     * Setter of the adapter of the programming language to the parser.
     * @param ptBuilder the adapter of the programming language.
     */
    void setTreeBuilder(ProjectTreeAdapter ptBuilder);
}
