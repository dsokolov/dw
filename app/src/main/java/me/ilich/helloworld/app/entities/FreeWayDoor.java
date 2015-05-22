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

    @Deprecated
    public enum State {
        OPEN,
        CLOSE,
        LOCKED
    }

    @Deprecated
    public enum Way {
        AB,
        BA,
        BOTH
    }

    @Deprecated
    private Coord coordA;
    @Deprecated
    private Coord coordB;
    @Deprecated
    private State state = State.OPEN;
    @Deprecated
    private Way direction = Way.BOTH;

    public Coord getCoordA() {
        return coordA;
    }

    public Coord getCoordB() {
        return coordB;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Way getDirection() {
        return direction;
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

        @Deprecated
        public Builder coordA(Coord c) {
            door.coordA = c;
            return this;
        }

        @Deprecated
        public Builder coordB(Coord c) {
            door.coordB = c;
            return this;
        }

        @Deprecated
        public Builder state(State state) {
            door.state = state;
            return this;
        }

        public FreeWayDoor create() {
            return door;
        }

    }

}
