package tgn.rkvy.deep.data;

import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

import java.util.ArrayList;
import java.util.List;


public class DataSeedAdapter {

    private final DataSource dataSource;

    public DataSeedAdapter(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("dataSource");
        }
        this.dataSource = dataSource;
    }

    public Setting getSetting(Seed seed) {
        return dataSource.getSetting(seed.getSettingId());
    }

    public Room getRoom(Seed seed) {
        return dataSource.getRoom(seed.getSettingId(), seed.getRoomId());
    }

    public List<Door> getDoors(Seed seed, List<Seed> directionSeeds) {
        return dataSource.getDoors(seed, directionSeeds);
    }

    public List<Event> getEvents(Seed seed) {
        return dataSource.getEvents(seed.getSettingId(), seed.getEventId());
    }

    public List<Command.Alias> getSuitableCommands(String alias) {
        return dataSource.getSuitableCommands(alias);
    }

    public List<Entity.Alias> getSuitableParams(String alias) {
        List<Entity.Alias> result = new ArrayList<>();
        result.addAll(dataSource.getSuitableDoors(alias));
        result.addAll(dataSource.getSuitableCommands(alias));
        return result;
    }

    public Teleport getTeleport(Seed seed) {
        return dataSource.getTeleport(seed);
    }
}
