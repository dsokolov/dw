package tgn.rkvy.deep.data;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.Utils;
import tgn.rkvy.deep.commands.*;
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

    public JsonDataSource() {
        String[] fileNames = new String[]{
                "commands.json",
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
                parse(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void parse(JSONObject jsonObject) {
        JSONArray settingsJsonArray = jsonObject.optJSONArray("settings");
        parseSettings(settingsJsonArray);
        JSONArray commandsJsonArray = jsonObject.optJSONArray("commands");
        parseCommands(commandsJsonArray);
    }

    private void parseCommands(JSONArray commandsJsonArray) {
        if (commandsJsonArray != null) {
            for (int commandIndex = 0; commandIndex < commandsJsonArray.length(); commandIndex++) {
                JSONObject commandJsonObject = commandsJsonArray.optJSONObject(commandIndex);
                final Command command = parseCommand(commandJsonObject);
                commandList.add(command);
            }
        }
    }

    private Command parseCommand(JSONObject commandJsonObject) {
        String id = commandJsonObject.optString("id").toLowerCase();
        String[] aliases = Utils.jsonArrayToStringArray(commandJsonObject.optJSONArray("aliases"));
        final Command command;
        switch (id) {
            case "exit":
                String promt = commandJsonObject.optString("promt");
                String[] positive = Utils.jsonArrayToStringArray(commandJsonObject.optJSONArray("positive"));
                String[] negative = Utils.jsonArrayToStringArray(commandJsonObject.optJSONArray("negative"));
                String unknown = commandJsonObject.optString("unknown");
                command = new ExitCommand(id, aliases, promt, positive, negative, unknown);
                break;
            case "help":
                command = new HelpCommand(id, aliases);
                break;
            case "look":
                String failText = commandJsonObject.optString("fail");
                String lookAtItemText = commandJsonObject.optString("lookAtItem");
                command = new LookCommand(id, aliases, lookAtItemText, failText);
                break;
            case "go":
                command = parseCommandGo(id, commandJsonObject, aliases);
                break;
            case "goup":
                command = parseCommandGoUp(id, commandJsonObject, aliases);
                break;
            case "godown":
                command = parseCommandGoDown(id, commandJsonObject, aliases);
                break;
            case "touch":
                command = parseTouchCommand(commandJsonObject, id, aliases);
                break;
            case "jump":
                command = new JumpCommand(id, aliases);
                break;
            default:
                command = new EmptyCommand(id);
        }
        return command;
    }

    private Command parseTouchCommand(JSONObject commandJsonObject, String id, String[] aliases) {
        Command command;
        String noParams = commandJsonObject.optString("noParams");
        String manyParams = commandJsonObject.optString("manyParams");
        String invalidType = commandJsonObject.optString("invalidType");
        command = new TouchCommand(id, aliases, noParams, manyParams, invalidType);
        return command;
    }

    private Command parseCommandGo(String id, JSONObject commandJsonObject, String[] aliases) {
        Command command;
        String noParams = commandJsonObject.optString("noParams");
        String manyParams = commandJsonObject.optString("manyParams");
        String invalidType = commandJsonObject.optString("invalidType");
        command = new GoCommand(id, aliases, noParams, manyParams, invalidType);
        return command;
    }

    private Command parseCommandGoUp(String id, JSONObject commandJsonObject, String[] aliases) {
        Command command;
        String noParams = commandJsonObject.optString("noParams");
        String manyParams = commandJsonObject.optString("manyParams");
        String invalidType = commandJsonObject.optString("invalidType");
        command = new GoDownCommand(id, aliases, noParams, manyParams, invalidType);
        return command;
    }

    private Command parseCommandGoDown(String id, JSONObject commandJsonObject, String[] aliases) {
        Command command;
        String noParams = commandJsonObject.optString("noParams");
        String manyParams = commandJsonObject.optString("manyParams");
        String invalidType = commandJsonObject.optString("invalidType");
        command = new GoDownCommand(id, aliases, noParams, manyParams, invalidType);
        return command;
    }

    private void parseSettings(JSONArray settingsJsonArray) {
        if (settingsJsonArray != null) {
            for (int settingIndex = 0; settingIndex < settingsJsonArray.length(); settingIndex++) {
                JSONObject settingJsonObject = settingsJsonArray.optJSONObject(settingIndex);
                final Setting setting = parseSetting(settingJsonObject);
                settingList.add(setting);
                parseRooms(settingJsonObject, setting);
                parseEvents(settingJsonObject, setting);
                parseTeleport(settingJsonObject, setting);
            }
        }
    }

    private void parseTeleport(JSONObject settingJsonObject, Setting setting) {
        JSONObject teleportJsonObject = settingJsonObject.optJSONObject("teleport");
        if (teleportJsonObject != null) {
            String[] aliases = Utils.jsonArrayToStringArray(teleportJsonObject.optJSONArray("aliases"));
            String shortText = teleportJsonObject.optString("short");
            String longText = teleportJsonObject.optString("long");
            List<CommandableEntity.CommandPattern> commandPatterns = parseCommandPattenrs(teleportJsonObject);
            Teleport teleport = new Teleport(setting.getSettingId(), aliases, shortText, longText, commandPatterns);
            teleports.add(teleport);
        }
    }

    private List<CommandableEntity.CommandPattern> parseCommandPattenrs(JSONObject jsonObject) {
        List<CommandableEntity.CommandPattern> result = new ArrayList<>();
        JSONArray commandsJsonArray = jsonObject.optJSONArray("commands");
        for (int i = 0; i < commandsJsonArray.length(); i++) {
            JSONObject commandJsonObject = commandsJsonArray.optJSONObject(i);
            String id = commandJsonObject.optString("id");
            String action = commandJsonObject.optString("action");
            CommandableEntity.CommandPattern commandPattern = new CommandableEntity.CommandPattern(id, action);
            result.add(commandPattern);
        }
        return result;
    }

    private void parseEvents(JSONObject settingJsonObject, Setting setting) {
        JSONArray eventsJsonArray = settingJsonObject.optJSONArray("events");
        if (eventsJsonArray != null) {
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObject = eventsJsonArray.optJSONObject(i);
                String eventId = eventJsonObject.optString("id");
                String eventText = eventJsonObject.optString("text");
                final Event event = new Event(setting.getSettingId(), eventId, eventText);
                eventList.add(event);
            }
        }
    }

    private void parseRooms(JSONObject settingJsonObject, Setting setting) {
        JSONArray roomsJsonArray = settingJsonObject.optJSONArray("rooms");
        if (roomsJsonArray != null) {
            for (int roomIndex = 0; roomIndex < roomsJsonArray.length(); roomIndex++) {
                JSONObject roomJsonObject = roomsJsonArray.optJSONObject(roomIndex);
                final Room room = parseRoom(setting, roomJsonObject);
                roomList.add(room);
                JSONArray doorsJsonArray = roomJsonObject.optJSONArray("doors");
                if (doorsJsonArray != null) {
                    for (int doorIndex = 0; doorIndex < doorsJsonArray.length(); doorIndex++) {
                        JSONObject doorJsonObject = doorsJsonArray.optJSONObject(doorIndex);
                        final Door door = parseDoor(setting, room, doorJsonObject);
                        doorList.add(door);
                    }
                }
            }
        }
    }

    private Setting parseSetting(JSONObject jsonObject) {
        String settingId = jsonObject.optString("id");
        String settingTitle = jsonObject.optString("title");
        return new Setting(settingId, settingTitle);
    }

    private Room parseRoom(Setting setting, JSONObject jsonObject) {
        String settingId = setting.getSettingId();
        String roomId = jsonObject.optString("id");
        String roomTitle = jsonObject.optString("title");
        String roomDescription = jsonObject.optString("description");
        return new Room(settingId, roomId, roomTitle, roomDescription);
    }

    private Door parseDoor(Setting setting, Room room, JSONObject jsonObject) {
        String settingId = setting.getSettingId();
        String sourceRoomId = room.getId();
        String destinationRoomId = jsonObject.optString("room_id");
        String doorShortText = jsonObject.optString("short");
        String doorLongText = jsonObject.optString("long");
        JSONArray commandIdsJsonArray = jsonObject.optJSONArray("commands");
        String[] commandIds = Utils.jsonArrayToStringArray(commandIdsJsonArray);
        JSONArray aliasesJsonArray = jsonObject.optJSONArray("aliases");
        String[] aliases = Utils.jsonArrayToStringArray(aliasesJsonArray);
        List<CommandableEntity.CommandPattern> commandPatterns = parseCommandPattenrs(jsonObject);
        return new Door(aliases, settingId, sourceRoomId, destinationRoomId, doorShortText, commandIds, commandPatterns);
    }

    @Override
    public Setting getSetting(String settingId) {
        Setting result = null;
        Setting defaultSetting = null;
        for (Setting setting : settingList) {
            if (setting.isSame(settingId)) {
                result = setting;
                break;
            }
            if (setting.isSame(DEFAULT_ID)) {
                defaultSetting = setting;
            }
        }
        if (result == null) {
            result = defaultSetting;
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
                result = room;
                break;
            }
            if (room.isSame(settingId, DEFAULT_ID)) {
                defaultSettingRoom = room;
            }
            if (room.isSame(DEFAULT_ID, DEFAULT_ID)) {
                defaultRoom = room;
            }
        }
        if (result == null) {
            result = defaultSettingRoom;
        }
        if (result == null) {
            result = defaultRoom;
        }
        return result;
    }

    @Override
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
    }

    @Override
    public List<Event> getEvents(String settingId, String eventId) {
        List<Event> result = new ArrayList<>();
        for (Event event : eventList) {
            String currentSettingId = event.getSettingId();
            String currentEventId = event.getId();
            if (currentSettingId.equals(settingId) && currentEventId.equals(eventId)) {
                result.add(event);
            }
        }
        return result;
    }

    @Override
    public Teleport getTeleport(Seed seed) {
        Teleport result = null;
        Teleport defaultTeleport = null;
        for (Teleport teleport : teleports) {
            if (teleport.getSettingId().equals(seed.getSettingId())) {
                result = teleport;
                break;
            }
            if (teleport.getSettingId().equals(DEFAULT_ID)) {
                defaultTeleport = teleport;
            }
        }
        if (result == null) {
            result = defaultTeleport;
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
