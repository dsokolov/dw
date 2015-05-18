package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Door;
import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HardcodeDataSource implements DataSource {

    private static final List<Room> rooms = new ArrayList<>();
    private static final List<Door> doors = new ArrayList<>();

    static {

        Item ball = new Item.Builder().title("мяч").description("Небольшой резиновый мяч.").pickable(true).containable(false).build();
        Item note = new Item.Builder().title("записка").description("На клочке бумаги что-то написано.").pickable(true).containable(false).build();
        Item box = new Item.Builder().title("ящик").description("Вместительный деревянный ящик").pickable(false).containable(true).item(note).build();

        Room centerRoom = new Room.Builder()
                .coord(Coord.xy(0, 0))
                .title("Начальная комната")
                .description("Начальная комната")
                .item(ball)
                .item(box)
                .build();

        Room eastRoom = new Room.Builder().coord(Coord.xy(1, 0)).title("Восточная комната").description("Восточная комната").build();
        Room westRoom = new Room.Builder().coord(Coord.xy(-1, 0)).title("Западная комната").description("Западная комната").build();
        Room northRoom = new Room.Builder().coord(Coord.xy(0, 1)).title("Северная комната").description("Северная комната").build();
        Room southRoom = new Room.Builder().coord(Coord.xy(0, -1)).title("Южная комната").description("Южная комната").build();
        Room warehous = new Room.Builder().coord(Coord.xy(1, -1)).title("Кладовка").description("Душное и пыльное помещение").build();

        rooms.add(centerRoom);
        rooms.add(eastRoom);
        rooms.add(westRoom);
        rooms.add(northRoom);
        rooms.add(southRoom);
        rooms.add(warehous);

        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(eastRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(westRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(northRoom.getCoord()).state(Door.State.CLOSE).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(southRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(southRoom.getCoord()).coordB(warehous.getCoord()).state(Door.State.CLOSE).create());

    }

    @Override
    public Room getRoom(Coord coord) {
        return rooms.stream().filter(room -> room.getCoord().equals(coord)).findFirst().get();
    }

    @Override
    public Door getDoor(Coord fromCoord, Coord toCoord) {
        Door d = doors.stream().filter(door -> {
            final boolean result;
            switch (door.getDirection()) {
                case AB:
                    result = door.getCoordA().equals(fromCoord) && door.getCoordB().equals(toCoord);
                    break;
                case BA:
                    result = door.getCoordB().equals(fromCoord) && door.getCoordA().equals(toCoord);
                    break;
                default:
                    result = (door.getCoordB().equals(fromCoord) && door.getCoordA().equals(toCoord)) || (door.getCoordA().equals(fromCoord) && door.getCoordB().equals(toCoord));
            }
            return result;
        }).findFirst().orElse(null);
        return d;
    }

    @Override
    public List<Door> getDoorsFrom(Coord coord) {
        return doors.stream().filter(door -> {
            final boolean result;
            switch (door.getDirection()) {
                case AB:
                    result = door.getCoordA().equals(coord);
                    break;
                case BA:
                    result = door.getCoordB().equals(coord);
                    break;
                default:
                    result = door.getCoordA().equals(coord) || door.getCoordB().equals(coord);
            }
            return result;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
