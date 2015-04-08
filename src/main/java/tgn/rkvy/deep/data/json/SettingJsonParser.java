package tgn.rkvy.deep.data.json;

import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.Utils;
import tgn.rkvy.deep.entities.*;

import java.util.ArrayList;
import java.util.List;

class SettingJsonParser {

    private final JsonParser.OnParsedListener onParsedListener;

    SettingJsonParser(JsonParser.OnParsedListener onParsedListener) {
        this.onParsedListener = onParsedListener;
    }

    public void parseSettings(JSONArray settingsJsonArray) {
        if (settingsJsonArray != null) {
            for (int settingIndex = 0; settingIndex < settingsJsonArray.length(); settingIndex++) {
                JSONObject settingJsonObject = settingsJsonArray.optJSONObject(settingIndex);
                final Setting setting = parseSetting(settingJsonObject);
                onParsedListener.onSetting(setting);
                parseLocations(settingJsonObject, setting);
                parseEvents(settingJsonObject, setting);
                parseTeleport(settingJsonObject, setting);
            }
        }
    }

    private Setting parseSetting(JSONObject jsonObject) {
        String settingId = jsonObject.optString("id");
        String settingTitle = jsonObject.optString("title");
        Point point = new Point(settingId);
        return new Setting(point, settingTitle);
    }

    private void parseEvents(JSONObject settingJsonObject, Setting setting) {
        JSONArray eventsJsonArray = settingJsonObject.optJSONArray("events");
        if (eventsJsonArray != null) {
            for (int i = 0; i < eventsJsonArray.length(); i++) {
                JSONObject eventJsonObject = eventsJsonArray.optJSONObject(i);
                String eventId = eventJsonObject.optString("id");
                String eventText = eventJsonObject.optString("text");
                final Event event = new Event(setting.getPoint(), eventId, eventText);
                onParsedListener.onEvent(event);
            }
        }
    }

    private void parseLocations(JSONObject settingJsonObject, Setting setting) {
        JSONArray locationsJsonArray = settingJsonObject.optJSONArray("locations");
        if (locationsJsonArray != null) {
            for (int locationIndex = 0; locationIndex < locationsJsonArray.length(); locationIndex++) {
                JSONObject locationJsonObject = locationsJsonArray.optJSONObject(locationIndex);
                final Location location = parseLocation(setting, locationJsonObject);
                onParsedListener.onLocation(location);
                JSONArray roomsJsonArray = locationJsonObject.optJSONArray("rooms");
                parseRooms(roomsJsonArray, setting, location);
            }
        }
    }

    private void parseRooms(JSONArray roomsJsonArray, Setting setting, Location location) {
        if (roomsJsonArray != null) {
            for (int i = 0; i < roomsJsonArray.length(); i++) {
                JSONObject roomJsonObject = roomsJsonArray.optJSONObject(i);
                Room room = parseRoom(roomJsonObject, setting, location);
                onParsedListener.onRoom(room);
                JSONArray doorsJsonArray = roomJsonObject.optJSONArray("doors");
                if (doorsJsonArray != null) {
                    for (int doorIndex = 0; doorIndex < doorsJsonArray.length(); doorIndex++) {
                        JSONObject doorJsonObject = doorsJsonArray.optJSONObject(doorIndex);
                        final Door door = parseDoor(room.getPoint(), doorJsonObject);
                        onParsedListener.onDoor(door);
                    }
                }
            }
        }
    }

    private Room parseRoom(JSONObject roomJsonObject, Setting setting, Location location) {
        String roomId = roomJsonObject.optString("room_id");
        String shortText = roomJsonObject.optString("short");
        Point p = location.getPoint().copyRoom(roomId);
        Room room = new Room(p, shortText);
        return room;
    }

    private void parseTeleport(JSONObject settingJsonObject, Setting setting) {
        JSONObject teleportJsonObject = settingJsonObject.optJSONObject("teleport");
        if (teleportJsonObject != null) {
            String[] aliases = Utils.jsonArrayToStringArray(teleportJsonObject.optJSONArray("aliases"));
            String shortText = teleportJsonObject.optString("short");
            String longText = teleportJsonObject.optString("long");
            List<CommandableEntity.CommandPattern> commandPatterns = parseCommandPattenrs(teleportJsonObject);
            Teleport teleport = new Teleport(setting.getPoint(), aliases, shortText, longText, commandPatterns);
            onParsedListener.onTeleport(teleport);
        }
    }

    private Location parseLocation(Setting setting, JSONObject jsonObject) {
        String locationId = jsonObject.optString("id");
        Point point = setting.getPoint().copyLocation(locationId);
        String locationTitle = jsonObject.optString("title");
        String locationDescription = jsonObject.optString("description");
        return new Location(point, locationTitle, locationDescription);
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

    private Door parseDoor(Point sourcePoint, JSONObject jsonObject) {
        String destinationSettingId = jsonObject.optString("setting_id");
        String destinationLocationId = jsonObject.optString("location_id");
        String destinationRoomId = jsonObject.optString("room_id");
        Point destinationPoint = sourcePoint.copy(destinationSettingId, destinationLocationId, destinationLocationId);

        String doorShortText = jsonObject.optString("short");
        String doorLongText = jsonObject.optString("long");
        JSONArray commandIdsJsonArray = jsonObject.optJSONArray("commands");
        String[] commandIds = Utils.jsonArrayToStringArray(commandIdsJsonArray);
        JSONArray aliasesJsonArray = jsonObject.optJSONArray("aliases");
        String[] aliases = Utils.jsonArrayToStringArray(aliasesJsonArray);
        List<CommandableEntity.CommandPattern> commandPatterns = parseCommandPattenrs(jsonObject);

        return new Door(aliases, sourcePoint, destinationPoint, doorShortText, commandIds, commandPatterns);
    }


}
