package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Scenable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Room extends Entity implements Scenable {

    private Scenable scenable;
    private Coord coord;
    private String title;

    private Room(UUID id, UUID parentId) {
        super(id, parentId);
    }

    public Coord getCoord() {
        return coord;
    }

    @Override
    public String toString() {
        return coord + " " + scenable.toString();
    }

    public String getTitle() {
        return title;
    }

    @Override
    public void onScene(Controller controller) {
        scenable.onScene(controller);
    }

    public static class Builder {

        private final Room room;

        public Builder(UUID roomId, UUID locationId) {
            this.room = new Room(roomId, locationId);
        }

        public Builder coord(Coord coord) {
            room.coord = coord;
            return this;
        }

        public Builder title(String s) {
            room.title = s;
            return this;
        }

        public Builder description(String s) {
            room.scenable = new Scenable.Impl(s);
            return this;
        }

        public Room build() {
            if (room.scenable == null) {
                room.scenable = new Scenable.Impl("");
            }
            return room;
        }

    }

}
