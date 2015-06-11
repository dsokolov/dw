package me.ilich.helloworld.app.entities;

import me.ilich.helloworld.app.entities.primitives.Primitive;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

public class Entity implements Primitive {

    private static final String ID = "id";
    private static final String PARENT_ID = "parent_id";

    private final UUID id;
    private UUID parentId;

    public Entity() {
        this((UUID) null);
    }

    public Entity(UUID parentId) {
        this.id = UUID.randomUUID();
        this.parentId = parentId;
    }

    protected Entity(Builder builder) {
        this(builder.parentId);
    }

    public Entity(Entity parentEntity) {
        this(parentEntity.getId());
    }

    public UUID getId() {
        return id;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public JSONObject toJson(JSONObject jsonObject) throws JSONException {
        if (jsonObject == null) {
            jsonObject = new JSONObject();
        }
        jsonObject.put(ID, id.toString());
        jsonObject.put(PARENT_ID, parentId == null ? null : parentId.toString());
        return jsonObject;
    }

    public static class Builder<B extends Builder<B>> {

        private UUID parentId;

        public Builder(Entity parent) {
            this.parentId = parent.getId();
        }

        protected Builder getThis() {
            return this;
        }

        public Entity build() {
            return new Entity(this);
        }

    }

}
