package me.ilich.helloworld.app;

import java.util.List;

public interface Controller {

    void stop();

    void tryMoveBy(Coord coord);

    void showRoomDescription();

    Room getCurrentRoom();

    List<Item> getInventory();

}
