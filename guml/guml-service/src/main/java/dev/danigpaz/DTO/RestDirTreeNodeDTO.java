package dev.danigpaz.DTO;

import java.util.List;

public class RestDirTreeNodeDTO {
    private String name;
    private List<RestDirTreeNodeDTO> children;
    private String content;
    private String type;

    public RestDirTreeNodeDTO() {
        // Constructor sin argumentos
    }

    public RestDirTreeNodeDTO(String name, List<RestDirTreeNodeDTO> children, String content) {
        this.name = name;
        this.children = children;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RestDirTreeNodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RestDirTreeNodeDTO> children) {
        this.children = children;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int depth) {
        StringBuilder sb = new StringBuilder();
        String indent = "  ".repeat(depth);

        sb.append(indent).append(type).append(": ").append(name);
        if (content != null) {
            sb.append(" (Content: ").append(content).append(")");
        }
        sb.append("\n");

        if (children != null) {
            for (RestDirTreeNodeDTO child : children) {
                sb.append(child.toString(depth + 1));
            }
        }

        return sb.toString();
    }
}
