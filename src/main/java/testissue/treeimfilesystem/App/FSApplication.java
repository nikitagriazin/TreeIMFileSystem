package testissue.treeimfilesystem.App;

import testissue.treeimfilesystem.Model.Command;
import testissue.treeimfilesystem.Service.FileSystem;
import testissue.treeimfilesystem.Service.IFileSystem;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FSApplication {
    private IFileSystem fileSystem;
    private Scanner scanner;

    public FSApplication() {
        this.fileSystem = new FileSystem();
        scanner = new Scanner(System.in);
    }

    public void Run() {
        Command lastCmd = null;
        PrintHelp();
        while (lastCmd != Command.EXIT) {
            String input = scanner.nextLine();
            List<String> arguments = Arrays.asList(input.split(" "));
            lastCmd = Command.valueOfCommand(arguments.get(0).toLowerCase());

            if (lastCmd != null && lastCmd != Command.EXIT) {
                ProcessCommand(lastCmd, arguments);
            }

            if (lastCmd == null) {
                System.out.println("Bad command" + arguments.get(0));
            }
        }
    }

    private void ProcessCommand(Command cmd, List<String> args) {
        if (!CheckForArguments(args, cmd.GetNumOfArgs())) return;
        switch (cmd) {
            case CREATE_FILE:
                try {
                    this.fileSystem.CreateFile(args.get(1), args.get(2));
                    System.out.println("File with name " + args.get(1) + " created");
                } catch (Exception ex) {
                    System.out.println("File not created coz error was occurred");
                }
                break;
            case CREATE_DIR:
                try {
                    this.fileSystem.CreateDirectory(args.get(1));
                    System.out.println("Directory with name " + args.get(1) + " created");
                } catch (Exception ex) {
                    System.out.println("Directory not created coz error was occurred");
                }
                break;
            case REMOVE:
                try {
                    this.fileSystem.RemoveFileOrDir(args.get(1));
                    System.out.println("File with name " + args.get(1) + " removed");
                } catch (Exception ex) {
                    System.out.println("File not removed coz error was occurred");
                }
                break;
            case OPEN:
                try {
                    String fileContent = this.fileSystem.OpenFile(args.get(1));
                    System.out.println();
                    System.out.println("File " + args.get(0) + " Content: ");
                    System.out.println();
                    System.out.println(fileContent);
                    System.out.println();
                } catch (Exception ex) {
                    System.out.println("File not opened coz error was occurred");
                }
                break;
            case SEARCH:
                try {
                    String result = this.fileSystem.SearchFile(args.get(1), true);
                    System.out.println("File found in: " + result);
                } catch (Exception ex) {
                    System.out.println("File not found coz error was occurred");
                }
                break;
            case CHANGE_DIRECTORY:
                try {
                    this.fileSystem.ChangeDirectory(args.get(1));
                } catch (Exception ex) {
                    System.out.println("Directory not changed coz error was occurred");
                }
                break;
            case LIST_DIRECTORY:
                try {
                    Set<String> result = this.fileSystem.ListDirectory();
                    for (String file: result) {
                        System.out.println(file);
                    }
                } catch (Exception ex) {
                    System.out.println("Directory list not shown coz error was occurred");
                }
                break;
        }
    }

    private void PrintHelp() {
        System.out.println("Help to use Tree In-Memory File System");
        System.out.println("-------------------------------------------------------------------");
        System.out.println("mkfile <name> <content> - for create file with content");
        System.out.println("mkdir <name> - create directory");
        System.out.println("rm <name> - remove directory");
        System.out.println("opn <name> - to open file in directory");
        System.out.println("find <name> - to find some file");
        System.out.println("cd <path> - to change selector path");
        System.out.println("ls - to list all files in directory");
        System.out.println("exit - to exit from app");
        System.out.println("-------------------------------------------------------------------");
        System.out.println();
    }

    private boolean CheckForArguments(List<String> args, int numOfArgs) {
        if (args.size() - 1 < numOfArgs) {
            System.out.println("Not enough arguments");
            return false;
        }
        return true;
    }
}
