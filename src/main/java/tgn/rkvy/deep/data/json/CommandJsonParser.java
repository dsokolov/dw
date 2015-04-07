package tgn.rkvy.deep.data.json;

import org.json.JSONArray;
import org.json.JSONObject;
import tgn.rkvy.deep.Utils;
import tgn.rkvy.deep.commands.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CommandJsonParser {

    private final Map<String, CommandFactory> commandFactoryMap = new HashMap<>();

    private final List<Command> commandList;

    CommandJsonParser(List<Command> commandList) {
        this.commandList = commandList;
        commandFactoryMap.put("exit", new ExitCommandFactory());
        commandFactoryMap.put("help", new HelpCommandFactory());
        commandFactoryMap.put("look", new LookCommandFactory());
        commandFactoryMap.put("wait", new WaitCommandFactory());
        commandFactoryMap.put("go", new GoCommandFactory());
        commandFactoryMap.put("goup", new GoUpCommandFactory());
        commandFactoryMap.put("godown", new GoDownCommandFactory());
        commandFactoryMap.put("touch", new TouchCommandFactory());
        commandFactoryMap.put("jump", new JumpCommandFactory());
    }

    public void parseCommands(JSONArray commandsJsonArray) {
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
        if (commandFactoryMap.containsKey(id)) {
            CommandFactory commandFactory = commandFactoryMap.get(id);
            command = commandFactory.create(id, aliases, commandJsonObject);
        } else {
            command = new EmptyCommand(id);
        }
        return command;
    }

    private interface CommandFactory {
        Command create(String id, String[] aliases, JSONObject commandJsonObject);
    }

    private class ExitCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String promt = commandJsonObject.optString("promt");
            String[] positive = Utils.jsonArrayToStringArray(commandJsonObject.optJSONArray("positive"));
            String[] negative = Utils.jsonArrayToStringArray(commandJsonObject.optJSONArray("negative"));
            String unknown = commandJsonObject.optString("unknown");
            return new ExitCommand(id, aliases, promt, positive, negative, unknown);
        }
    }

    private class HelpCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            return new HelpCommand(id, aliases);
        }
    }

    private class LookCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String failText = commandJsonObject.optString("fail");
            String lookAtItemText = commandJsonObject.optString("lookAtItem");
            return new LookCommand(id, aliases, lookAtItemText, failText);
        }
    }

    private class WaitCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            return new WaitCommand(id, aliases, "");
        }
    }

    private class GoCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String noParams = commandJsonObject.optString("noParams");
            String manyParams = commandJsonObject.optString("manyParams");
            String invalidType = commandJsonObject.optString("invalidType");
            return new GoCommand(id, aliases, noParams, manyParams, invalidType);
        }
    }

    private class GoUpCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String noParams = commandJsonObject.optString("noParams");
            String manyParams = commandJsonObject.optString("manyParams");
            String invalidType = commandJsonObject.optString("invalidType");
            return new GoDownCommand(id, aliases, noParams, manyParams, invalidType);
        }
    }

    private class GoDownCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String noParams = commandJsonObject.optString("noParams");
            String manyParams = commandJsonObject.optString("manyParams");
            String invalidType = commandJsonObject.optString("invalidType");
            return new GoDownCommand(id, aliases, noParams, manyParams, invalidType);
        }
    }

    private class TouchCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            String noParams = commandJsonObject.optString("noParams");
            String manyParams = commandJsonObject.optString("manyParams");
            String invalidType = commandJsonObject.optString("invalidType");
            return new TouchCommand(id, aliases, noParams, manyParams, invalidType);
        }
    }

    private class JumpCommandFactory implements CommandFactory {
        @Override
        public Command create(String id, String[] aliases, JSONObject commandJsonObject) {
            return new JumpCommand(id, aliases);
        }
    }

}
