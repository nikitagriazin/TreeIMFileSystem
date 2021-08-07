package testissue.treeimfilesystem.Model;

import java.util.HashMap;
import java.util.Set;

public class File {
    private String name;
    private boolean isDir;
    private HashMap<String, File> children = new HashMap<String, File>();
    private String content;

    public File(String name, boolean isDir) {
        this.name = name;
        this.isDir = isDir;
    }

    public void SetName(String name) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }

    public boolean IsDir() {
        return isDir;
    }

    public void SetContent(String content) {
        this.content = content;
    }

    public String GetContent() {
        return this.content;
    }

    public Set<String> GetChildren() {
        return children.keySet();
    }

    public void AddChild(File child) {
        String path = child.name;
        children.put(path, child);
    }

    public void RemoveChild(String name) {
        children.remove(name);
    }

    public File GetChild(String name) {
        for (String fileName : children.keySet()) {
            if (name.equals(fileName)) {
                return children.get(fileName);
            }
        }
        return null;
    }
}
