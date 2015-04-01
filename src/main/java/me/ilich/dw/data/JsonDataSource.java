package me.ilich.dw.data;

import me.ilich.dw.commands.Command;
import me.ilich.dw.commands.EmptyCommand;
import me.ilich.dw.commands.ExitCommand;
import me.ilich.dw.commands.HelpCommand;
import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Event;
import me.ilich.dw.entities.Room;
import me.ilich.dw.entities.Setting;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

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
        String id = commandJsonObject.optString("id");
        JSONArray aliasesJsonArray = commandJsonObject.optJSONArray("aliases");
        String[] aliases = new String[aliasesJsonArray.length()];
        for (int i = 0; i < aliasesJsonArray.length(); i++) {
            aliases[i] = aliasesJsonArray.optString(i);
        }
        String actionText = commandJsonObject.optString("actionText");
        final Command command;
        switch (id) {
            case "exit":
                JSONArray positiveJsonArray = commandJsonObject.optJSONArray("positive");
                String[] positive = new String[positiveJsonArray.length()];
                for (int i = 0; i < positiveJsonArray.length(); i++) {
                    positive[i] = positiveJsonArray.optString(i);
                }
                command = new ExitCommand(aliases, actionText, positive);
                break;
            case "help":
                command = new HelpCommand(aliases, actionText);
                break;
            default:
                command = new EmptyCommand();
        }
        return command;
    }

    private void parseSettings(JSONArray settingsJsonArray) {
        if (settingsJsonArray != null) {
            for (int settingIndex = 0; settingIndex < settingsJsonArray.length(); settingIndex++) {
                JSONObject settingJsonObject = settingsJsonArray.optJSONObject(settingIndex);
                final Setting setting = parseSetting(settingJsonObject);
                settingList.add(setting);
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
                JSONArray eventsJsonArray = settingJsonObject.optJSONArray("events");
                if (eventsJsonArray != null) {
                    for (int i = 0; i < eventsJsonArray.length(); i++) {
                        JSONObject eventJsonObject = eventsJsonArray.optJSONObject(i);
                        String eventId = eventJsonObject.optString("id");
                        String eventText = eventJsonObject.optString("text");
                        final Event event = new Event(setting.getId(), eventId, eventText);
                        eventList.add(event);
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
        String settingId = setting.getId();
        String roomId = jsonObject.optString("id");
        String roomTitle = jsonObject.optString("title");
        String roomDescription = jsonObject.optString("description");
        return new Room(settingId, roomId, roomTitle, roomDescription);
    }

    private Door parseDoor(Setting setting, Room room, JSONObject jsonObject) {
        String settingId = setting.getId();
        String sourceRoomId = room.getId();
        String destinationRoomId = jsonObject.optString("room_id");
        String doorDescription = jsonObject.optString("description");
        return new Door(settingId, sourceRoomId, destinationRoomId, doorDescription);
    }

    @Override
    public Setting getSetting(String settingId) {
        Setting result = null;
        Setting defaultSetting = null;
        for (Setting setting : settingList) {
            if (setting.getId().equalsIgnoreCase(settingId)) {
                result = setting;
                break;
            }
            if (setting.getId().equalsIgnoreCase(DEFAULT_ID)) {
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
            String currentSettingId = room.getSettingId();
            String currentRoomId = room.getId();
            if (currentSettingId.equals(settingId) && currentRoomId.equals(roomId)) {
                result = room;
                break;
            }
            if (currentSettingId.equals(settingId) && currentRoomId.equals(DEFAULT_ID)) {
                defaultSettingRoom = room;
            }
            if (currentSettingId.equals(DEFAULT_ID) && currentRoomId.equals(DEFAULT_ID)) {
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
    public List<Door> getDoors(String settingId, String sourceRoomId, List<String> directionSeeds) {
        List<Door> result = new ArrayList<>();
        for (Door door : doorList) {
            String currentSettingId = door.getSettingId();
            String currentSourceRoomId = door.getSourceRoomId();
            String currentDestinationRoomId = door.getDestinationRoomId();
            if (currentSettingId.equals(settingId) && currentSourceRoomId.equals(sourceRoomId) && directionSeeds.contains(currentDestinationRoomId)) {
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
                result.add(event);
            }
        }
        return result;
    }

    @Override
    public List<Command> getSuitableCommands(String inputString) {
        List<Command> suitableCommandList = new ArrayList<>();
        String[] inputs = inputString.split(" ");
        if (inputs.length > 0) {
            String commandBody = inputs[0];
            for (Command command : commandList) {
                if (command.isSuitable(commandBody)) {
                    suitableCommandList.add(command);
                }
            }
        }
        return suitableCommandList;
    }

}
