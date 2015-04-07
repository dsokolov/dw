package tgn.rkvy.deep.data.json;

import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.Utils;
import tgn.rkvy.deep.entities.*;

import java.util.ArrayList;
import java.util.List;

class SettingJsonParser {

    private final List<Setting> settingList;
    private final List<Room> roomList;
    private final List<Door> doorList;
    private final List<Event> eventList;
    private final List<Teleport> teleports;

    SettingJsonParser(List<Setting> settingList, List<Room> roomList, List<Door> doorList, List<Event> eventList, List<Teleport> teleports) {
        this.settingList = settingList;
        this.roomList = roomList;
        this.doorList = doorList;
        this.eventList = eventList;
        this.teleports = teleports;
    }

    public void parseSettings(JSONArray settingsJsonArray) {
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

    private Setting parseSetting(JSONObject jsonObject) {
        String settingId = jsonObject.optString("id");
        String settingTitle = jsonObject.optString("title");
        return new Setting(settingId, settingTitle);
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

    private Room parseRoom(Setting setting, JSONObject jsonObject) {
        String settingId = setting.getSettingId();
        String roomId = jsonObject.optString("id");
        String roomTitle = jsonObject.optString("title");
        String roomDescription = jsonObject.optString("description");
        return new Room(settingId, roomId, roomTitle, roomDescription);
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


}
