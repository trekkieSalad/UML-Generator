package dev.danigpaz.DTO;

import java.util.List;

public class RestDirTreeDTO {

    private String language;
    private boolean verbose;
    private List<RestDirTreeNodeDTO> project;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<RestDirTreeNodeDTO> getProject() {
        return project;
    }

    public void setProject(List<RestDirTreeNodeDTO> project) {
        this.project = project;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Language: ").append(language).append("\n");

        if (project != null) {
            for (RestDirTreeNodeDTO node : project) {
                sb.append(node.toString());
            }
        }

        return sb.toString();
    }
}
