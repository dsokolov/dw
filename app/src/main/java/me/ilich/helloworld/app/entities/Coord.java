package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.data.AbsDirection;

/**
 * Created by disokolov on 12.05.15.
 */
public class Coord {

    public static Coord sum(Coord coordA, Coord coordB) {
        Coord coord = Coord.coord(coordA);
        coord.add(coordB);
        return coord;
    }

    public static Coord zero() {
        return new Coord(0, 0, 0);
    }

    public static Coord north() {
        return new Coord(0, 1, 0);
    }

    public static Coord east() {
        return new Coord(1, 0, 0);
    }

    public static Coord south() {
        return new Coord(0, -1, 0);
    }

    public static Coord west() {
        return new Coord(-1, 0, 0);
    }

    public static Coord xy(int x, int y) {
        return new Coord(x, y, 0);
    }

    public static Coord xyz(int x, int y, int z) {
        return new Coord(x, y, z);
    }

    public static Coord coord(Coord coord) {
        return new Coord(coord.x, coord.y, coord.z);
    }

    private int x = 0;
    private int y = 0;
    private int z = 0;

    private Coord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void addX(int dX) {
        x += dX;
    }

    public void addY(int dY) {
        y += dY;
    }

    public void addZ(int dZ) {
        z += dZ;
    }

    public void add(Coord coord) {
        x += coord.x;
        y += coord.y;
        z += coord.z;
    }

    public void set(Coord coord) {
        x = coord.x;
        y = coord.y;
        z = coord.z;
    }

    public AbsDirection getAngel() {
        return Coord.zero().getAngel(this);
    }

    public AbsDirection getAngel(Coord coord) {
        AbsDirection d;
        if (x > coord.x) {
            if (y > coord.y) {
                d = AbsDirection.OTHER;
            } else if (y < coord.y) {
                d = AbsDirection.OTHER;
            } else {
                d = AbsDirection.W;
            }
        } else if (x < coord.x) {
            if (y > coord.y) {
                d = AbsDirection.OTHER;
            } else if (y < coord.y) {
                d = AbsDirection.OTHER;
            } else {
                d = AbsDirection.E;
            }
        } else {
            if (y > coord.y) {
                d = AbsDirection.S;
            } else if (y < coord.y) {
                d = AbsDirection.N;
            } else {
                d = AbsDirection.OTHER;
            }
        }
        return d;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coord coord = (Coord) o;

        if (x != coord.x) return false;
        if (y != coord.y) return false;
        return z == coord.z;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %s)", x, y, z);
    }

}
