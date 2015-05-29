package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.primitives.Coordinable;
import me.ilich.helloworld.app.entities.primitives.Enterable;

import java.util.UUID;

public class FreeWayDoor extends Entity implements Coordinable, Enterable {

    private Coordinable coordinable;
    private Enterable enterable;

    private FreeWayDoor(UUID id, UUID parentId) {
        super(id, parentId);
        enterable = new Enterable.Impl();
    }

    @Override
    public Coord getCoord() {
        return coordinable.getCoord();
    }

    @Override
    public void onEnter(Controller controller) {
        enterable.onEnter(controller);
    }

    public static class Builder {

        private final FreeWayDoor door;

        public Builder(UUID id, UUID parentId) {
            door = new FreeWayDoor(id, parentId);
        }

        public Builder coord(Coord c) {
            door.coordinable = new Coordinable.Impl(c);
            return this;
        }

        public FreeWayDoor create() {
            return door;
        }

    }

}
