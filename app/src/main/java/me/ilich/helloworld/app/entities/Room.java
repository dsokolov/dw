package me.ilich.helloworld.app.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Room {

    private Coord coord;
    private String title;
    private String description;
    private final List<Door> doors = new ArrayList<>();
    private final List<Item> items = new ArrayList<>();

    public Room(Coord coord, String title, String description, Door[] doors, Item[] items) {
        this.coord = coord;
        this.title = title;
        this.description = description;
        this.doors.addAll(Arrays.asList(doors));
        this.items.addAll(Arrays.asList(items));
    }

    private Room() {

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

    public static class Builder {

        private Room room = new Room();

        public Builder coord(Coord coord) {
            room.coord = coord;
            return this;
        }

        public Builder title(String s) {
            room.title = s;
            return this;
        }

        public Builder description(String s) {
            room.description = s;
            return this;
        }

        public Builder door(Door door) {
            room.doors.add(door);
            return this;
        }

        public Builder item(Item item) {
            room.items.add(item);
            return this;
        }

        public Room build() {
            return room;
        }

    }

}
