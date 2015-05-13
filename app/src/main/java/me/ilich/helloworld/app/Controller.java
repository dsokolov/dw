package me.ilich.helloworld.app;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public interface Controller {

    void stop();

    void tryMoveBy(Coord coord);

    void showRoomDescription();

    Room getCurrentRoom();

    List<Item> getInventory();

}
