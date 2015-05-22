package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Coordinable;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.entities.primitives.Titlelable;

import java.util.UUID;

public class Room extends Entity implements Titlelable, Scenable, Coordinable {

    private Titlelable titlelable;
    private Scenable scenable;
    private Coordinable coordinable;

    private Room(UUID id, UUID parentId) {
        super(id, parentId);
    }

    @Override
    public String toString() {
        return coordinable.getCoord() + " " + scenable.toString();
    }

    @Override
    public void onScene(Controller controller) {
        scenable.onScene(controller);
    }

    @Override
    public Coord getCoord() {
        return coordinable.getCoord();
    }

    @Override
    public String getTitle(int index) {
        return titlelable.getTitle(index);
    }

    @Override
    public boolean isTitleSuitable(String s) {
        return titlelable.isTitleSuitable(s);
    }

    public static class Builder {

        private final Room room;

        public Builder(UUID roomId, UUID locationId) {
            this.room = new Room(roomId, locationId);
        }

        public Builder coord(Coord coord) {
            room.coordinable = new Coordinable.Impl(coord);
            return this;
        }

        public Builder title(String s) {
            room.titlelable = new Titlelable.Impl(s);
            return this;
        }

        public Builder description(String s) {
            room.scenable = new Scenable.Impl(s);
            return this;
        }

        public Room build() {
            if (room.titlelable == null) {
                room.titlelable = new Titlelable.Impl("");
            }
            if (room.scenable == null) {
                room.scenable = new Scenable.Impl("");
            }
            if (room.coordinable == null) {
                room.coordinable = new Coordinable.Impl(null);
            }
            return room;
        }

    }

}
