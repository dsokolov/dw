package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.*;
import me.ilich.helloworld.app.data.AbsDirection;
import me.ilich.helloworld.app.datasource.DataSource;
import me.ilich.helloworld.app.datasource.HardcodeDataSource;
import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Primitive;
import me.ilich.helloworld.app.entities.primitives.Scenable;
import me.ilich.helloworld.app.utils.TitleUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private DataSource dataSource = new HardcodeDataSource();

    /*private final List<Item> inventory = new ArrayList<>();*/

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
        public void tryMoveTo(Coord toCoord) {
            FreeWayDoor door = dataSource.getDoor(currentCoord, toCoord);
            if (door == null) {
                controller.println("Вы не можете идти в этом направлении.");
            } else {
                switch (door.getState()) {
                    case OPEN:
                        if (currentCoord.equals(door.getCoordA())) {
                            App.this.currentCoord.set(door.getCoordB());
                        } else {
                            App.this.currentCoord.set(door.getCoordA());
                        }
                        roomDescriptionVisible = true;
                        break;
                    case CLOSE:
                        controller.println("Дверь закрыта");
                        break;
                    case LOCKED:
                        break;
                }
            }
        }

        @Override
        public void showRoomDescription() {
            roomDescriptionVisible = true;
        }

        @Override
        public Room getCurrentRoom() {
            return currentRoom;
        }

/*        @Override
        public List<Item> getInventory() {
            return inventory;
        }*/

        @Override
        public List<Command> getCommands() {
            return commands;
        }

        @Override
        public void tryMoveBy(Coord coord) {
            Coord newCoord = Coord.coord(currentCoord);
            newCoord.add(coord);
            tryMoveTo(newCoord);
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
        public Coord getCurrentCoord() {
            return currentCoord;
        }

        @Override
        public FreeWayDoor getDoor(Coord coordFrom, Coord coordTo) {
            return dataSource.getDoor(coordFrom, coordTo);
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
        public DataSource getDataSource() {
            return dataSource;
        }

        @Override
        public Player getPlayer() {
            return player;
        }

        @Override
        public List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives) {
            return dataSource.getChildEntities(parentId, primitives);
        }

    };

    public void run() {
        player = (Player) dataSource.getEntities(Player.class).stream().findFirst().orElse(null);
        while (working) {
            currentRoom = dataSource.getRoom(currentCoord);
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
                    controller.println("неправильный ввод");
                    break;
                case 1:
                    actions.get(0).execute(controller);
                    break;
                default:
                    controller.println("неопределённый ввод");
            }
        }
    }

    private void displayRoom() {
        controller.println(String.format("*** %s ***", TitleUtils.i(currentRoom)));
        List<Entity> roomEntities = new ArrayList<>();
        roomEntities.addAll(dataSource.getEntities(currentRoom.getId()));
        roomEntities.addAll(dataSource.getChildEntities(currentRoom.getId(), new Class[]{}));
        roomEntities.forEach(entity -> {
            if (entity instanceof Scenable) {
                ((Scenable) entity).onScene(controller);
            }
        });
    }

    private void displayDoors() {
        List<FreeWayDoor> doors = dataSource.getDoorsFrom(currentCoord);
        StringBuilder doorsStringBuilder = new StringBuilder();
        if (doors.size() > 0) {
            List<AbsDirection> directions = new ArrayList<>();
            doors.forEach(item -> {
                Coord coord = currentCoord.equals(item.getCoordA()) ? item.getCoordB() : item.getCoordA();
                AbsDirection direction = currentCoord.getAngel(coord);
                directions.add(direction);
            });
            directions.sort(Enum::compareTo);
            doorsStringBuilder.append("Выходы: ");
            final boolean[] first = {true};
            directions.forEach(item -> {
                if (first[0]) {
                    first[0] = false;
                } else {
                    doorsStringBuilder.append(" ");
                }

                final String t;
                switch (item) {
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
                doorsStringBuilder.append(t);
            });
            controller.println(doorsStringBuilder.toString());
        }
    }

}
