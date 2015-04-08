package tgn.rkvy.deep.data;

import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

import java.util.List;


public interface DataSource {

    Setting getSetting(Point point);

    Location getLocation(Point point);

    Room getRoom(Point point);

    List<Door> getDoors(Point sourcePoint);

    List<Event> getEvents(Point point, String eventId);

    Teleport getTeleport(Point point);

    List<Command.Alias> getSuitableCommands(String s);

    List<Entity.Alias> getSuitableDoors(String alias);


    String getTextInvalidCommand(String s);

}
