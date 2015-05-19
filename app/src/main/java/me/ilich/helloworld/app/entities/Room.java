package me.ilich.helloworld.app.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room {

    private UUID id = UUID.randomUUID();
    private Coord coord;
    private String title;
    private String description;
    private final List<Item> items = new ArrayList<>();

    private Room() {

    }

    public UUID getId() {
        return id;
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

        public Builder item(Item item) {
            room.items.add(item);
            return this;
        }

        public Room build() {
            return room;
        }

    }

}
