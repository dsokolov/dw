package me.ilich.helloworld.app.stories;

import me.ilich.helloworld.app.datasource.WriteDataSource;
import me.ilich.helloworld.app.entities.*;
import me.ilich.helloworld.app.entities.primitives.Enterable;

public class DachaStory implements Story {

    @Override
    public void loadTo(WriteDataSource dataSource) {

        Setting dachaSetting = new Setting();

        Location gardenLocation = new Location(dachaSetting);
        Location inHouseLocation = new Location(dachaSetting);

        {
            {
                Room frontOfHouseRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 0)).title("Перед домом").description("Вы стоите перед дачным домом").build();
                dataSource.add(frontOfHouseRoom);
                dataSource.add(new FreeWayDoor.Builder(frontOfHouseRoom).coord(Coord.north()).title("Центр сада").create());
                dataSource.add(new FreeWayDoor.Builder(frontOfHouseRoom).coord(Coord.south()).title("В дом").roomSource(new Enterable.LocationRoomSource(inHouseLocation, Coord.zero())).create());

                dataSource.add(new Player(frontOfHouseRoom));
            }
            {
                Room gardenRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 1)).title("Центральная часть сада").description("Здесь растут деревья").build();
                dataSource.add(gardenRoom);
                dataSource.add(new FreeWayDoor.Builder(gardenRoom).coord(Coord.north()).title("В дальную часть сада").create());
                dataSource.add(new FreeWayDoor.Builder(gardenRoom).coord(Coord.south()).title("К дому").create());
            }
            {
                Room endGardenRoom = new Room.Builder(gardenLocation).coord(Coord.xy(0, 2)).title("Дальняя часть сада").description("Здесь темно и прохладно").build();
                dataSource.add(endGardenRoom);
                dataSource.add(new FreeWayDoor.Builder(endGardenRoom).coord(Coord.south()).title("Центр сада").create());
            }
        }
        {
            OpenableTrigger openableTrigger1=  new OpenableTrigger();
            {
                Room houseVerandaRoom = new Room.Builder(inHouseLocation).coord(Coord.xy(0, 0)).title("Веранда").description("Вы стоите на веранде дачного дома").build();
                dataSource.add(houseVerandaRoom);
                dataSource.add(new FreeWayDoor.Builder(houseVerandaRoom).coord(Coord.north()).title("В сад").roomSource(new Enterable.LocationRoomSource(gardenLocation, Coord.zero())).create());
                dataSource.add(new OpenCloseDoor.Builder(houseVerandaRoom).coord(Coord.south()).title("Зайти в дом").openTrigger(openableTrigger1).create());

            }
            {
                Room houseKitchenRoom = new Room.Builder(inHouseLocation).coord(Coord.xy(0, -1)).title("Кухока").description("Вы находитесь в крошечной кухне.").build();
                dataSource.add(houseKitchenRoom);
                dataSource.add(new OpenCloseDoor.Builder(houseKitchenRoom).coord(Coord.north()).title("Выйти из дома").openTrigger(openableTrigger1).create());

                Chest chest = new Chest.Builder<>(houseKitchenRoom).
                        title("холодильник|холодильника|холодильнику|холодильник|холодильником|холодильнике").
                        scene("В углу стоит холодильник").
                        build();
                dataSource.add(chest);
            }
        }

    }

}
