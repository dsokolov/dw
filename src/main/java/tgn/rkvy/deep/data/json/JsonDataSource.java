package tgn.rkvy.deep.data.json;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.data.DataSource;
import tgn.rkvy.deep.entities.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JsonDataSource implements DataSource {

    private static final String DEFAULT_ID = "default";

    private List<Command> commandList = new ArrayList<>();
    private List<Action> actionList = new ArrayList<>();
    private List<Setting> settingList = new ArrayList<>();
    private List<Location> locationList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<Teleport> teleportList = new ArrayList<>();
    private Map<String, String> globalConstants = new HashMap<>();

    private final JsonParser jsonParser = new JsonParser(new JsonParser.OnParsedListener() {
        @Override
        public void onSetting(Setting setting) {
            settingList.add(setting);
        }

        @Override
        public void onLocation(Location location) {
            locationList.add(location);
        }

        @Override
        public void onRoom(Room room) {
            roomList.add(room);
        }

        @Override
        public void onDoor(Door door) {
            doorList.add(door);
        }

        @Override
        public void onEvent(Event event) {
            eventList.add(event);
        }

        @Override
        public void onTeleport(Teleport teleport) {
            teleportList.add(teleport);
        }

        @Override
        public void onCommand(Command command) {
            commandList.add(command);
        }

        @Override
        public void onAction(Action action) {
            actionList.add(action);
        }

        @Override
        public void onGlobalConstant(String key, String value) {
            globalConstants.put(key, value);
        }
    });

    public JsonDataSource() {
        String[] fileNames = new String[]{
                "global.json",
                "commands.json",
                "actions.json",
                "setting_default.json",
                "setting_0.json",
                "setting_F.json"
        };
        for (String fileName : fileNames) {
            try (
                    InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                    StringWriter writer = new StringWriter()
            ) {
                IOUtils.copy(inputStream, writer);
                String s = writer.toString();
                JSONObject jsonObject = new JSONObject(s);
                jsonParser.parse(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Setting getSetting(String settingId) {
        Setting result = null;
        Setting defaultSetting = null;
        for (Setting setting : settingList) {
            if (setting.isSame(settingId)) {
                result = setting.copy();
                break;
            }
            if (setting.isSame(DEFAULT_ID)) {
                defaultSetting = setting;
            }
        }
        if (result == null && defaultSetting != null) {
            result = defaultSetting.copy();
        }
        return result;
    }

    @Override
    public Location getLocation(String settingId, String locationId) {
        Location result = null;
        Location defaultSettingLocation = null;
        Location defaultLocation = null;
        for (Location location : locationList) {
            if (location.isSame(settingId, locationId)) {
                result = location.copy();
                break;
            }
            if (location.isSame(settingId, DEFAULT_ID)) {
                defaultSettingLocation = location;
            }
            if (location.isSame(DEFAULT_ID, DEFAULT_ID)) {
                defaultLocation = location;
            }
        }
        if (result == null && defaultSettingLocation != null) {
            result = defaultSettingLocation.copy();
        }
        if (result == null && defaultLocation != null) {
            result = defaultLocation.copy();
        }
        return result;
    }

    @Override
    public Room getRoom(Point point) {
        Room result = null;
        for (Room room : roomList) {
            if (room.getPoint().sameRoom(point)) {
                result = room;
                break;
            }
        }
        return result;
    }

    @Override
    public List<Door> getDoors(Point sourcePoint) {
        List<Door> result = new ArrayList<>();
        for (Door door : doorList) {
            if (door.getSourcePoint().sameLocation(sourcePoint)) {
                result.add(door);
            }
        }
        return result;
    }

/*    @Override
    public List<Door> getDoors(Seed seed, List<Seed> directionSeeds) {
        String settingId = seed.getSettingId();
        String sourceLocationId = seed.getLocationId();
        List<Door> result = new ArrayList<>();
        for (Door door : doorList) {
            String doorSettingId = door.getSettingId();
            String doorSourceLocationId = door.getSourceLocationId();
            String doorDestinationLocationId = door.getDestinationLocationId();
            if (settingId.equals(doorSettingId) && sourceLocationId.equals(doorSourceLocationId)) {
                int count = 0;
                String tag = null;
                for (Seed directionSeed : directionSeeds) {
                    String directionSettingId = directionSeed.getSettingId();
                    String directionLocationId = directionSeed.getLocationId();
                    if (settingId.equals(directionSettingId) && doorDestinationLocationId.equals(directionLocationId)) {
                        tag = directionSeed.getTag();
                        count++;
                    }
                }
                if (count == 1) {
                    result.add(door.copyWithTag(tag));
                }
            }
        }
        return result;
    }*/

    @Override
    public List<Event> getEvents(String settingId, String eventId) {
        List<Event> result = new ArrayList<>();
        for (Event event : eventList) {
            String currentSettingId = event.getSettingId();
            String currentEventId = event.getId();
            if (currentSettingId.equals(settingId) && currentEventId.equals(eventId)) {
                result.add(event.copy());
            }
        }
        return result;
    }

    @Override
    public Teleport getTeleport(String settingId) {
        Teleport result = null;
        Teleport defaultTeleport = null;
        for (Teleport teleport : teleportList) {
            if (teleport.getSettingId().equals(settingId)) {
                result = teleport.copy();
                break;
            }
            if (teleport.getSettingId().equals(DEFAULT_ID)) {
                defaultTeleport = teleport;
            }
        }
        if (result == null && defaultTeleport != null) {
            result = defaultTeleport.copy();
        }
        return result;
    }

    @Override
    public List<Command.Alias> getSuitableCommands(String commandBody) {
        List<Entity.Alias> suitableCommandList = new ArrayList<>();
        for (Command command : commandList) {
            Entity.Alias alias = command.getSuitableAlias(commandBody);
            if (alias != null) {
                suitableCommandList.add(alias);
            }
        }
        return suitableCommandList;
    }

    @Override
    public List<Entity.Alias> getSuitableDoors(String alias) {
        List<Entity.Alias> result = new ArrayList<>();
        for (Door door : doorList) {
            Entity.Alias suitableAlias = door.getSuitableAlias(alias);
            if (suitableAlias != null) {
                result.add(suitableAlias);
            }
        }
        return result;
    }

    @Override
    public String getTextInvalidCommand(String s) {
        return String.format(globalConstants.get("invalidCommand"), s);
    }

}
