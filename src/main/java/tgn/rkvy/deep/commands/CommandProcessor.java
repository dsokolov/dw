package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.IOController;
import tgn.rkvy.deep.entities.Door;
import tgn.rkvy.deep.entities.Entity;
import tgn.rkvy.deep.entities.Teleport;

import java.util.ArrayList;
import java.util.List;

public class CommandProcessor {

    private final Source source;

    public CommandProcessor(Source source) {
        this.source = source;
    }

    public void process(String s) {
        String[] inputs = s.split(" ");
        if (inputs.length > 0) {
            Entity.Alias commandAlias = null;
            List<Entity.Alias> params = new ArrayList<>();
            boolean commandParsed = true;
            for (int i = 0; i < inputs.length; i++) {
                String input = inputs[i].trim();
                List<Entity.Alias> aliasList;
                if (i == 0) {
                    aliasList = source.getSuitableCommands(input);
                } else {
                    aliasList = new ArrayList<>();
                    for (Door door : source.getCurrentDoors()) {
                        Entity.Alias suitableAlias = door.getSuitableAlias(input);
                        if (suitableAlias != null) {
                            aliasList.add(suitableAlias);
                        }
                    }
                    if (source.getCurrentTeleport() != null) {
                        Entity.Alias suitableAlias = source.getCurrentTeleport().getSuitableAlias(input);
                        if (suitableAlias != null) {
                            aliasList.add(suitableAlias);
                        }
                    }
                }
                final boolean found;
                int count = aliasList.size();
                switch (count) {
                    case 0:
                        source.getIoController().outln(String.format("Что значит %s?", input));
                        found = false;
                        break;
                    case 1:
                        if (i == 0) {
                            commandAlias = aliasList.get(0);
                        } else {
                            params.add(aliasList.get(0));
                        }
                        found = true;
                        break;
                    case 2:
                    case 3:
                        source.getIoController().outln(String.format("Что значит %s? Возможные варианты:", input));
                        for (Entity.Alias entityAlias : aliasList) {
                            source.getIoController().outln(entityAlias.getAliasText());
                        }
                        found = false;
                        break;
                    default:
                        source.getIoController().outln(String.format("%s допускает много трактований.", input));
                        found = false;
                }
                if (!found) {
                    commandParsed = false;
                    break;
                }
            }
            if (commandParsed) {
                if (commandAlias != null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("> ");
                    sb.append(commandAlias.getAliasText());
                    for (Entity.Alias alias : params) {
                        sb.append(" ");
                        sb.append(alias.getAliasText());
                    }
                    source.getIoController().outln(sb.toString());
                    Command command = (Command) commandAlias.getEntity();
                    command.execute(source.getController(), params);
                }
            }
        } else {

        }
    }

    public interface Source {

        List<Entity.Alias> getSuitableCommands(String input);

        List<Door> getCurrentDoors();

        Teleport getCurrentTeleport();

        IOController getIoController();

        Controller getController();
    }

}
