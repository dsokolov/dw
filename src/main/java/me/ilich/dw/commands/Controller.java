package me.ilich.dw.commands;

import me.ilich.dw.IOController;
import me.ilich.dw.data.DataSeedAdapter;
import me.ilich.dw.data.JsonDataSource;
import me.ilich.dw.data.Seed;
import me.ilich.dw.entities.*;
import me.ilich.dw.seeds.SeedSource;
import me.ilich.dw.seeds.VkSeedSource;

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
            for (Seed seed : directionSeeds) {
                if (currentSeed.getSettingId().equals(seed.getSettingId())) {
                    ioController.out(seed + "");
                }
            }
            currentSetting = dataSeedAdapter.getSetting(currentSeed);
            currentRoom = dataSeedAdapter.getRoom(currentSeed);
            currentEvents = dataSeedAdapter.getEvents(currentSeed);
            currentDoors = dataSeedAdapter.getDoors(currentSeed, directionSeeds);

            renderCurrentScene();
            shouldReloadScene = false;
        }
    }

    public void renderCurrentScene() {
        List<Sceneable> sceneableList = new ArrayList<>();
        sceneableList.add(currentSetting);
        sceneableList.add(currentRoom);
        sceneableList.addAll(currentEvents);
        sceneableList.addAll(currentDoors);

        Scene scene = new Scene(this);
        for (Sceneable sceneable : sceneableList) {
            sceneable.processScene(scene);
        }
        scene.render();
    }

    public void processCommand() {
        String s = ioController.in();
        String[] inputs = s.split(" ");
        Entity.Alias commandAlias = null;
        Entity.Alias[] params = new Entity.Alias[inputs.length - 1];
        boolean commandParsed = true;
        for (int i = 0; i < inputs.length; i++) {
            String input = inputs[i];
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
            }
            final boolean found;
            int count = aliasList.size();
            switch (count) {
                case 0:
                    ioController.out(String.format("Что значит %s?", input));
                    found = false;
                    break;
                case 1:
                    if (i == 0) {
                        commandAlias = aliasList.get(0);
                    } else {
                        params[i - 1] = aliasList.get(0);
                    }
                    found = true;
                    break;
                case 2:
                case 3:
                    ioController.out(String.format("Что значит %s? Возможные варианты:", input));
                    for (Entity.Alias entityAlias : aliasList) {
                        ioController.out(entityAlias.getAliasText());
                    }
                    found = false;
                    break;
                default:
                    ioController.out(String.format("%s допускает много трактований.", input));
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
                ioController.out(sb.toString());
                Command command = (Command) commandAlias.getEntity();
                command.execute(this, params);
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

}
