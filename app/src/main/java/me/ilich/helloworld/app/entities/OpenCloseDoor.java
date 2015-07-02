package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Coordinable;
import me.ilich.helloworld.app.entities.primitives.Enterable;
import me.ilich.helloworld.app.entities.primitives.Openable;

public class OpenCloseDoor extends Entity implements Coordinable, Enterable, Openable {

    private Coordinable coordinable;
    private Enterable enterable;
    private Openable openable;
    private OpenableTrigger openableTrigger;

    private OpenCloseDoor(Entity parent) {
        super(parent);
        openable = new Openable.Impl();
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

    @Override
    public void open(Controller controller) {
        openable.open(controller);
        openableTrigger.onOpened(this);
    }

    @Override
    public void close(Controller controller) {
        openable.close(controller);
        openableTrigger.onClosed(this);
    }

    @Override
    public OpenState getOpenState() {
        return openable.getOpenState();
    }

    @Override
    public void setOpenState(OpenState openState) {
        openable.setOpenState(openState);
    }

    public static class Builder {

        private final OpenCloseDoor door;
        private String title = "";
        private boolean visible = true;
        private RoomSource roomSource;
        private OpenableTrigger openableTrigger;

        public Builder(Entity parent) {
            door = new OpenCloseDoor(parent);
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

        public Builder openTrigger(OpenableTrigger openableTrigger) {
            this.openableTrigger = openableTrigger;
            return this;
        }

        public OpenCloseDoor create() {
            if (roomSource == null) {
                roomSource = new RelativeCoordRoomSource(door.coordinable);
            }
            door.enterable = new Enterable.Impl(title, visible, roomSource);
            if (openableTrigger == null) {
                door.openableTrigger = new OpenableTrigger();
                door.openableTrigger.add(door);
            } else {
                door.openableTrigger = openableTrigger;
                openableTrigger.add(door);
            }
            return door;
        }

    }

}
