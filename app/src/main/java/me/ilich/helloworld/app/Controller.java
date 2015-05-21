package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.Command;
import me.ilich.helloworld.app.datasource.DataSource;
import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.List;
import java.util.UUID;

public interface Controller {

    void stop();

    void tryMoveTo(Coord coord);

    void showRoomDescription();

    Room getCurrentRoom();

    List<Command> getCommands();

    void tryMoveBy(Coord coord);

    void print(String s);

    void println(String s);

    Coord getCurrentCoord();

    Door getDoor(Coord coordFrom, Coord coordTo);

    List<Entity> getInventoryEntities(Class<? extends Primitive>... primitives);

    List<Entity> getCurrentRoomEntities(Class<? extends Primitive>... primitives);

    DataSource getDataSource();

    Player getPlayer();

    List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives);
}
