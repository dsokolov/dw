package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public interface Primitive {

    JSONObject toJson(JSONObject jsonObject) throws JSONException;

    abstract class Impl implements Primitive {

    }

}
