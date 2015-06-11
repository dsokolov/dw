package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Coord;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Location;
import me.ilich.helloworld.app.entities.Room;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface Enterable extends Primitive {

    String getTitle();

    boolean isVisible();

    void onEnter(Controller controller);

    Room getRoom(Controller controller);

    class Impl extends Primitive.Impl implements Enterable {

        private final String title;
        private final boolean visible;
        private final RoomSource roomSource;

        public Impl(String title, boolean visible, RoomSource roomSource) {
            this.title = title;
            this.visible = visible;
            this.roomSource = roomSource;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public boolean isVisible() {
            return visible;
        }

        @Override
        public void onEnter(Controller controller) {
            controller.println("enter"); //TODO
            Room newRoom = roomSource.getRoom(controller);
            //TODO null check
            controller.setCurrentRoom(newRoom);
        }

        @Override
        public Room getRoom(Controller controller) {
            return roomSource.getRoom(controller);
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject;
        }
    }

    interface RoomSource {

        Room getRoom(Controller controller);

    }

    class RelativeCoordRoomSource implements RoomSource {

        private final Coordinable coordinable;

        public RelativeCoordRoomSource(Coordinable coordinable) {
            this.coordinable = coordinable;
        }

        @Override
        public Room getRoom(Controller controller) {
            List<Entity> roomEntities = controller.getEntities(Room.class);
            Coord coord = Coord.sum(controller.getCurrentRoom().getCoord(), coordinable.getCoord());
            Room room = (Room) roomEntities.stream().filter(entity -> ((Room) entity).getCoord().equals(coord) && Objects.equals(entity.getParentId(), controller.getCurrentRoom().getParentId())).findFirst().orElse(null);
            return room;
        }

    }

    class LocationRoomSource implements RoomSource {

        private final UUID locationId;
        private final Coord roomCoord;

        public LocationRoomSource(Location location, Coord roomCoord) {
            this.locationId = location.getId();
            this.roomCoord = roomCoord;
        }

        @Override
        public Room getRoom(Controller controller) {
            List<Entity> roomEntities = controller.getEntities(Room.class);
            Room room = (Room) roomEntities.stream().filter(entity -> Objects.equals(entity.getParentId(), locationId) && ((Room) entity).getCoord().equals(roomCoord)).findFirst().orElse(null);
            return room;
        }
    }

}
