package me.ilich.helloworld.app.datasource.json;

import me.ilich.helloworld.app.entities.Room;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParser {

    private final SettingJsonParser settingJsonParser;

    public JsonParser(OnParsedListener onParsedListener) {
        settingJsonParser = new SettingJsonParser(onParsedListener);
    }

    public void parse(JSONObject jsonObject) {
        JSONArray settingsJsonArray = jsonObject.optJSONArray("settings");
        settingJsonParser.parseSettings(settingsJsonArray);
    }

    public interface OnParsedListener {

        void onRoom(Room room);

    }

}
