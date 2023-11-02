package dev.danigpaz;

import dev.danigpaz.DTO.RestDirTreeDTO;
import dev.danigpaz.dirTree.AbstractDirTreeNode;
import dev.danigpaz.export.ExporterTypes;
import dev.danigpaz.mapper.RestDirTreeDTOToDirTree;
import dev.danigpaz.parser.ProgrammingLanguages;
import dev.danigpaz.treeManagerExceptions.UnknownLanguageException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/guml")
public class GumlController {

    @PostMapping("/generate")
    public String generateUML(@RequestBody RestDirTreeDTO dirTree) {
        AbstractDirTreeNode root = RestDirTreeDTOToDirTree.toAbstractDirTree(dirTree);
        TreeManager treeManager = new TreeManager(root, ProgrammingLanguages.getLanguage(dirTree.getLanguage()));
        String toReturn;
        try {
            toReturn = treeManager.generateUML(ExporterTypes.PLANTUML, dirTree.isVerbose());
        } catch (Exception e) {
            toReturn = e.getMessage();
        }
        return toReturn;
    }

    @PostMapping("/generate/image")
    public String generateUMLImage(@RequestBody RestDirTreeDTO dirTree) {
        AbstractDirTreeNode root = RestDirTreeDTOToDirTree.toAbstractDirTree(dirTree);
        TreeManager treeManager = new TreeManager(root, ProgrammingLanguages.getLanguage(dirTree.getLanguage()));
        String toReturn;
        try {
            toReturn = treeManager.generateUMLImage(ExporterTypes.PLANTUML, dirTree.isVerbose());
        } catch (Exception e) {
            toReturn = e.getMessage();
        }
        return toReturn;
    }
}
