package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.FreeWayDoor;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.List;
import java.util.UUID;

public interface DataSource {

    @Deprecated
    Room getRoom(Coord coord);

    @Deprecated
    FreeWayDoor getDoor(Coord fromCoord, Coord toCoord);

    @Deprecated
    List<FreeWayDoor> getDoorsFrom(Coord coord);

    List<Entity> getEntities(UUID id);

    List<Entity> getEntities(Class<? extends Primitive>... primitives);

    List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>[] primitives);

}
