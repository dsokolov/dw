package me.ilich.helloworld.app.stories;

import me.ilich.helloworld.app.datasource.WriteDataSource;
import me.ilich.helloworld.app.entities.*;

public class SandboxStory implements Story {

    @Override
    public void loadTo(WriteDataSource entities) {

        Setting sadboxSetting = new Setting();

        Location sanboxLocation = new Location(sadboxSetting);

        {
            Room centerRoom = new Room.Builder(sanboxLocation)
                    .coord(Coord.xy(0, 0))
                    .title("Начальная комната")
                    .description("Начальная комната.")
                    .build();
            entities.add(centerRoom);
            entities.add(new FreeWayDoor.Builder(centerRoom).coord(Coord.north()).create());
            entities.add(new FreeWayDoor.Builder(centerRoom).coord(Coord.east()).create());
            entities.add(new FreeWayDoor.Builder(centerRoom).coord(Coord.south()).create());
            entities.add(new FreeWayDoor.Builder(centerRoom).coord(Coord.west()).create());

            PickableItem ball = new PickableItem.Builder(centerRoom).title("мяч|мяч|мячу|мяч|мячом|мяче").scene("Здесь лежит мяч.").look("Красно-синий резиновый мяч.").build();
            ContainerItem box = new ContainerItem.Builder(centerRoom).title("ящик|ящика|ящику|ящик|ящиком|ящике").scene("В деревянный ящик можно что-нибудь положить.").build();
            PickableItem note = new PickableItem.Builder(centerRoom).title("записка|записки|записке|записку|запиской|записке").scene("Кто-то оставил здесь записку.").build();
            entities.add(ball);
            entities.add(note);
            entities.add(box);

            PickableItem trash = new PickableItem.Builder(box).title("мусор|мусора|мусору|мусор|мусором|мусоре").scene("Аккуратной горкой лежит какой-то мусор.").look("Какие-то пыльные тряпки, обломки пластмассы и осколки стекла. Ничего полезного.").build();
            entities.add(trash);

            entities.add(new Player(centerRoom));
        }

        {
            Room eastRoom = new Room.Builder(sanboxLocation).coord(Coord.xy(1, 0)).title("Восточная комната").description("Восточная комната").build();
            entities.add(eastRoom);
            entities.add(new FreeWayDoor.Builder(eastRoom).coord(Coord.west()).create());
        }

        {
            Room westRoom = new Room.Builder(sanboxLocation).coord(Coord.xy(-1, 0)).title("Западная комната").description("Западная комната").build();
            entities.add(westRoom);
            entities.add(new FreeWayDoor.Builder(westRoom).coord(Coord.east()).create());

            entities.add(new Decoration(westRoom, "кнопка", "Описание кнопки", "На стене находится кнопка."));

        }

        {
            Room northRoom = new Room.Builder(sanboxLocation).coord(Coord.xy(0, 1)).title("Северная комната").description("Северная комната").build();
            entities.add(northRoom);
            entities.add(new FreeWayDoor.Builder(northRoom).coord(Coord.south()).create());
        }

        {
            Room southRoom = new Room.Builder(sanboxLocation).coord(Coord.xy(0, -1)).title("Южная комната").description("Южная комната").build();
            entities.add(southRoom);
            entities.add(new FreeWayDoor.Builder(southRoom).coord(Coord.north()).create());
            entities.add(new FreeWayDoor.Builder(southRoom).coord(Coord.east()).create());
        }

        {
            Room warehous = new Room.Builder(sanboxLocation).coord(Coord.xy(1, -1)).title("Кладовка").description("Душное и пыльное помещение").build();
            entities.add(warehous);
            entities.add(new FreeWayDoor.Builder(warehous).coord(Coord.west()).create());
        }
    }

}
