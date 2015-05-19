package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.Command;
import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Entity;

import java.util.List;

public interface Controller {

    void stop();

    void tryMoveTo(Coord coord);

    void showRoomDescription();

    Room getCurrentRoom();

    List<Item> getInventory();

    List<Command> getCommands();

    void tryMoveBy(Coord coord);

    void println(String s);

    Coord getCurrentCoord();

    Door getDoor(Coord coordFrom, Coord coordTo);

    List<Entity> getCurrentRoomEntities();
}
