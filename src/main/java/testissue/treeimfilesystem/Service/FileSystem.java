package testissue.treeimfilesystem.Service;

import testissue.treeimfilesystem.Model.File;

import java.lang.ref.WeakReference;
import java.util.*;

public class FileSystem implements IFileSystem {
    private File root;
    private WeakReference<File> selector;

    public FileSystem() {
        root = new File("root", true);
        selector = new WeakReference<File>(root);
    }

    public void CreateFile(String path, String content) {
        String name = GetNameByPath(path);
        File newFile = new File(name, false);
        newFile.SetContent(content);
        File parent = FindParent(path);
        AddFileToDirectory(newFile, parent);
    }

    public void CreateDirectory(String path) {
        String name = GetNameByPath(path);
        File newFile = new File(name, true);
        File parent = FindParent(path);
        AddFileToDirectory(newFile, parent);
    }

    public void RemoveFileOrDir(String path) {
        File parent = FindParent(path);
        String name = GetNameByPath(path);
        for (String child : parent.GetChildren()) {
            if (child.equals(name)) {
                parent.RemoveChild(child);
                return;
            }
        }
    }

    public String OpenFile(String path) {
        try {
            return SearchFileByPath(path).GetContent();
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Can't open File by path " + path);
        }
    }

    public String SearchFile(String name, boolean fromRoot) {
        File file = fromRoot ? this.root : this.selector.get();
        assert file != null : "Selector is null";
        return this.SearchFileRecursive(name, file);
    }

    public void ChangeDirectory(String path) throws NoSuchElementException {
        File result = SearchFileByPath(path);
        if (!result.IsDir()) {
            throw new IllegalArgumentException("Path is not Directory");
        }
        this.selector = new WeakReference<File>(result);
    }

    public Set<String> ListDirectory() {
        File file = this.selector.get();
        return file != null ? file.GetChildren() : new HashSet<String>();
    }

    private String SearchFileRecursive(String name, File selected) {
        Set<String> children = selected.GetChildren();
        for (String child : children) {
            if (child.equals(name)) {
                return selected.GetName();
            }
            File childFile = selected.GetChild(child);
            if (childFile.IsDir()) {
                String path = SearchFileRecursive(name, childFile);
                if (path != null) {
                    return selected.GetName() + "/" + path;
                }
            }
        }
        return null;
    }

    private File SearchFileByPath(String path) throws NoSuchElementException {
        File file;
        if (path.substring(0, 1).equals("/")) {
            file = this.root;
        } else {
            file = this.selector.get();
        }

        if (file == null) {
            throw new NoSuchElementException("No element with such path existed");
        }

        Queue<String> pathSplit = new LinkedList<String>(Arrays.asList(path.split("/")));
        for (String name : pathSplit) {
            boolean fileFound = false;

            for (String child : file.GetChildren()) {
                if (child.equals(name)) {
                    file = file.GetChild(child);
                    fileFound = true;
                    break;
                }
            }

            if (!fileFound)  throw new NoSuchElementException("No element with such path existed");
        }
        return file;
    }

    private File FindParent(String path) {
        String ancestry = GetAncestry(path);
        if (ancestry.equals("")) {
            return selector.get();
        } else {
            return SearchFileByPath(path);
        }
    }

    private String GetNameByPath(String path) {
        String[] split = path.split("/");
        return split[split.length - 1];
    }

    private void AddFileToDirectory(File toAdd, File dir) {
        assert dir != null : "Selector is null";
        assert dir.IsDir() : "Selected path is not a directory";
        dir.AddChild(toAdd);
    }

    private static String GetAncestry(String path) {
        int ancestryPoint = path.lastIndexOf('/');
        return ancestryPoint > 0 ? path.substring(0, ancestryPoint) : "";
    }
}
