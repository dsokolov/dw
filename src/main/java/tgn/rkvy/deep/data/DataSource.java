package tgn.rkvy.deep.data;

import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

import java.util.List;


public interface DataSource {

    Setting getSetting(String settingId);

    Location getLocation(String settingId, String locationId);

    Room getRoom(Point point);

    List<Door> getDoors(Point sourcePoint);

    List<Event> getEvents(String settingId, String eventId);

    Teleport getTeleport(String settingId);

    List<Command.Alias> getSuitableCommands(String s);

    List<Entity.Alias> getSuitableDoors(String alias);


    String getTextInvalidCommand(String s);

}
