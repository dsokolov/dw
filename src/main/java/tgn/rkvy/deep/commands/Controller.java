package tgn.rkvy.deep.commands;

import tgn.rkvy.deep.IOController;
import tgn.rkvy.deep.data.AllDataSeedFilter;
import tgn.rkvy.deep.data.DataSeedAdapter;
import tgn.rkvy.deep.data.DataSource;
import tgn.rkvy.deep.data.Seed;
import tgn.rkvy.deep.data.json.JsonDataSource;
import tgn.rkvy.deep.entities.*;
import tgn.rkvy.deep.seeds.SeedSource;
import tgn.rkvy.deep.seeds.VkSeedSource;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final SeedSource seedSource = new VkSeedSource();
    private final DataSource dataSource = new JsonDataSource();
    private final DataSeedAdapter dataSeedAdapter = new DataSeedAdapter(dataSource, new AllDataSeedFilter());
    private IOController ioController = new IOController();
    //
    private boolean working = true;
    private boolean shouldReloadScene = true;
    private LoadMode loadMode = LoadMode.ID;
    //
    private String currentTag;
    private Point currentPoint;
    //
    private Setting currentSetting;
    private Location currentLocation;
    private Room currentRoom;
    private List<Event> currentEvents;
    private List<Door> currentDoors;
    private Teleport currentTeleport;
    //
    private CommandProcessor commandProcessor = new CommandProcessor(new CommandProcessor.Source() {
        @Override
        public List<Entity.Alias> getSuitableCommands(String input) {
            return dataSeedAdapter.getSuitableCommands(input);
        }

        @Override
        public List<Door> getCurrentDoors() {
            return currentDoors;
        }

        @Override
        public Teleport getCurrentTeleport() {
            return currentTeleport;
        }

        @Override
        public IOController getIoController() {
            return ioController;
        }

        @Override
        public Controller getController() {
            return Controller.this;
        }
    });

    public Controller() {
        //setCurrentTag(seedSource.getStartTag());
        setIds(new Point("F", "1", "0"));
    }

    public boolean isWorking() {
        return working;
    }

    void stop() {
        working = false;
    }

    public void loadSceneIfNeed() {
        if (shouldReloadScene) {
            switch (loadMode) {
                case SEED:
                    seedSource.load(currentTag);
                    Seed currentSeed = seedSource.getCurrentSeed();
                    List<Seed> directionSeeds = seedSource.getDirectionSeeds();
                    currentSetting = dataSeedAdapter.getSetting(currentSeed);
                    currentLocation = dataSeedAdapter.getLocation(currentSeed);
                    currentEvents = dataSeedAdapter.getEvents(currentSeed);
                    currentDoors = dataSeedAdapter.getDoors(currentSeed);
                    if (currentDoors.size() == 0) {
                        currentTeleport = dataSeedAdapter.getTeleport(currentSeed);
                    } else {
                        currentTeleport = null;
                    }
                    ioController.debug("loaded " + currentSeed);
                    break;
                case ID:
                    currentSetting = dataSource.getSetting(currentPoint);
                    currentLocation = dataSource.getLocation(currentPoint);
                    currentRoom = dataSource.getRoom(currentPoint);
                    currentEvents = dataSource.getEvents(currentPoint, null); //TODO
                    currentDoors = dataSource.getDoors(currentPoint);
                    if (currentDoors.size() == 0) {
                        currentTeleport = dataSource.getTeleport(currentPoint);
                    } else {
                        currentTeleport = null;
                    }
                    break;
            }
            renderCurrentScene();
            shouldReloadScene = false;
        }
    }

    public void renderCurrentScene() {
        List<Sceneable> sceneableList = new ArrayList<>();
        sceneableList.add(currentSetting);
        sceneableList.add(currentLocation);
        if (currentEvents.size() > 0) {
            sceneableList.addAll(currentEvents);
        }
        if (currentDoors.size() > 0) {
            sceneableList.addAll(currentDoors);
        }
        if (currentTeleport != null) {
            sceneableList.add(currentTeleport);
        }

        Scene scene = new Scene(this.ioController);
        for (Sceneable sceneable : sceneableList) {
            sceneable.processScene(scene);
        }
        scene.render();
    }

    public void processCommand() {
        String s = ioController.in();
        s.trim();
        if (s.isEmpty()) {

        } else {
            commandProcessor.process(s);
        }
    }

    public void setCurrentTag(String tag) {
        currentTag = tag;
        shouldReloadScene = true;
        loadMode = LoadMode.SEED;
    }

    public void setIds(Point point) {
        currentPoint = point;
        shouldReloadScene = true;
        loadMode = LoadMode.ID;
    }

    public IOController getIO() {
        return ioController;
    }

    public String randomTag() {
        return seedSource.getRandomTag();
    }

    private enum LoadMode {
        SEED,
        ID
    }
}
