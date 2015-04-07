package tgn.rkvy.deep.entities;

public class Point {

    private final String EMPTY = "";

    private final String settingId;
    private final String locationId;
    private final String roomId;

    public Point(String settingId) {
        this.settingId = settingId;
        this.locationId = EMPTY;
        this.roomId = EMPTY;
    }

    public Point(String settingId, String locationId) {
        this.settingId = settingId;
        this.locationId = locationId;
        this.roomId = EMPTY;
    }

    public Point(String settingId, String locationId, String roomId) {
        this.settingId = settingId;
        this.locationId = locationId;
        this.roomId = roomId;
    }

    @Override
    public boolean equals(Object obj) {
        final boolean result;
        if (obj instanceof Point) {
            Point p = (Point) obj;
            result = p.settingId.equalsIgnoreCase(settingId) && p.locationId.equalsIgnoreCase(locationId) && p.roomId.equalsIgnoreCase(roomId);
        } else {
            result = false;
        }
        return result;
    }

    public boolean sameSetting(Point point) {
        return settingId.equalsIgnoreCase(point.settingId);
    }

    public boolean sameLocation(Point point) {
        return sameSetting(point) && locationId.equalsIgnoreCase(point.locationId);
    }

    public boolean sameRoom(Point point) {
        return sameLocation(point) && roomId.equalsIgnoreCase(roomId);
    }

    public String getSettingId() {
        return settingId;
    }

    public String getLocationId() {
        return locationId;
    }
}
