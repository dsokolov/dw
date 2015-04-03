package tgn.rkvy.deep.data;

import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

import java.util.List;


public interface DataSource {

    Setting getSetting(String settingId);

    Room getRoom(String settingId, String roomId);

    List<Door> getDoors(Seed seed, List<Seed> directionSeeds);

    List<Event> getEvents(String settingId, String eventId);

    Teleport getTeleport(Seed seed);

    List<Command.Alias> getSuitableCommands(String s);

    List<Entity.Alias> getSuitableDoors(String alias);
}
