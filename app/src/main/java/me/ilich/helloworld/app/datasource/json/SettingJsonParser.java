package me.ilich.helloworld.app.datasource.json;

import org.json.JSONArray;
import org.json.JSONObject;

class SettingJsonParser {

    private final JsonParser.OnParsedListener onParsedListener;

    SettingJsonParser(JsonParser.OnParsedListener onParsedListener) {
        this.onParsedListener = onParsedListener;
    }

    public void parseSettings(JSONArray settingsJsonArray) {
        if (settingsJsonArray != null) {
            for (int settingIndex = 0; settingIndex < settingsJsonArray.length(); settingIndex++) {
                JSONObject settingJsonObject = settingsJsonArray.optJSONObject(settingIndex);
            }
        }
    }

}
