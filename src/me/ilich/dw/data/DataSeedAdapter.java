package me.ilich.dw.data;

import java.util.ArrayList;
import java.util.List;

import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Room;
import me.ilich.dw.entities.Setting;


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
        List<String> destinationRoomIds = new ArrayList<>();
        for (Seed direstionSeed : directionSeeds) {
            destinationRoomIds.add(direstionSeed.getRoomId());
        }
        return dataSource.getDoors(seed.getSettingId(), seed.getRoomId(), destinationRoomIds);
    }

}
