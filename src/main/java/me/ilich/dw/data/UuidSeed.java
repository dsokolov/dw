package me.ilich.dw.data;

import java.util.UUID;


public class UuidSeed implements Seed {

    private final String tag;
    private final String seed;

    public UuidSeed(String tag) {
        this.tag = tag;
        UUID uuid = UUID.nameUUIDFromBytes(tag.getBytes());
        this.seed = uuid.toString().toUpperCase();
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getSettingId() {
        return seed.substring(0, 1);
    }

    @Override
    public String getRoomId() {
        return seed.substring(2, 4);
    }
}
