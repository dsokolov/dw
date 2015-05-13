package tgn.rkvy.deep.data;

import tgn.rkvy.deep.entities.Point;

import java.util.UUID;

/**
 * 41286403-D273-3AC5-A016-244ADFF68151
 * 01234567-89AB-CDEF-0123-456789ABCDEF
 * <p/>
 * 0  - setting
 * 1 - ?
 * 2 - location
 * 45 - event
 */
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
    public String getLocationId() {
        return seed.substring(2, 3);
    }

    @Override
    public String getRoomId() {
        return seed.substring(3, 4);
    }

    @Override
    public Point getPoint() {
        return new Point(getSettingId(), getLocationId(), getRoomId());
    }

    @Override
    public String getEventId() {
        return seed.substring(4, 6);
    }

    @Override
    public String toString() {
        return seed + " " + tag;
    }
}
