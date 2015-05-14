package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.*;
import me.ilich.helloworld.app.datasource.DataSource;
import me.ilich.helloworld.app.datasource.HardcodeDataSource;
import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Door;
import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App {

    private static List<Command> commands = new ArrayList<>();

    static {
        commands.add(new ExitCommand());
        commands.add(new HelpCommand());
        commands.add(new DefaultMoveCommand());
        commands.add(new LookCommand());
        commands.add(new DestroyCommand());
        commands.add(new PickUpCommand());
        commands.add(new PutCommand());
        commands.add(new DropCommand());
        commands.add(new InventoryCommand());
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

    private final List<Item> inventory = new ArrayList<>();

    private Coord coord = Coord.zero();
    private boolean working = true;
    private boolean roomDescriptionVisible = true;
    private Room currentRoom = null;

    private Controller controller = new Controller() {

        @Override
        public void stop() {
            working = false;
        }

        @Override
        public void tryMoveBy(Coord coord) {
            Door door = null;
            for (Door currentDoor : currentRoom.getDoors()) {
                if (currentDoor.getCoord().equals(coord)) {
                    door = currentDoor;
                    break;
                }
            }
            if (door == null) {
                System.out.println("Вы не можете идти в этом направлении.");
            } else {
                App.this.coord.add(door.getCoord());
                roomDescriptionVisible = true;
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

        @Override
        public List<Item> getInventory() {
            return inventory;
        }

    };

    public void run() {
        while (working) {
            currentRoom = dataSource.getRooms().stream().filter(room -> room.getCoord().equals(coord)).findFirst().get();
            if (roomDescriptionVisible) {
                displayRoom();
                displayItems();
                displayDoors();
                roomDescriptionVisible = false;
            }
            final String input = readConsole().toLowerCase().trim();
            List<Action> actions = new ArrayList<>();
            commands.stream().forEach(command -> actions.addAll(command.suitableActions(input)));
            switch (actions.size()) {
                case 0:
                    System.out.println("неправильный ввод");
                    break;
                case 1:
                    actions.get(0).execute(controller);
                    break;
                default:
                    System.out.println("неопределённый ввод");
            }
        }
    }

    private void displayRoom() {
        System.out.println(currentRoom.getTitle());
    }

    private void displayItems() {
        StringBuilder itemsStringBuilder = new StringBuilder();
        if (currentRoom.getItems().size() > 0) {
            itemsStringBuilder.append("Предметы: ");
            final boolean[] first = {true};
            currentRoom.getItems().forEach(item -> {
                if (first[0]) {
                    first[0] = false;
                } else {
                    itemsStringBuilder.append(", ");
                }
                itemsStringBuilder.append(item.getTitle());
            });
            itemsStringBuilder.append(".");
            System.out.println(itemsStringBuilder.toString());
        }
    }

    private void displayDoors() {
        StringBuilder doorsStringBuilder = new StringBuilder();
        if (currentRoom.getDoors().size() > 0) {
            doorsStringBuilder.append("Выходы: ");
            final boolean[] first = {true};
            currentRoom.getDoors().forEach(item -> {
                if (first[0]) {
                    first[0] = false;
                } else {
                    doorsStringBuilder.append(" ");
                }
                doorsStringBuilder.append(item.getDirectionTitle());
            });
            System.out.println(doorsStringBuilder.toString());
        }
    }

}
