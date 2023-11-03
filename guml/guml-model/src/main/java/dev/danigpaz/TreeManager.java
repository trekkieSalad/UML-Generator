package dev.danigpaz;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.dirTree.DirTreeType;
import dev.danigpaz.dirTree.concreteDirTreeNodes.File;
import dev.danigpaz.export.ExporterTypes;
import dev.danigpaz.export.UmlExporter;
import dev.danigpaz.parser.ParserFactory;
import dev.danigpaz.parser.ProgrammingLanguageParser;
import dev.danigpaz.parser.ProgrammingLanguages;
import dev.danigpaz.parser.ProjectTreeAdapter;
import dev.danigpaz.treeManagerExceptions.CreationExporterException;
import dev.danigpaz.treeManagerExceptions.CreationParserException;
import dev.danigpaz.treeManagerExceptions.ParserException;
import dev.danigpaz.treeManagerExceptions.UnknownLanguageException;

public class TreeManager {

    private AbstractDirTreeNode directoryTree;
    private ProjectTreeAdapter ptBuilder;
    private ProgrammingLanguages language;
    private ExporterTypes exporterType;

    public TreeManager(AbstractDirTreeNode directoryTree){
        this.directoryTree = directoryTree;
        language = null;
        exporterType = ExporterTypes.PLANTUML;
    }

    /**
     * Constructor for TreeManager
     * @param directoryTree Directory tree to be parsed and converted to UML
     * @param language Programming language of the project
     */
    public TreeManager(AbstractDirTreeNode directoryTree, ProgrammingLanguages language){
        this.directoryTree = directoryTree;
        this.language = language;
        exporterType = ExporterTypes.PLANTUML;
    }

    /**
     * Getter for the programming language of the project
     * @return Programming language of the project
     */
    public ProgrammingLanguages getLanguage(){
        return language;
    }

    /**
     * Setter for the programming language of the project
     * @param language Programming language of the project
     */
    public void setLanguage(ProgrammingLanguages language){
        this.language = language;
    }

    /**
     * Getter for de exporter type of the project
     * @return Exporter type of the project
     */
    public ExporterTypes getExporterType(){
        return exporterType;
    }

    /**
     * Setter for de exporter type of the project
     * @param exporterType Exporter type of the project
     */
    public void setExporterType(ExporterTypes exporterType){
        this.exporterType = exporterType;
    }

    /**
     * Detects the programming language of the project
     * @throws UnknownLanguageException If the programming language of the project could not be detected
     */
    public void detectLanguage() throws UnknownLanguageException {
        Map<ProgrammingLanguages, Integer> languages = new HashMap<>();
        for (AbstractDirTreeNode node : directoryTree) {
            if (node.getType() == DirTreeType.FILE && node.getName().contains(".")) {
                String extension = node.getName().substring(node.getName().lastIndexOf('.'));
                ProgrammingLanguages language = ProgrammingLanguages.getLanguage(extension);
                if (language != null) {
                    if (languages.containsKey(language))
                        languages.put(language, languages.get(language) + 1);
                    else languages.put(language, 1);
                }
            }
        }
        if (!languages.isEmpty()) {
            this.language = languages.entrySet().stream()
                    .max(Comparator.comparing(Map.Entry::getValue))
                    .map(Map.Entry::getKey)
                    .orElse(null);
        }
        if (language == null) throw new UnknownLanguageException("Could not detect language of the project");
    }

    /**
     * Parses the directory tree and creates the project tree
     * @throws UnknownLanguageException If the programming language of the project could not be detected
     * @throws ParserException If there is an error parsing a file
     * @throws CreationParserException If there is an error creating the parser
     */
    private void parseDirectoryTree() throws UnknownLanguageException, ParserException, CreationParserException {
        if (language == null) detectLanguage();

        for (AbstractDirTreeNode node : directoryTree) {
            if (node.getType() == DirTreeType.FILE && node.getName().contains(".")) {
                File file = (File) node;
                String extension = node.getName().substring(node.getName().lastIndexOf('.'));
                System.out.println("Parsing file " + file.getName() + " with extension " + extension);

                if (language.matchExtension(extension)) {
                    ProgrammingLanguageParser parser = ParserFactory.createParser(language, file.getContent(), ptBuilder);
                    try {
                        parser.parse();
                    } catch (Exception e) {
                        throw new ParserException("Error parsing file " + file.getName() + ": " + e.getMessage());
                    }
                    ptBuilder = parser.getTreeBuilder();
                }
            }
        }
    }

    /**
     * Parses the project tree and creates the UML
     * @param exporterType Type of the exporter
     * @param verbose If the UML should be verbose
     * @return UML representation of the project
     * @throws CreationExporterException If there is an error creating the exporter
     */
    private String parseProjectTree(ExporterTypes exporterType, boolean verbose) throws CreationExporterException {

        if (exporterType == null) throw new CreationExporterException("Exporter not implemented yet");
        UmlExporter exporter = exporterType.getExporter();
        return exporter.export(ptBuilder.getProjectTree(), verbose);

    }

    /**
     * Generates the UML of the project
     * @param exporterType Type of the exporter
     * @param verbose If the UML should be verbose
     * @return UML representation of the project
     * @throws Exception
     */
    public String generateUML(ExporterTypes exporterType, boolean verbose) throws Exception{

        parseDirectoryTree();
        ptBuilder.getProjectTree().expandDependencies();
        ptBuilder.getProjectTree().reduce();
        return parseProjectTree(exporterType, verbose);
    }

    public String generateUMLImage(ExporterTypes exporterType, boolean verbose) throws Exception{
        parseDirectoryTree();
        ptBuilder.getProjectTree().expandDependencies();
        ptBuilder.getProjectTree().reduce();
        return exporterType.getExporter().exportImage(ptBuilder.getProjectTree(), verbose);
    }

}
