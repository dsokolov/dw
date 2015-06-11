package me.ilich.helloworld.app.entities.primitives;

import me.ilich.helloworld.app.Controller;
import org.json.JSONException;
import org.json.JSONObject;

public interface Openable extends Primitive {

    void open(Controller controller);

    void close(Controller controller);

    enum OpenState {
        OPEN,
        CLOSE
    }

    OpenState getOpenState();

    class Impl extends Primitive.Impl implements Openable {

        private OpenState openState = OpenState.CLOSE;

        @Override
        public JSONObject toJson(JSONObject jsonObject) throws JSONException {
            return null;
        }

        @Override
        public void open(Controller controller) {
            openState = OpenState.OPEN;
        }

        @Override
        public void close(Controller controller) {
            openState = OpenState.CLOSE;
        }

        @Override
        public OpenState getOpenState() {
            return openState;
        }
    }

}
