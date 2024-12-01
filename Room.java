import java.util.ArrayList;

public class Room {
    private String description;
    private Room northExit, southExit, eastExit, westExit;
    private ArrayList<String> items;

    public Room(String description) {
        this.description = description;
        this.items = new ArrayList<>();
    }

    public void setExits(Room north, Room east, Room south, Room west) {
        if (north != null) northExit = north;
        if (east != null) eastExit = east;
        if (south != null) southExit = south;
        if (west != null) westExit = west;
    }

    public void addItem(String item) {
        items.add(item);
    }

    public String takeItem(String item) {
        if (items.contains(item)) {
            items.remove(item);
            return item;
        }
        return null;
    }

    public String getItems() {
        if (items.isEmpty()) return "No items here.";
        return String.join(", ", items);
    }

    public String getDescription() {
        return description + "\nItems in this room: " + getItems();
    }

    public Room getExit(String direction) {
        return switch (direction) {
            case "north" -> northExit;
            case "east" -> eastExit;
            case "south" -> southExit;
            case "west" -> westExit;
            default -> null;
        };
    }
}
