package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Primitive;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class HardcodeDataSource implements DataSource {

    private static final List<Entity> entities = new ArrayList<>();

    static {
        Player player = new Player(UUID.randomUUID());

        entities.add(player);

        Room centerRoom = new Room.Builder(UUID.randomUUID(), null)
                .coord(Coord.xy(0, 0))
                .title("Начальная комната")
                .description("Начальная комната.")
                .build();
        entities.add(centerRoom);
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), centerRoom.getId()).coord(Coord.north()).create());
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), centerRoom.getId()).coord(Coord.east()).create());
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), centerRoom.getId()).coord(Coord.south()).create());
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), centerRoom.getId()).coord(Coord.west()).create());

        Room eastRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, 0)).title("Восточная комната").description("Восточная комната").build();
        entities.add(eastRoom);
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), eastRoom.getId()).coord(Coord.south()).create());

        Room westRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(-1, 0)).title("Западная комната").description("Западная комната").build();
        entities.add(westRoom);
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), westRoom.getId()).coord(Coord.south()).create());

        Room northRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, 1)).title("Северная комната").description("Северная комната").build();
        entities.add(northRoom);
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), northRoom.getId()).coord(Coord.south()).create());

        Room southRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, -1)).title("Южная комната").description("Южная комната").build();
        entities.add(southRoom);
        entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), southRoom.getId()).coord(Coord.south()).create());

        Room warehous = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, -1)).title("Кладовка").description("Душное и пыльное помещение").build();
        entities.add(warehous);

        PickableItem ball = new PickableItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("мяч|мяч|мячу|мяч|мячом|мяче").scene("Здесь лежит мяч.").look("Красно-синий резиновый мяч.").build();
        ContainerItem box = new ContainerItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("ящик|ящика|ящику|ящик|ящиком|ящике").scene("В деревянный ящик можно что-нибудь положить.").build();
        PickableItem note = new PickableItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("записка|записки|записке|записку|запиской|записке").scene("Кто-то оставил здесь записку.").build();
        PickableItem trash = new PickableItem.Builder(UUID.randomUUID(), box.getId()).title("мусор|мусора|мусору|мусор|мусором|мусоре").scene("Аккуратной горкой лежит какой-то мусор.").look("Какие-то пыльные тряпки, обломки пластмассы и осколки стекла. Ничего полезного.").build();
        entities.add(ball);
        entities.add(note);
        entities.add(box);
        entities.add(trash);




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
    public List<Entity> getEntities(UUID id) {
        return entities.stream().filter(entity -> Objects.equals(id, entity.getId())).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Entity> getEntities(Class<? extends Primitive>... primitives) {
        return entities.stream().filter(entity -> {
            boolean result = true;
            for (Class<? extends Primitive> primitive : primitives) {
                if (!primitive.isInstance(entity)) {
                    result = false;
                    break;
                }
            }
            return result;
        }).collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public List<Entity> getChildEntities(UUID parentId, Class<? extends Primitive>... primitives) {
        return entities.stream().filter(entity -> {
            boolean b = true;
            for (Class<? extends Primitive> primitive : primitives) {
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
