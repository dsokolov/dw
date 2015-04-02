package me.ilich.dw.data;

import me.ilich.dw.commands.Command;
import me.ilich.dw.entities.*;

import java.util.List;


public interface DataSource {

    Setting getSetting(String settingId);

    Room getRoom(String settingId, String roomId);

    List<Door> getDoors(String settingId, String sourceRoomId, List<String> directionSeeds);

    List<Event> getEvents(String settingId, String eventId);

    List<Command.Alias> getSuitableCommands(String s);

    List<Entity.Alias> getSuitableDoors(String alias);
}
