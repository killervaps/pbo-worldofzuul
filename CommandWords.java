public class CommandWords {
    private static final String[] validCommands = {
        "go", "quit", "help", "look", "take", "inventory"
    };

    public CommandWords() {
        // No initialization needed.
    }

    public boolean isCommand(String aString) {
        for (String command : validCommands) {
            if (command.equals(aString)) return true;
        }
        return false;
    }

    public void showAll() {
        System.out.println(String.join(" ", validCommands));
    }
}
