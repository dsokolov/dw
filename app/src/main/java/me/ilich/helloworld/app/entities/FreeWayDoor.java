package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Coordinable;
import me.ilich.helloworld.app.entities.primitives.Enterable;

public class FreeWayDoor extends Entity implements Coordinable, Enterable {

    private Coordinable coordinable;
    private Enterable enterable;

    private FreeWayDoor(Entity parent) {
        super(parent);
    }

    @Override
    public Coord getCoord() {
        return coordinable.getCoord();
    }

    @Override
    public String getTitle() {
        return enterable.getTitle();
    }

    @Override
    public boolean isVisible() {
        return enterable.isVisible();
    }

    @Override
    public void onEnter(Controller controller) {
        enterable.onEnter(controller);
    }

    @Override
    public Room getRoom(Controller controller) {
        return enterable.getRoom(controller);
    }

    public static class Builder {

        private final FreeWayDoor door;
        private String title = "";
        private boolean visible = true;
        private RoomSource roomSource;

        public Builder(Entity parent) {
            door = new FreeWayDoor(parent);
        }

        public Builder coord(Coord c) {
            door.coordinable = new Coordinable.Impl(c);
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder visible(boolean b) {
            this.visible = b;
            return this;
        }

        public Builder roomSource(RoomSource roomSource) {
            this.roomSource = roomSource;
            return this;
        }

        public FreeWayDoor create() {
            if (roomSource == null) {
                roomSource = new RelativeCoordRoomSource(door.coordinable);
            }
            door.enterable = new Enterable.Impl(title, visible, roomSource);
            return door;
        }

    }

}
