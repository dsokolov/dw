package me.ilich.helloworld.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {

    private final Coord coord;
    private final String title;
    private final String description;
    private final List<Door> doors = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public Room(Coord coord, String title, String description, Door[] doors, Item[] items) {
        this.coord = coord;
        this.title = title;
        this.description = description;
        this.doors.addAll(Arrays.asList(doors));
        this.items.addAll(Arrays.asList(items));
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

    public List<Door> getDoors() {
        return doors;
    }

    public String getTitle() {
        return title;
    }

    public List<Item> getItems() {
        return items;
    }

}
