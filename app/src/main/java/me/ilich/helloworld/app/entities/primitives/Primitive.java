package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONException;
import org.json.JSONObject;

public interface Primitive {

    JSONObject toJson(JSONObject jsonObject) throws JSONException;

    abstract class Impl implements Primitive {

    }

}
