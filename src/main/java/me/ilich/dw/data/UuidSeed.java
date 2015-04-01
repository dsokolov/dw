package me.ilich.dw.data;

import java.util.UUID;

/**
 * 41286403-D273-3AC5-A016-244ADFF68151
 * 01234567-89AB-CDEF-0123-456789ABCDEF
 * <p/>
 * 0  - setting
 * 1 - ?
 * 23 - room
 * 45 - event
 */
public class UuidSeed implements Seed {

    private final String tag;
    private final String seed;

    public UuidSeed(String tag) {
        this.tag = tag;
        UUID uuid = UUID.nameUUIDFromBytes(tag.getBytes());
        this.seed = uuid.toString().toUpperCase();
        System.out.println(seed);
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

    @Override
    public String getEventId() {
        return seed.substring(4, 6);
    }

}
