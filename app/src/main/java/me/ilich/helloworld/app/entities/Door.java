package me.ilich.helloworld.app.entities;

public class Door {

    public enum State {
        OPEN,
        CLOSE,
        LOCKED
    }

    public enum Way {
        AB,
        BA,
        BOTH
    }

    private Coord coordA;
    private Coord coordB;
    private State state = State.OPEN;
    private Way direction = Way.BOTH;

    private Door() {

    }

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

        private Door door = new Door();

        public Builder coordA(Coord c) {
            door.coordA = c;
            return this;
        }

        public Builder coordB(Coord c) {
            door.coordB = c;
            return this;
        }

        public Builder state(State state) {
            door.state = state;
            return this;
        }

        public Door create() {
            return door;
        }

    }

}
