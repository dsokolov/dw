package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Door;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.List;
import java.util.UUID;

public interface DataSource {

    Room getRoom(Coord coord);

    Door getDoor(Coord fromCoord, Coord toCoord);

    List<Door> getDoorsFrom(Coord coord);

    List<Entity> getEntities(UUID id);

    List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>[] primitives);

}
