package me.ilich.helloworld.app;

public class Door {

    public static Door north() {
        Door door = new Door(Coord.north(), "С");
        return door;
    }

    public static Door west() {
        Door door = new Door(Coord.west(), "З");
        return door;
    }

    public static Door south() {
        Door door = new Door(Coord.south(), "Ю");
        return door;
    }

    public static Door east() {
        Door door = new Door(Coord.east(), "В");
        return door;
    }

    private final Coord coord;
    private final String directionTitle;

    private Door(Coord coord, String directionTitle) {
        this.coord = coord;
        this.directionTitle = directionTitle;
    }

    public Object getDirectionTitle() {
        return directionTitle;
    }

    public Coord getCoord() {
        return coord;
    }
}
