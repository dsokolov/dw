package me.ilich.helloworld.app.stories;

import me.ilich.helloworld.app.datasource.WriteDataSource;
import me.ilich.helloworld.app.entities.*;

import java.util.UUID;

public class SandboxStory implements Story {

    @Override
    public void loadTo(WriteDataSource entities) {
        Player player = new Player(UUID.randomUUID());
        entities.add(player);

        {
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

            PickableItem ball = new PickableItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("мяч|мяч|мячу|мяч|мячом|мяче").scene("Здесь лежит мяч.").look("Красно-синий резиновый мяч.").build();
            ContainerItem box = new ContainerItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("ящик|ящика|ящику|ящик|ящиком|ящике").scene("В деревянный ящик можно что-нибудь положить.").build();
            PickableItem note = new PickableItem.Builder(UUID.randomUUID(), centerRoom.getId()).title("записка|записки|записке|записку|запиской|записке").scene("Кто-то оставил здесь записку.").build();
            entities.add(ball);
            entities.add(note);
            entities.add(box);

            PickableItem trash = new PickableItem.Builder(UUID.randomUUID(), box.getId()).title("мусор|мусора|мусору|мусор|мусором|мусоре").scene("Аккуратной горкой лежит какой-то мусор.").look("Какие-то пыльные тряпки, обломки пластмассы и осколки стекла. Ничего полезного.").build();
            entities.add(trash);

            player.setParentId(centerRoom.getId());

        }

        {
            Room eastRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, 0)).title("Восточная комната").description("Восточная комната").build();
            entities.add(eastRoom);
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), eastRoom.getId()).coord(Coord.west()).create());
        }

        {
            Room westRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(-1, 0)).title("Западная комната").description("Западная комната").build();
            entities.add(westRoom);
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), westRoom.getId()).coord(Coord.east()).create());

            entities.add(new Decoration(UUID.randomUUID(), westRoom.getId(), "кнопка", "Описание кнопки", "На стене находится кнопка."));

        }

        {
            Room northRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, 1)).title("Северная комната").description("Северная комната").build();
            entities.add(northRoom);
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), northRoom.getId()).coord(Coord.south()).create());
        }

        {
            Room southRoom = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(0, -1)).title("Южная комната").description("Южная комната").build();
            entities.add(southRoom);
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), southRoom.getId()).coord(Coord.north()).create());
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), southRoom.getId()).coord(Coord.east()).create());
        }

        {
            Room warehous = new Room.Builder(UUID.randomUUID(), null).coord(Coord.xy(1, -1)).title("Кладовка").description("Душное и пыльное помещение").build();
            entities.add(warehous);
            entities.add(new FreeWayDoor.Builder(UUID.randomUUID(), warehous.getId()).coord(Coord.west()).create());
        }
    }

}
