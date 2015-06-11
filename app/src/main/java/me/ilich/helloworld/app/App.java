package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.*;
import me.ilich.helloworld.app.datasource.DataSource;
import me.ilich.helloworld.app.datasource.HardcodeDataSource;
import me.ilich.helloworld.app.datasource.ReadDataSource;
import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Player;
import me.ilich.helloworld.app.entities.Room;
import me.ilich.helloworld.app.entities.primitives.*;
import me.ilich.helloworld.app.stories.DachaStory;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class App {

    private static List<Command> commands = new ArrayList<>();

    static {
        commands.add(new ExitCommand());
        commands.add(new HelpCommand());

        commands.add(new DefaultMoveCommand());
        commands.add(new WalkCommand());

        commands.add(new OpenCommand());
        commands.add(new CloseCommand());

        commands.add(new LookCommand());

        commands.add(new InventoryCommand());
        commands.add(new DestroyCommand());
        commands.add(new PickUpCommand());
        commands.add(new PutCommand());
        commands.add(new DropCommand());

    }

    public static void main(String... args) {
        System.out.println("begin");
        App app = new App();
        app.init();
        app.run();
        System.out.println("end");
    }

    private static String readConsole() {
        String result = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            result = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private ReadDataSource dataSource;

    private Coord currentCoord = Coord.zero();
    private boolean working = true;
    private boolean roomDescriptionVisible = true;
    private Room currentRoom = null;
    private Player player = null;

    private Controller controller = new Controller() {

        @Override
        public void stop() {
            working = false;
        }

        @Override
        public void showRoomDescription() {
            roomDescriptionVisible = true;
        }

        @Override
        public Room getCurrentRoom() {
            return currentRoom;
        }

        @Override
        public List<Command> getCommands() {
            return commands;
        }

        @Override
        public void tryMoveBy(Coord coord) {
            List<Entity> entities = dataSource.
                    getChildEntities(currentRoom.getId(), Enterable.class, Coordinable.class).
                    stream().
                    filter(entity -> ((Coordinable) entity).getCoord().equals(coord)).
                    collect(Collectors.toCollection(ArrayList::new));
            //TODO количество элементов
            if (entities.size() == 1) {
                Entity entity = entities.get(0);
                if (entity instanceof Openable) {
                    switch (((Openable) entity).getOpenState()) {
                        case OPEN:
                            Enterable enterable = (Enterable) entity;
                            enterable.onEnter(this);
                            break;
                        case CLOSE:
                            controller.println("Закрыто.");
                            break;
                    }
                } else {
                    Enterable enterable = (Enterable) entities.get(0);
                    enterable.onEnter(this);
                }
            } else {
                controller.println("Вы не можете идти в этом направлении.");
            }
        }

        @Override
        public void print(String s) {
            System.out.print(s);
        }

        @Override
        public void println(String s) {
            System.out.println(s);
        }

        @Override
        public List<Entity> getInventoryEntities(Class<? extends Primitive>... primitives) {
            return dataSource.getChildEntities(player.getId(), primitives);
        }

        @Override
        public List<Entity> getCurrentRoomEntities(Class<? extends Primitive>... primitives) {
            return dataSource.getChildEntities(currentRoom.getId(), primitives);
        }

        @Override
        public Player getPlayer() {
            return player;
        }

        @Override
        public List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives) {
            return dataSource.getChildEntities(parentId, primitives);
        }

        @Override
        public void setCurrentRoom(Room newRoom) {
            App.this.currentCoord.set(newRoom.getCoord());
            currentRoom = newRoom;
            player.setParentId(currentRoom.getId());
            roomDescriptionVisible = true;
        }

        @Override
        public List<Entity> getEntities(Class<? extends Primitive>... primitives) {
            return dataSource.getEntities(primitives);
        }

    };

    public App() {
        DataSource dataSource = new HardcodeDataSource();
        //new SandboxStory().loadTo(dataSource);
        new DachaStory().loadTo(dataSource);
        this.dataSource = dataSource;
    }

    public void init() {
        initPlayer();
        initRoom();
    }

    private void initRoom() {
        List<Entity> roomEntities = dataSource.getEntities(player.getParentId());
        if (roomEntities.isEmpty()) {
            throw new RuntimeException("no room");
        } else {
            currentRoom = (Room) roomEntities.get(0);
        }
        currentCoord = Coord.coord(currentRoom.getCoord());
    }

    private void initPlayer() {
        player = (Player) dataSource.getEntities(Player.class).stream().findFirst().orElse(null);
        if (player == null) {
            throw new RuntimeException("no player");
        }
    }

    public void run() {
        while (working) {
            if (roomDescriptionVisible) {
                displayRoom();
                displayDoors();
                roomDescriptionVisible = false;
            }
            final String input = readConsole().toLowerCase().trim();
            List<Action> actions = new ArrayList<>();
            commands.stream().forEach(command -> actions.addAll(command.suitableActions(input)));
            switch (actions.size()) {
                case 0:
                    controller.println(String.format("Что значит '%s'?", input));
                    break;
                case 1:
                    actions.get(0).execute(controller);
                    break;
                case 2:
                    controller.println(String.format("Возможно, вы имели ввиду '%s' или '%s'.", actions.get(0).getTitle(), actions.get(1).getTitle()));
                    break;
                default:
                    controller.println(String.format("Ввод '%s' допускает множество трактований.", input));
            }
        }
    }

    private void displayRoom() {
        controller.println(String.format("*** %s ***", TitleUtils.i(currentRoom)));
        List<Entity> roomEntities = new ArrayList<>();
        roomEntities.addAll(dataSource.getEntities(currentRoom.getId()));
        roomEntities.addAll(dataSource.getChildEntities(currentRoom.getId()));
        roomEntities.forEach(entity -> {
            if (entity instanceof Scenable) {
                ((Scenable) entity).onScene(controller);
            }
        });
    }

    private void displayDoors() {
        List<Entity> doors = dataSource.getChildEntities(currentRoom.getId(), Enterable.class);
        StringBuilder doorsStringBuilder = new StringBuilder();
        List<Coordinable> coordinableDoors = new ArrayList<>();
        doors.forEach(entity -> {
            if (entity instanceof Coordinable) {
                Coordinable coordinable = (Coordinable) entity;
                coordinableDoors.add(coordinable);
            } else {
                //TODO non-coordinable door
            }
        });
        if (coordinableDoors.size() > 0) {
            coordinableDoors.sort((o1, o2) -> o1.getCoord().getAngel().compareTo(o2.getCoord().getAngel()));
            doorsStringBuilder.append("Выходы: ");
            final boolean[] first = {true};
            coordinableDoors.forEach(item -> {
                if (first[0]) {
                    first[0] = false;
                } else {
                    doorsStringBuilder.append(" ");
                }

                final String t;
                switch (item.getCoord().getAngel()) {
                    case N:
                        t = "С";
                        break;
                    case E:
                        t = "В";
                        break;
                    case S:
                        t = "Ю";
                        break;
                    case W:
                        t = "З";
                        break;
                    default:
                        t = "";
                }
                doorsStringBuilder.append(String.format("%s (%s)", t, ((Enterable) item).getTitle()));
            });
        }
        controller.println(doorsStringBuilder.toString());
    }

}

