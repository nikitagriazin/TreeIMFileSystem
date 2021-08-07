package testissue.treeimfilesystem.Service;

import java.util.NoSuchElementException;
import java.util.Set;

public interface IFileSystem {
    void CreateFile(String name, String content);

    void CreateDirectory(String name);

    void RemoveFileOrDir(String name);

    String OpenFile(String name);

    String SearchFile(String name, boolean fromRoot);

    void ChangeDirectory(String path) throws NoSuchElementException;

    Set<String> ListDirectory();
}
