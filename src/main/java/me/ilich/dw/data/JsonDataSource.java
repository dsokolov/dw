package me.ilich.dw.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import me.ilich.dw.data.DataSource;
import me.ilich.dw.entities.Door;
import me.ilich.dw.entities.Room;
import me.ilich.dw.entities.Setting;


public class JsonDataSource implements DataSource {

    private List<Setting> settingList = new ArrayList<>();
    private List<Room> roomList = new ArrayList<>();
    private List<Door> doorList = new ArrayList<>();

    public JsonDataSource() {
        String[] fileNames = new String[]{
                "setting_FA.json"
        };
        for (String fileName : fileNames) {
            File f = new File(fileName);
            readFromFile(f);
        }
    }

    private void readFromFile(File file) {
        try {
            byte[] b = Files.readAllBytes(file.toPath());
            JSONObject jsonObject = new JSONObject(new String(b));
            JSONArray settingsJsonArray = jsonObject.optJSONArray("settings");
            for (int settingIndex = 0; settingIndex < settingsJsonArray.length(); settingIndex++) {
                JSONObject settingJsonObject = settingsJsonArray.optJSONObject(settingIndex);
                final Setting setting = parseSetting(settingJsonObject);
                settingList.add(setting);
                JSONArray roomsJsonArray = settingJsonObject.optJSONArray("rooms");
                for (int roomIndex = 0; roomIndex < roomsJsonArray.length(); roomIndex++) {
                    JSONObject roomJsonObject = roomsJsonArray.optJSONObject(roomIndex);
                    final Room room = parseRoom(setting, roomJsonObject);
                    roomList.add(room);
                    JSONArray doorsJsonArray = roomJsonObject.optJSONArray("doors");
                    for (int doorIndex = 0; doorIndex < doorsJsonArray.length(); doorIndex++) {
                        JSONObject doorJsonObject = doorsJsonArray.optJSONObject(doorIndex);
                        final Door door = parseDoor(setting, room, doorJsonObject);
                        doorList.add(door);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        for (Setting setting : settingList) {
            if (setting.getId().equals(settingId)) {
                result = setting;
                break;
            }
        }
        if (result == null) {
            result = Setting.DEFAULT;
        }
        return result;
    }

    @Override
    public Room getRoom(String settingId, String roomId) {
        Room result = null;
        for (Room room : roomList) {
            String currentSettingId = room.getSettingId();
            String currentRoomId = room.getId();
            if (currentSettingId.equals(settingId) && currentRoomId.equals(roomId)) {
                result = room;
                break;
            }
        }
        if (result == null) {
            result = Room.DEFAULT;
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

}
