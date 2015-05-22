package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.entities.Coord;
import org.json.JSONException;
import org.json.JSONObject;

public interface Coordinable extends Primitive {

    Coord getCoord();

    class Impl extends Primitive.Impl implements Coordinable {

        private final Coord coord;

        public Impl(Coord coord) {
            this.coord = coord;
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject;
        }

        @Override
        public Coord getCoord() {
            return coord;
        }
    }

}
