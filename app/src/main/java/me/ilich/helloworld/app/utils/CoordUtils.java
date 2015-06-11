package me.ilich.helloworld.app.utils;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Coordinable;

import java.util.Objects;

public class CoordUtils {

    public static boolean isSuitable(Entity entity, Coord direction) {
        if (entity instanceof Coordinable) {
            return Objects.equals(((Coordinable) entity).getCoord(), direction);
        } else {
            return false;
        }
    }

}
