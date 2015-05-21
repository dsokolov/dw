package me.ilich.helloworld.app.entities;

import java.util.UUID;

public class Player extends Entity {

    public Player(UUID id) {
        super(id);
    }

    public Player(UUID id, UUID parentId) {
        super(id, parentId);
    }

}
