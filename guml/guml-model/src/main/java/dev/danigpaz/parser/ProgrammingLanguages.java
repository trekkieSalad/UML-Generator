package dev.danigpaz.parser;

import java.util.Set;

/**
 * Enum that represents the programming languages supported by the parser.
 */
public enum ProgrammingLanguages {
    JAVA(Set.of(".java")),
    PYTHON(Set.of(".py")),
    UNKNOWN(Set.of());

    /**
     * Set of extensions that can have a file of this language.
     */
    private Set<String> extensions;

    /**
     * Constructor of the enum.
     * @param extensions Set of extensions that can have a file of this language.
     */
    ProgrammingLanguages(Set<String> extensions){
        this.extensions = extensions;
    }

    /**
     * Returns the language that matches the extension.
     * @param extension Extension of the file.
     * @return The language that matches the extension.
     */
    public static ProgrammingLanguages getLanguage(String extension){
        for (ProgrammingLanguages language : ProgrammingLanguages.values()) {
            if (language.extensions.contains(extension)) return language;
        }
        return null;
    }

    /**
     * Getter of the set of extensions.
     * @return The set of extensions of the language.
     */
    public Set<String> getExtensions(){
        return extensions;
    }

    /**
     * Checks if the extension is in the set of extensions.
     * @param extension Extension to check.
     * @return True if the extension is in the set of extensions, false otherwise.
     */
    public boolean matchExtension(String extension){
        return extensions.contains(extension);
    }

}
