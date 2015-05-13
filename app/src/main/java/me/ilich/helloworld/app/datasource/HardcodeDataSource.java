package me.ilich.helloworld.app.datasource;

import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Door;
import me.ilich.helloworld.app.entities.Item;
import me.ilich.helloworld.app.entities.Room;

import java.util.ArrayList;
import java.util.List;

public class HardcodeDataSource implements DataSource {

    private static List<Room> rooms = new ArrayList<>();

    static {

        Item ball = new Item("мяч", "Небольшой резиновый мяч.");
        Item box = new Item("ящик", "Вместительный деревянный ящик");

        rooms.add(new Room.Builder()
                .coord(Coord.xy(0, 0))
                .title("Начальная комната")
                .description("Начальная комната")
                .door(Door.north())
                .door(Door.east())
                .door(Door.south())
                .door(Door.west())
                .item(ball)
                .item(box)
                .build());

        rooms.add(new Room(Coord.xy(1, 0), "Восточная комната", "Восточная комната", new Door[]{Door.west()}, new Item[]{}));
        rooms.add(new Room(Coord.xy(-1, 0), "Западная комната", "Западная комната", new Door[]{Door.east()}, new Item[]{}));
        rooms.add(new Room(Coord.xy(0, 1), "Северная комната", "Северная комната", new Door[]{Door.south()}, new Item[]{}));
        rooms.add(new Room(Coord.xy(0, -1), "Южная комната", "Южная комната", new Door[]{Door.north()}, new Item[]{}));
    }


    @Override
    public List<Room> getRooms() {
        return rooms;
    }
}
