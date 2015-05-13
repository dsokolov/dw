package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.Room;

import java.util.List;

public interface DataSource {

    List<Room> getRooms();

}
