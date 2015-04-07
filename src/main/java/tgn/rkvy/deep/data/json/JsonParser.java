package tgn.rkvy.deep.data.json;

import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

import java.util.List;
import java.util.Map;

public class JsonParser {

    private final SettingJsonParser settingJsonParser;
    private final CommandJsonParser commandJsonParser;
    private final GlobalJsonParser globalJsonParser;

    public JsonParser(List<Setting> settingList, List<Command> commandList, List<Room> roomList, List<Door> doorList, List<Event> eventList, List<Teleport> teleports, Map<String, String> globalConstants) {
        settingJsonParser = new SettingJsonParser(settingList, roomList, doorList, eventList, teleports);
        commandJsonParser = new CommandJsonParser(commandList);
        globalJsonParser = new GlobalJsonParser(globalConstants);
    }

    public void parse(JSONObject jsonObject) {
        JSONArray settingsJsonArray = jsonObject.optJSONArray("settings");
        settingJsonParser.parseSettings(settingsJsonArray);
        JSONArray commandsJsonArray = jsonObject.optJSONArray("commands");
        commandJsonParser.parseCommands(commandsJsonArray);
        JSONObject globalJsonObject = jsonObject.optJSONObject("global");
        globalJsonParser.parse(globalJsonObject);
    }

}
