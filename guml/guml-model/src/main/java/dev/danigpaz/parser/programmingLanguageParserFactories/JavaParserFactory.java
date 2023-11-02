package dev.danigpaz.parser.programmingLanguageParserFactories;

import dev.danigpaz.parser.ParserFactory;
import dev.danigpaz.parser.ProgrammingLanguageLexer;
import dev.danigpaz.parser.ProgrammingLanguageParser;
import dev.danigpaz.parser.ProjectTreeAdapter;
import dev.danigpaz.parser.programmingLanguageParsers.java.JavaLexer;
import dev.danigpaz.parser.programmingLanguageParsers.java.JavaParser;
import dev.danigpaz.parser.programmingLanguageParsers.java.JavaProjectTreeAdapter;
import dev.danigpaz.treeManagerExceptions.CreationParserException;

/**
 * This class is used to create a parser for Java.
 */
public class JavaParserFactory extends ParserFactory {
    @Override
    public ProgrammingLanguageParser createParser(String file, ProjectTreeAdapter ptAdapter) throws CreationParserException {

        JavaProjectTreeAdapter javaPTAdapter = (JavaProjectTreeAdapter) ptAdapter;
        if (javaPTAdapter == null) {
            // If the adapter is null, create a new one
            javaPTAdapter = (JavaProjectTreeAdapter) createAdapter();
        }

        // Create the lexer and set the adapter to it
        JavaLexer jl = new JavaLexer(new java.io.StringReader(file));
        jl.setTreeBuilder(javaPTAdapter);

        // Create the parser and set the adapter and lexer to it
        JavaParser jp;
        try {
            jp = new JavaParser(jl);
        } catch (Exception e) {
            throw new CreationParserException("Error creating parser for Java");
        }
        jp.setTreeBuilder(javaPTAdapter);
        jp.setLexer(jl);
        return jp;
    }

    @Override
    public ProgrammingLanguageLexer createLexer(String file) throws CreationParserException {
        // Create the lexer for Java and throw an CreationParserException if it fails
        try {
            return new JavaLexer(new java.io.StringReader(file));
        } catch (Exception e) {
            throw new CreationParserException("Error creating lexer for Java");
        }
    }

    @Override
    public ProjectTreeAdapter createAdapter() throws CreationParserException {
        // Create the adapter for Java and throw an CreationParserException if it fails
        try {
            return new JavaProjectTreeAdapter();
        } catch (Exception e) {
            throw new CreationParserException("Error creating adapter for Java project structure");
        }
    }

}
