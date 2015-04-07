package tgn.rkvy.deep.data.json;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.data.DataSource;
import tgn.rkvy.deep.entities.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class JsonDataSource implements DataSource {

    private static final String DEFAULT_ID = "default";

    private List<Command> commandList = new ArrayList<>();
    private List<Setting> settingList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();
    private List<Event> eventList = new ArrayList<>();
    private List<Teleport> teleports = new ArrayList<>();

    private final JsonParser jsonParser = new JsonParser(settingList, commandList, roomList, doorList, eventList, teleports);

    public JsonDataSource() {
        String[] fileNames = new String[]{
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
    public Room getRoom(String settingId, String roomId) {
        Room result = null;
        Room defaultSettingRoom = null;
        Room defaultRoom = null;
        for (Room room : roomList) {
            if (room.isSame(settingId, roomId)) {
                result = room.copy();
                break;
            }
            if (room.isSame(settingId, DEFAULT_ID)) {
                defaultSettingRoom = room;
            }
            if (room.isSame(DEFAULT_ID, DEFAULT_ID)) {
                defaultRoom = room;
            }
        }
        if (result == null && defaultSettingRoom != null) {
            result = defaultSettingRoom.copy();
        }
        if (result == null && defaultRoom != null) {
            result = defaultRoom.copy();
        }
        return result;
    }

/*    @Override
    public List<Door> getDoors(Seed seed, List<Seed> directionSeeds) {
        String settingId = seed.getSettingId();
        String sourceRoomId = seed.getRoomId();
        List<Door> result = new ArrayList<>();
        for (Door door : doorList) {
            String doorSettingId = door.getSettingId();
            String doorSourceRoomId = door.getSourceRoomId();
            String doorDestinationRoomId = door.getDestinationRoomId();
            if (settingId.equals(doorSettingId) && sourceRoomId.equals(doorSourceRoomId)) {
                int count = 0;
                String tag = null;
                for (Seed directionSeed : directionSeeds) {
                    String directionSettingId = directionSeed.getSettingId();
                    String directionRoomId = directionSeed.getRoomId();
                    if (settingId.equals(directionSettingId) && doorDestinationRoomId.equals(directionRoomId)) {
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
    public List<Door> getDoors(String settingId, String sourceRoomId) {
        List<Door> result = new ArrayList<>();
        for (Door door : doorList) {
            String doorSettingId = door.getSettingId();
            String doorSourceRoomId = door.getSourceRoomId();
            if (settingId.equals(doorSettingId) && sourceRoomId.equals(doorSourceRoomId)) {
                result.add(door);
            }
        }
        return result;
    }

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
        for (Teleport teleport : teleports) {
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

}
