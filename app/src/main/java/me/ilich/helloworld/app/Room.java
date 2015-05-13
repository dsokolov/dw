package me.ilich.helloworld.app;

public class Room {

    private final Coord coord;
    private final String title;
    private final String description;
    private final Door[] doors;
    private final Item[] items;

    public Room(Coord coord, String title, String description, Door[] doors, Item[] items) {
        this.coord = coord;
        this.title = title;
        this.description = description;
        this.doors = doors;
        this.items = items;
    }


    public Coord getCoord() {
        return coord;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return coord + " " + description;
    }

    public Door[] getDoors() {
        return doors;
    }

    public String getTitle() {
        return title;
    }

    public Item[] getItems() {
        return items;
    }
}
