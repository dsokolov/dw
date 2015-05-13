package me.ilich.helloworld.app;

import me.ilich.helloworld.app.commands.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class App {

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

    private List<Command> commands = new ArrayList<>();
    private List<Room> rooms = new ArrayList<>();

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
                System.out.println("Вы не можете идти в эиом направлении.");
            } else {
                App.this.coord.add(door.getCoord());
            }
        }

        @Override
        public void showRoomDescription() {
            roomDescriptionVisible = true;
        }

    };

    public App() {
        commands.add(new ExitCommand());
        commands.add(new HelpCommand());
        commands.add(new DefaultMoveCommand());
        commands.add(new LookCommand());

        rooms.add(new Room(Coord.xy(0, 0), "Начальная комната", "Начальная комната", new Door[]{Door.north(), Door.east(), Door.south(), Door.west()}));
        rooms.add(new Room(Coord.xy(1, 0), "Восточная комната", "Восточная комната", new Door[]{Door.west()}));
        rooms.add(new Room(Coord.xy(-1, 0), "Западная комната", "Западная комната", new Door[]{Door.east()}));
        rooms.add(new Room(Coord.xy(0, 1), "Северная комната", "Северная комната", new Door[]{Door.south()}));
        rooms.add(new Room(Coord.xy(0, -1), "Южная комната", "Южная комната", new Door[]{Door.north()}));
    }

    public void run() {

        while (working) {
            currentRoom = rooms.stream().filter(room -> room.getCoord().equals(coord)).findFirst().get();
            if (roomDescriptionVisible) {
                System.out.println(currentRoom.getTitle());
                Door[] roomDoors = currentRoom.getDoors();
                StringBuilder doorsStringBuilder = new StringBuilder();
                boolean first = true;
                for (Door roomDoor : roomDoors) {
                    if (first) {
                        first = false;
                    } else {
                        doorsStringBuilder.append(" ");
                    }
                    String s = String.format("[%s]", roomDoor.getDirectionTitle());
                    doorsStringBuilder.append(s);
                }
                System.out.println(doorsStringBuilder.toString());
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
                    actions.get(0).getOnExecute().onExecute(controller);
                    break;
                default:
                    System.out.println("неопределённый ввод");
            }
        }
    }

}
