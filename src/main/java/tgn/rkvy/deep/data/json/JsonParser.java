package tgn.rkvy.deep.data.json;

import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.actions.Action;
import tgn.rkvy.deep.commands.Command;
import tgn.rkvy.deep.entities.*;

public class JsonParser {

    private final SettingJsonParser settingJsonParser;
    private final CommandJsonParser commandJsonParser;
    private final GlobalJsonParser globalJsonParser;

    public JsonParser(OnParsedListener onParsedListener) {
        settingJsonParser = new SettingJsonParser(onParsedListener);
        commandJsonParser = new CommandJsonParser(onParsedListener);
        globalJsonParser = new GlobalJsonParser(onParsedListener);
    }

    public void parse(JSONObject jsonObject) {
        JSONArray settingsJsonArray = jsonObject.optJSONArray("settings");
        settingJsonParser.parseSettings(settingsJsonArray);
        JSONArray commandsJsonArray = jsonObject.optJSONArray("commands");
        commandJsonParser.parseCommands(commandsJsonArray);
        JSONObject globalJsonObject = jsonObject.optJSONObject("global");
        globalJsonParser.parse(globalJsonObject);
    }

    public interface OnParsedListener {

        void onSetting(Setting setting);

        void onLocation(Location location);

        void onRoom(Room room);

        void onDoor(Door door);

        void onEvent(Event event);

        void onTeleport(Teleport teleport);

        void onCommand(Command command);

        void onAction(Action action);

        void onGlobalConstant(String key, String value);

    }

}
