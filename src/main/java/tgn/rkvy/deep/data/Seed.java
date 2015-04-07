package tgn.rkvy.deep.data;

import tgn.rkvy.deep.entities.Point;

public interface Seed {

    String getTag();

    String getSettingId();

    String getLocationId();

    String getRoomId();

    Point getPoint();

    String getEventId();

}
