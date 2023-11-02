package dev.danigpaz.parser;

import dev.danigpaz.parser.programmingLanguageParserFactories.JavaParserFactory;
import dev.danigpaz.treeManagerExceptions.CreationParserException;

public abstract class ParserFactory {

    /**
     * Creates a parser for the given programming language.
     * @param language the programming language.
     * @param file the file to parse.
     * @param ptAdapter the project tree adapter of the specific programming language.
     * @return A LALR parser for the given programming language.
     * @throws CreationParserException if the parser cannot be created.
     */
    public static ProgrammingLanguageParser createParser(ProgrammingLanguages language, String file, ProjectTreeAdapter ptAdapter) throws CreationParserException {
        switch (language) {
            case JAVA:
                return new JavaParserFactory().createParser(file, ptAdapter);
            default:
                return null;
        }
    }

    /**
     * Creates a parser for the given programming language.
     * @param file the file to parse.
     * @param ptAdapter the project tree adapter of the specific programming language.
     * @return A parser for the given programming language.
     * @throws CreationParserException if the parser cannot be created.
     */
    public abstract ProgrammingLanguageParser createParser(String file, ProjectTreeAdapter ptAdapter) throws CreationParserException;

    /**
     * Creates a lexer for the given programming language.
     * @param file the file to parse.
     * @return A lexer for the given programming language.
     * @throws CreationParserException if the lexer cannot be created.
     */
    public abstract ProgrammingLanguageLexer createLexer(String file) throws CreationParserException;

    /**
     * Creates a project tree adapter for the given programming language.
     * @return A project tree adapter for the given programming language.
     * @throws CreationParserException if the project tree adapter cannot be created.
     */
    public abstract ProjectTreeAdapter createAdapter() throws CreationParserException;
}
