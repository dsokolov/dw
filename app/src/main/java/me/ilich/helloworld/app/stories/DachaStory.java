package me.ilich.helloworld.app.stories;

import me.ilich.helloworld.app.datasource.WriteDataSource;
import me.ilich.helloworld.app.entities.*;

public class DachaStory implements Story {

    @Override
    public void loadTo(WriteDataSource dataSource) {

        Setting dachaSetting = new Setting();


        Location gardenLocation = new Location(dachaSetting);
        {
            Room frontOfHouseRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 0)).title("Перед домом").description("Вы стоите перед дачным домом").build();
            dataSource.add(frontOfHouseRoom);
            dataSource.add(new FreeWayByCoordDoor.Builder(frontOfHouseRoom).coord(Coord.north()).title("Центр сада").create());
            dataSource.add(new FreeWayByCoordDoor.Builder(frontOfHouseRoom).coord(Coord.south()).title("В дом").create());
            dataSource.add(new Player(frontOfHouseRoom));
        }
        {
            Room gardenRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 1)).title("Центральная часть сада").description("Здесь растут деревья").build();
            dataSource.add(gardenRoom);
            dataSource.add(new FreeWayByCoordDoor.Builder(gardenRoom).coord(Coord.north()).title("В дальную часть сада").create());
            dataSource.add(new FreeWayByCoordDoor.Builder(gardenRoom).coord(Coord.south()).title("К дому").create());
        }
        {
            Room endGardenRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 2)).title("Дальняя часть сада").description("Здесь темно и прохладно").build();
            dataSource.add(endGardenRoom);
            dataSource.add(new FreeWayByCoordDoor.Builder(endGardenRoom).coord(Coord.south()).title("Центр сада").create());
        }

        Location inHouseLocation = new Location(dachaSetting);
        {
            Room enteringHouseRoom = new Room.Builder(inHouseLocation).coord(Coord.xy(0, 0)).title("Веранда").description("Вы стоите на веранде дачного дома").build();
            dataSource.add(enteringHouseRoom);
        }

    }

}
