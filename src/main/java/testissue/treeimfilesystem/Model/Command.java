package testissue.treeimfilesystem.Model;

public enum Command {
    CREATE_FILE("mkfile", 2),
    CREATE_DIR("mkdir", 1),
    REMOVE("rm", 1),
    OPEN("opn", 1),
    SEARCH("find", 1),
    CHANGE_DIRECTORY("cd", 1),
    LIST_DIRECTORY("ls", 0),
    EXIT("exit", 0);

    private String value;
    private int numOfArgs;

    Command(String value, int numOfArgs) {
        this.value = value;
        this.numOfArgs = numOfArgs;
    }

    public static Command valueOfCommand(String value) {
        for (Command cmd : Command.values()) {
            if (cmd.value.equals(value)) return cmd;
        }
        return null;
    }

    public String GetValue() {
        return value;
    }

    public int GetNumOfArgs() {
        return numOfArgs;
    }
}