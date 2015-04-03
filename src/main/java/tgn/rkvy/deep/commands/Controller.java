package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.IOController;
import tgn.rkvy.deep.data.DataSeedAdapter;
import tgn.rkvy.deep.data.JsonDataSource;
import tgn.rkvy.deep.data.Seed;
import tgn.rkvy.deep.entities.*;
import tgn.rkvy.deep.seeds.SeedSource;
import tgn.rkvy.deep.seeds.VkSeedSource;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final SeedSource seedSource = new VkSeedSource();
    private final DataSeedAdapter dataSeedAdapter = new DataSeedAdapter(new JsonDataSource());
    private IOController ioController = new IOController();

    private boolean working = true;
    private boolean shouldReloadScene = true;
    private String currentTag;
    private Setting currentSetting;
    private Room currentRoom;
    private List<Event> currentEvents;
    private List<Door> currentDoors;
    private Teleport currentTeleport;

    public Controller() {
        currentTag = seedSource.getStartTag();
    }

    public boolean isWorking() {
        return working;
    }

    void stop() {
        working = false;
    }

    public void loadSceneIfNeed() {
        if (shouldReloadScene) {
            seedSource.load(currentTag);
            Seed currentSeed = seedSource.getCurrentSeed();
            List<Seed> directionSeeds = seedSource.getDirectionSeeds();
            currentSetting = dataSeedAdapter.getSetting(currentSeed);
            currentRoom = dataSeedAdapter.getRoom(currentSeed);
            currentEvents = dataSeedAdapter.getEvents(currentSeed);
            currentDoors = dataSeedAdapter.getDoors(currentSeed, directionSeeds);
            if (currentDoors.size() == 0) {
                currentTeleport = dataSeedAdapter.getTeleport(currentSeed);
            } else {
                currentTeleport = null;
            }
            renderCurrentScene();
            ioController.debug("loaded " + currentSeed);
            shouldReloadScene = false;
        }
    }

    public void renderCurrentScene() {
        List<Sceneable> sceneableList = new ArrayList<>();
        sceneableList.add(currentSetting);
        sceneableList.add(currentRoom);
        if (currentEvents.size() > 0) {
            sceneableList.addAll(currentEvents);
        }
        if (currentDoors.size() > 0) {
            sceneableList.addAll(currentDoors);
        }
        if (currentTeleport != null) {
            sceneableList.add(currentTeleport);
        }

        Scene scene = new Scene(this);
        for (Sceneable sceneable : sceneableList) {
            sceneable.processScene(scene);
        }
        scene.render();
    }

    public void processCommand() {
        String s = ioController.in();
        if (!s.isEmpty()) {
            String[] inputs = s.split(" ");
            if (inputs.length > 0) {
                Entity.Alias commandAlias = null;
                List<Entity.Alias> params = new ArrayList<>();
                boolean commandParsed = true;
                for (int i = 0; i < inputs.length; i++) {
                    String input = inputs[i].trim();
                    List<Entity.Alias> aliasList;
                    if (i == 0) {
                        aliasList = dataSeedAdapter.getSuitableCommands(input);
                    } else {
                        aliasList = new ArrayList<>();
                        for (Door door : currentDoors) {
                            Entity.Alias suitableAlias = door.getSuitableAlias(input);
                            if (suitableAlias != null) {
                                aliasList.add(suitableAlias);
                            }
                        }
                        if (currentTeleport != null) {
                            Entity.Alias suitableAlias = currentTeleport.getSuitableAlias(input);
                            if (suitableAlias != null) {
                                aliasList.add(suitableAlias);
                            }
                        }
                    }
                    final boolean found;
                    int count = aliasList.size();
                    switch (count) {
                        case 0:
                            ioController.outln(String.format("Что значит %s?", input));
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
                            ioController.outln(String.format("Что значит %s? Возможные варианты:", input));
                            for (Entity.Alias entityAlias : aliasList) {
                                ioController.out(entityAlias.getAliasText());
                            }
                            found = false;
                            break;
                        default:
                            ioController.outln(String.format("%s допускает много трактований.", input));
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
                        ioController.outln(sb.toString());
                        Command command = (Command) commandAlias.getEntity();
                        command.execute(this, params);
                    }
                }
            }
        }
    }


    public void setCurrentTag(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag");
        }
        currentTag = tag;
        shouldReloadScene = true;
    }

    public IOController getIO() {
        return ioController;
    }

    public String randomTag() {
        return seedSource.getRandomTag();
    }
}
