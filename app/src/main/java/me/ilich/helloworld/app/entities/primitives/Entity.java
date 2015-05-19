package me.ilich.helloworld.app.entities.primitives;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Entity {

    private static final String ID = "id";
    private static final String PARENT_ID = "parent_id";

/*    public Entity fromJson(JSONObject jsonObject) {
        UUID id = UUID.fromString(jsonObject.optString(ID));
        String ptId = jsonObject.optString(PARENT_ID);
        final UUID parentId = ptId == null || ptId.equals("") ? null : UUID.fromString(ptId);
        return new Entity(id, parentId);
    }*/

    private final UUID id;
    private final UUID parentId;

    public Entity(UUID id, UUID parentId) {
        this.id = id;
        this.parentId = parentId;
    }

    public UUID getId() {
        return id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public JSONObject toJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        jsonObject.put(ID, id.toString());
        jsonObject.put(PARENT_ID, parentId == null ? null : parentId.toString());
        return jsonObject;
    }

}
