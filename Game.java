import java.util.ArrayList;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private ArrayList<String> inventory;

    public Game() {
        createRooms();
        parser = new Parser();
        inventory = new ArrayList<>();
    }

    private void createRooms() {
        Room outside, theater, pub, lab, office;

        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");

        outside.setExits(null, theater, lab, pub);
        theater.setExits(null, null, null, outside);
        pub.setExits(null, outside, null, null);
        lab.setExits(outside, office, null, null);
        office.setExits(null, null, null, lab);

        outside.addItem("map");
        pub.addItem("beer");
        lab.addItem("computer");

        currentRoom = outside;
    }

    public void play() {
        printWelcome();
        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing. Goodbye.");
    }

    private boolean processCommand(Command command) {
        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        switch (commandWord) {
            case "help" -> printHelp();
            case "go" -> goRoom(command);
            case "look" -> look();
            case "take" -> takeItem(command);
            case "inventory" -> showInventory();
            case "quit" -> {
                return quit(command);
            }
        }
        return false;
    }

    private void printWelcome() {
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        look();
    }

    private void printHelp() {
        System.out.println("You are lost. You are alone.");
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return;
        }

        Room nextRoom = currentRoom.getExit(command.getSecondWord());
        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            currentRoom = nextRoom;
            look();
        }
    }

    private void look() {
        System.out.println(currentRoom.getDescription());
    }

    private void takeItem(Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Take what?");
            return;
        }

        String item = command.getSecondWord();
        String takenItem = currentRoom.takeItem(item);
        if (takenItem == null) {
            System.out.println("That item is not here.");
        } else {
            inventory.add(takenItem);
            System.out.println("You have taken the " + item + ".");
        }
    }

    private void showInventory() {
        if (inventory.isEmpty()) {
            System.out.println("You are not carrying anything.");
        } else {
            System.out.println("You are carrying: " + String.join(", ", inventory));
        }
    }

    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        return true;
    }
}
