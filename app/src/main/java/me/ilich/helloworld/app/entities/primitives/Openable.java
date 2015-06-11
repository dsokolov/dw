package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONException;
import org.json.JSONObject;

public interface Openable extends Primitive {

    void open();

    void close();

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
        public void open() {
            openState = OpenState.OPEN;
        }

        @Override
        public void close() {
            openState = OpenState.CLOSE;
        }

        @Override
        public OpenState getOpenState() {
            return openState;
        }
    }

}
