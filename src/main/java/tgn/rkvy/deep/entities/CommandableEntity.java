package tgn.rkvy.deep.entities;

import tgn.rkvy.deep.commands.Command;

import java.util.List;

public class CommandableEntity extends Entity {

    private final List<CommandPattern> commandPatterns;

    public CommandableEntity(String[] aliases, List<CommandPattern> commandPatterns) {
        super(aliases);
        this.commandPatterns = commandPatterns;
    }

    public CommandableEntity(String[] aliases, String shortText, List<CommandPattern> commandPatterns) {
        super(aliases, shortText);
        this.commandPatterns = commandPatterns;
    }

    public CommandableEntity(String[] aliases, String shortText, String longText, List<CommandPattern> commandPatterns) {
        super(aliases, shortText, longText);
        this.commandPatterns = commandPatterns;
    }

    public CommandPattern getSuitablePattern(Command command) {
        CommandPattern result = null;
        for (CommandPattern commandPattern : commandPatterns) {
            if (commandPattern.isSuitalbe(command)) {
                result = commandPattern;
                break;
            }
        }
        return result;
    }

    protected List<CommandPattern> getCommandPatterns() {
        return commandPatterns;
    }

    public static class CommandPattern {

        private final String id;
        private final String action;

        public CommandPattern(String id, String action) {
            this.id = id;
            this.action = action;
        }

        public boolean isSuitalbe(Command command) {
            return id.equalsIgnoreCase(command.getId());
        }

        public String getActionText() {
            return action;
        }

    }

}
