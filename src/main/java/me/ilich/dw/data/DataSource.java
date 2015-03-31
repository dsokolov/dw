package me.ilich.dw.data;

import java.util.List;

import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Room;
import me.ilich.dw.entities.Setting;


public interface DataSource {

    Setting getSetting(String settingId);

    Room getRoom(String settingId, String roomId);

    List<Door> getDoors(String settingId, String sourceRoomId, List<String> directionSeeds);

}
