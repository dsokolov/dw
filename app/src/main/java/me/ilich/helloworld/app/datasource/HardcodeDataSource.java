package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Entity;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HardcodeDataSource implements DataSource {

    private static final List<Door> doors = new ArrayList<>();
    private static final List<Entity> entities = new ArrayList<>();

    static {

        Player player = new Player(UUID.randomUUID());

        entities.add(player);

        UUID centerRoomId = UUID.randomUUID();

        Item ball = new Item.Builder(UUID.randomUUID(), centerRoomId).title("мяч").scene("Здесь лежит мяч.").look("Красно-синий резиновый мяч.").pickable(true).containable(false).build();
        Item note = new Item.Builder(UUID.randomUUID(), centerRoomId).title("записка").scene("Кто-то оставил здесь записку.").pickable(true).containable(false).build();
        Item box = new Item.Builder(UUID.randomUUID(), centerRoomId).title("ящик").scene("В деревянный ящик можно что-нибудь положить.").pickable(false).containable(true).item(note).build();

        entities.add(ball);
        entities.add(box);

        Room centerRoom = new Room.Builder(centerRoomId, null)
                .coord(Coord.xy(0, 0))
                .title("Начальная комната")
                .description("Начальная комната.")
                .build();

        Room eastRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, 0)).title("Восточная комната").description("Восточная комната").build();
        Room westRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(-1, 0)).title("Западная комната").description("Западная комната").build();
        Room northRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, 1)).title("Северная комната").description("Северная комната").build();
        Room southRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, -1)).title("Южная комната").description("Южная комната").build();
        Room warehous = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, -1)).title("Кладовка").description("Душное и пыльное помещение").build();

        entities.add(centerRoom);
        entities.add(eastRoom);
        entities.add(westRoom);
        entities.add(northRoom);
        entities.add(southRoom);
        entities.add(warehous);

        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(eastRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(westRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(northRoom.getCoord()).state(Door.State.CLOSE).create());
        doors.add(new Door.Builder().coordA(centerRoom.getCoord()).coordB(southRoom.getCoord()).create());
        doors.add(new Door.Builder().coordA(southRoom.getCoord()).coordB(warehous.getCoord()).state(Door.State.CLOSE).create());

        entities.add(new Decoration(UUID.randomUUID(), westRoom.getId(), "кнопка", "Описание кнопки", "На стене находится кнопка."));

    }

    @Override
    public Room getRoom(Coord coord) {
        Entity entity = entities.stream().filter(e -> {
            final boolean result;
            if (e instanceof Room) {
                result = ((Room) e).getCoord().equals(coord);
            } else {
                result = false;
            }
            return result;
        }).findFirst().get();
        return (Room) entity;
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

    @Override
    public List<Entity> getEntities(UUID id) {
        return entities.stream().filter(entity -> Objects.equals(id, entity.getId())).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Entity> getChildEntities(UUID parentId, Class<Primitive>[] primitives) {
        return entities.stream().filter(entity -> {
            boolean b = true;
            for (Class<Primitive> primitive : primitives) {
                if (!primitive.isInstance(entity)) {
                    b = false;
                    break;
                }
            }
            if (b) {
                b = Objects.equals(parentId, entity.getParentId());
            }
            return b;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

}
