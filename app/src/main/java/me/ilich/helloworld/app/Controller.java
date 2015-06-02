package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.Command;
import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.List;
import java.util.UUID;

public interface Controller {

    void stop();

    void showRoomDescription();

    Room getCurrentRoom();

    List<Command> getCommands();

    void tryMoveBy(Coord coord);

    void print(String s);

    void println(String s);

    List<Entity> getInventoryEntities(Class<? extends Primitive>... primitives);

    List<Entity> getCurrentRoomEntities(Class<? extends Primitive>... primitives);

    Player getPlayer();

    List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives);
}
