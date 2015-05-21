package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import me.ilich.helloworld.app.entities.Entity;
import me.ilich.helloworld.app.entities.Player;
import org.json.JSONException;
import org.json.JSONObject;

public interface Pickable extends Primitive {

    void onPickup(Controller controller, Entity thisEntity, Player player);

    class Impl extends Primitive.Impl implements Pickable {

        @Override
        public void onPickup(Controller controller, Entity thisEntity, Player player) {
            thisEntity.setParentId(player.getId());
        }

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return jsonObject;
        }

    }

}
